package com.bytecodeio.looker.api;

import java.util.HashMap;

import com.bytecodeio.looker.model.Dashboard;
import com.bytecodeio.looker.model.DashboardElement;
import com.bytecodeio.looker.model.RenderTask;
import com.bytecodeio.looker.util.ApiException;
import com.bytecodeio.looker.util.Config;
import com.bytecodeio.looker.util.MappingUtils;
import com.bytecodeio.looker.util.RestClient;

public class RenderTaskApi extends ApiBase{

	public static final String DASHBOARD_FORMAT_PDF = "pdf";
	public static final String DASHBOARD_FORMAT_JPG = "jpg";
	public static final String DASHBOARD_FORMAT_PNG = "png";
	public static final String DASHBOARD_FORMAT_CSV = "csv";
	public static final String DASHBOARD_FORMAT_HTML = "html";

	public static final String RENDER_STATUS_ENQUED_FOR_QUERY = "enqueued_for_query";
	public static final String RENDER_STATUS_QUERYING = "querying";
	public static final String RENDER_STATUS_FOR_RENDER = "enqueued_for_render";
	public static final String RENDER_STATUS_RENDERING = "rendering";
	public static final String RENDER_STATUS_SUCCESS = "success";
	public static final String RENDER_STATUS_FAILURE = "failure";

	String apiSuffix_3_0 = Config.CONFIG_API_BASE_3_0 +"/render_tasks";
	private DashboardApi dashboardApi;
	QueryApi queryApi;

	public static final int RENDER_STATUS_CHECK_DELAY = 200;

	public RenderTaskApi(){
		super();
		this.dashboardApi = new DashboardApi();
		this.queryApi = new QueryApi();
	}

	RenderTask registerDashboardRenderTask(String dashboardId, String format)throws ApiException{
		HashMap<String, String>params = new HashMap();
		params.put("width", "1000");
		params.put("height", "1024");
		params.put("pdf_paper_size", "a4");

		//String responseJson = RestClient.performPOSTOperation(getAuthToken("3.0"), apiSuffix_3_0 +"/dashboards/"+ dashboardId +"/"+ format +"?width=1000&height=1024&pdf_paper_size=a4","{}");
		String responseJson = RestClient.performPOSTOperation(getAuthToken("3.0"), apiSuffix_3_0 +"/dashboards/"+ dashboardId +"/"+ format ,"{\"dashboard_filters\":\"account_id=67923\"}", params);

		RenderTask renderTask = new RenderTask();
		try{
			MappingUtils.populateFromJson(responseJson, renderTask);
		}
		catch(Exception e){
			throw new ApiException("Unable to map object");
		}
		return renderTask;
	}

	RenderTask registerLookRenderTask(String lookId, String format)throws ApiException{
		HashMap<String, String>params = new HashMap();
		params.put("width", "1000");
		params.put("height", "1024");
		params.put("pdf_paper_size", "a4");

		String responseJson = RestClient.performPOSTOperation(getAuthToken("3.0"), apiSuffix_3_0 +"/looks/"+ lookId +"/"+ format, params);
		RenderTask renderTask = new RenderTask();
		try{
			MappingUtils.populateFromJson(responseJson, renderTask);
		}
		catch(Exception e){
			throw new ApiException("Unable to map object");
		}
		return renderTask;
	}

	RenderTask getRenderTask(String id)throws ApiException{

		String responseJson = RestClient.performGETOperation(getAuthToken("3.0"), apiSuffix_3_0 +"/"+ id);

		RenderTask renderTask = new RenderTask();
		try{
			MappingUtils.populateFromJson(responseJson, renderTask);
		}
		catch(Exception e){
			throw new ApiException("Unable to map object");
		}
		return renderTask;
	}

	byte[]getRenderTaskResult(String renderTaskId)throws ApiException{

		byte[] responseBytes = RestClient.performBinaryGETOperation(getAuthToken("3.0"), apiSuffix_3_0 +"/"+ renderTaskId +"/results");

		return responseBytes;
	}

	public byte[]downloadDashboard(String format, String dashboardId)throws ApiException{
		//TDO: Log statement...
		System.out.println("Rendering dashboard in '"+ format +"' format.");
		//TODO: Condense methods
		if(format.equals(DASHBOARD_FORMAT_PDF)){
			//Request render task for dashboard
			RenderTask renderTask = registerDashboardRenderTask(dashboardId, DASHBOARD_FORMAT_PDF);

			//Wait until status enters 'completed' status.
			renderTask = getRenderTask(renderTask.getId());
			boolean rendering = true;
			while(rendering){
				try{ Thread.sleep(RENDER_STATUS_CHECK_DELAY); }catch(Exception e){}
				renderTask = getRenderTask(renderTask.getId());
				System.out.println(renderTask.getStatus());
				if(renderTask.getStatus().equals(RENDER_STATUS_SUCCESS)){
					break;
				}
			}

			//Now retrieve binary content
			byte[] result = getRenderTaskResult(renderTask.getId());
			return result;
		}
		else if(format.equals(DASHBOARD_FORMAT_JPG)){
			//Request render task for dashboard
			RenderTask renderTask = registerDashboardRenderTask(dashboardId, DASHBOARD_FORMAT_JPG);

			//Wait until status enters 'completed' status.
			renderTask = getRenderTask(renderTask.getId());
			boolean rendering = true;
			while(rendering){
				try{ Thread.sleep(RENDER_STATUS_CHECK_DELAY); }catch(Exception e){}
				renderTask = getRenderTask(renderTask.getId());
				System.out.println(renderTask.getStatus());
				if(renderTask.getStatus().equals(RENDER_STATUS_SUCCESS)){
					break;
				}
			}

			//Now retrieve binary content
			byte[] result = getRenderTaskResult(renderTask.getId());
			return result;
		}
		else if(format.equals(DASHBOARD_FORMAT_PNG)){
			//Request render task for dashboard
			RenderTask renderTask = registerDashboardRenderTask(dashboardId, DASHBOARD_FORMAT_PNG);

			//Wait until status enters 'completed' status.
			renderTask = getRenderTask(renderTask.getId());
			boolean rendering = true;
			while(rendering){
				try{ Thread.sleep(RENDER_STATUS_CHECK_DELAY); }catch(Exception e){}
				renderTask = getRenderTask(renderTask.getId());
				System.out.println(renderTask.getStatus());
				if(renderTask.getStatus().equals(RENDER_STATUS_SUCCESS)){
					break;
				}
			}

			//Now retrieve binary content
			byte[] result = getRenderTaskResult(renderTask.getId());
			return result;
		}
		else if(format.equals(DASHBOARD_FORMAT_HTML)){
			return getDashboardAsHtml(dashboardId);
		}

		return null;
	}

	/**
	 * Retrieve HTML content of dashboard:
	 *
	 * @param dashboardId
	 * @return
	 */
	private byte[] getDashboardAsHtml(String dashboardId){

		Dashboard target = dashboardApi.getDashboard(dashboardId);

		if(target==null){
			throw new ApiException("Unable to locate dashboard with id '"+ dashboardId +"'");
		}

		StringBuilder html = new StringBuilder();
		for(DashboardElement curElement:target.getDashboardElements()){
			String lookHtml = queryApi.executeQueryRenderTaskForTextContent(curElement.getLook().getQueryId(), DASHBOARD_FORMAT_HTML);
			html.append(formatLookHtmlFragment(lookHtml));
		}

		return html.toString().getBytes();
	}

	String formatLookHtmlFragment(String lookHtml){
		lookHtml = lookHtml.replace("<head>", "");
		lookHtml = lookHtml.replace("</head>", "");
		return "<html><body>"+ lookHtml +"</body></html>";
	}

}
