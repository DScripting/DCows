package scripts.DTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Newsloader 
{

	private static ArrayList<String> latestNews = new ArrayList<String>();
	private static long startNewsTime = System.currentTimeMillis();
	private static boolean initialLoad = true;
	
	public static ArrayList<String> getNews()
	{
		ArrayList<String> toReturn = new ArrayList<String>();
		try {
		    URL url = new URL("http://d-scripting.com/DEssentials/DNews.txt");
		    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		    String str;
		    while ((str = in.readLine()) != null) {
		    	toReturn.add(str);
		    }
		    in.close();
		} catch (MalformedURLException e) {
			System.out.println("Your firewall blocked the news loader.");
		} catch (IOException e) {
			System.out.println("Unable to connect to the news loader.");
		}
	return toReturn;
	}
	
	
	private static int newsToShow = 0;
	
	private static void getnewsdata()
	{
		int newsTimer = (int) (System.currentTimeMillis() - startNewsTime) / 1000;
		if(newsTimer > 8)
		{
			startNewsTime = System.currentTimeMillis();
			if(newsToShow == (latestNews.size() -1)) newsToShow = 0;
			else if(newsToShow < (latestNews.size())) newsToShow++;
			
		}
		
	}
	
	public static String getLatestNews()
	{
		if(initialLoad) { initialLoad = false; latestNews = getNews(); }
		getnewsdata();
		return latestNews.get(newsToShow);
	}
	
	
}
