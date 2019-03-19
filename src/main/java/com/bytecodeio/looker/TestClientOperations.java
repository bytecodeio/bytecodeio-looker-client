package com.bytecodeio.looker;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import com.bytecodeio.looker.api.DashboardApi;
import com.bytecodeio.looker.api.DashboardElementApi;
import com.bytecodeio.looker.api.LookApi;
import com.bytecodeio.looker.api.RenderTaskApi;
import com.bytecodeio.looker.api.SpaceApi;
import com.bytecodeio.looker.model.Dashboard;
import com.bytecodeio.looker.model.DashboardElement;
import com.bytecodeio.looker.model.Look;
import com.bytecodeio.looker.model.Space;
import com.bytecodeio.looker.util.Config;

public class TestClientOperations {

	static void outputFile(String dest, byte[] data)throws Exception{
		File destFile = new File("/Users/jefe/temp/looker/"+ dest);

		if(destFile.exists()){
			destFile.delete();
		}
		FileOutputStream outputStream = new FileOutputStream(destFile);
		outputStream.write(data);
		outputStream.flush();
		outputStream.close();

	}

	public static void main(String[] args){

		try{
			Config config = Config.getConfig();
			config.CONFIG_API_KEY="gtfp9GZxRZ2PzyhHPFrG";
			config.CONFIG_SECRET_KEY="XCRvfrpKgdQCjBfH9xw42Z76";
			config.CONFIG_API_BASE_3_0 = "https://bytecode.looker.com:19999/api/3.0";
			config.CONFIG_API_BASE_3_1 = "https://bytecode.looker.com:19999/api/3.1";
			config.CONFIG_EMBED_SECRET_ID = "577b70af9697d599928ad0367bd85b7da3be3721fbb607af47c1b0542daaf1c4";

			RenderTaskApi renderTaskApi = new RenderTaskApi();
			DashboardApi dashboardApi = new DashboardApi();
			LookApi lookApi = new LookApi();
			DashboardElementApi dashboardElementApi = new DashboardElementApi();
			SpaceApi spaceApi = new SpaceApi();

			//List<Dashboard> dashboards = dashboardApi.getDashboardSummaries(null);


			//Retrieve a dashboard
				//Dashboard dashboard = dashboardApi.getDashboard("37");

			//List all looks for a 'template' space
				//List<Look>availableTemplates = spaceApi.getLooksForSpace("57");
				//Space space = spaceApi.getSpace("57");
				//List<Look>availableTemplates = space.getLooks();


			//Add a few looks to an existing dashboard

				DashboardElement newElement;
				newElement = dashboardElementApi.addLookToDefaultDashboard("14", "37","");
				/*newElement = dashboardElementApi.addLookToDefaultDashboard("14", "37","");
				newElement = dashboardElementApi.addLookToDefaultDashboard("14", "37","");
				newElement = dashboardElementApi.addLookToDefaultDashboard("14", "37","");
				newElement = dashboardElementApi.addLookToDefaultDashboard("14", "37","");
				newElement = dashboardElementApi.addLookToDefaultDashboard("14", "37","");*/


			//List existing dashboard elements
				//List<DashboardElement>existingDashboardElements = dashboardElementApi.getDashboardElements("37");

			//Remove a look from a dashboard
				dashboardElementApi.removeTileFromDashboard(newElement.getId(), "37");


			//Download dashboard in PDF format
				//byte[] pdfContent = renderTaskApi.downloadDashboard("pdf", "37");
				//outputFile("looker.pdf", pdfContent);

			//Download dashboard in JPG format
				//byte[] jpgContent = renderTaskApi.downloadDashboard("jpg", "37");
				//outputFile("looker.jpg", jpgContent);

			//Download dashboard in PNG format
				//byte[] pngContent = renderTaskApi.downloadDashboard("png", "37");
				//outputFile("looker.png", pngContent);

			//Download dashboard in HTML format
				//byte[] dashboardHtml = renderTaskApi.downloadDashboard("html","37");
				//outputFile("looker.html", dashboardHtml);

			//List looks for a given space.
			/*
			List<Look> looks = spaceApi.getLooksForSpace("57");
			for(Look look:looks){
				look.getTitle();
			}*/

			System.out.println("Fin");
		}
		catch(Exception e){
			e.printStackTrace();
		}


	}

}
