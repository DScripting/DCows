package scripts.DTools;

import java.awt.Desktop;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class DWeb 
{
	
	
	public static void openSite(String url)
	{
		 URL a = null;
			try {
				a = new URL(url);
			} catch (MalformedURLException e1) {
				System.out.println("ERROR OPENING URL");
				e1.printStackTrace();
			}
			openWebpage(a);
	}  
	  
		private static void openWebpage(URI uri) {
		    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
		        try {
		            desktop.browse(uri);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		}

		private static void openWebpage(URL url) {
		    try {
		        openWebpage(url.toURI());
		    } catch (URISyntaxException e) {
		        e.printStackTrace();
		    }
		}
		
}
