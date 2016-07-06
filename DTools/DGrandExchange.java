package scripts.DTools;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DGrandExchange 
{

	public Item getData(final int itemID) {		
		try {			
			URL u = new URL("http://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=" + itemID);			
			URLConnection c = u.openConnection();			
			BufferedReader r = new BufferedReader(new InputStreamReader(c.getInputStream()));			
			try {				
				String text = r.readLine();				
					if (text == null || text.length() == 0)					
						return null;				
					Image icon = null;				
					Image icon_large = null;				
					int id = -1;				
					String name = "";				
					String description = "";				
					int price = -1;				
					boolean members = true;				
					String regex = "http(\\D+)(\\d+)(\\D+)(\\d+)";				
					Matcher matcher = Pattern.compile(regex).matcher(text);				
					for (int i = 1; i <= 2; i++) {					
						if (matcher.find()) {						
							if (i == 1) {							
								//icon = getImage(matcher.group().replaceAll("itemdb_rs", "itemdb_oldschool"));						
								} else {							
									//icon_large = getImage(matcher.group().replaceAll("itemdb_rs", "itemdb_oldschool"));						
									}					
							}				
						}				
					regex = "id\":(\\d+)";				
					matcher = Pattern.compile(regex).matcher(text);				
					if (matcher.find())					
						id = Integer.parseInt(matcher.group().replaceAll("\\D+", ""));				
					regex = "name\":\"[^\"]+";				
					matcher = Pattern.compile(regex).matcher(text);				
					if (matcher.find())					
						name = matcher.group().substring(7);				
					regex = "description\":\"[^\"]+";				
					matcher = Pattern.compile(regex).matcher(text);				
					if (matcher.find())					
						description = matcher.group().substring(14);				
					regex = "current\"\\:\\{(.*?)\\}";				
					matcher = Pattern.compile(regex).matcher(text);				
					if (matcher.find()) {					
						regex = matcher.group().replaceAll("\"|:|}|\\{|,", "").replaceAll("^(.*?)price", "");					
						if (regex.contains("m")) {						
							price = Integer.parseInt(String.format("%.0f", Double.parseDouble(regex.replaceAll("[^\\d.]", "")) * 1000000));					
							} else if (regex.contains("k")) {					
								price = Integer.parseInt(								
										String.format("%.0f", Double.parseDouble(regex.replaceAll("[^\\d.]", "")) * 1000));					
								} else {						
									price = Integer.parseInt(regex);				
								}				
						}				
					regex = "members\":\"[^\"]+";				
					matcher = Pattern.compile(regex).matcher(text);				
					if (matcher.find())					
						if (matcher.group().substring(10).contains("false"))						
							members = false;				
					return new Item(icon, icon_large, id, name, description, price, members);			
					} finally {				
						r.close();			
						}		
			} catch (MalformedURLException e) {			
				e.printStackTrace();		
				} catch (IOException e) {			
					e.printStackTrace();		
					}		
		return null;	
		}
	

	public class Item {		
		private Image icon;		
		private Image icon_large;		
		private int id;		
		private String name;		
		private String description;		
		private int price;		
		private boolean members;		
		private Item(Image icon, Image icon_large, int id, String name, String description, int price, boolean members) 
		{			
			this.icon = icon;			
			this.icon_large = icon_large;			
			this.id = id;			
			this.name = name;			
			this.description = description;			
			this.price = price;			
			this.members = members;		
			}		
		
		private Image getIcon() 
		{			
			return icon;		
			}		
		private Image getIconLarge() 
		{			
			return icon_large;		
			}		
		private int getID()
		{			
			return id;		
			}		
		private String getName()
		{			
			return name;		
			}		
		private String getDescription() 
		{			
			return description;		
			}		
		public int getPrice() 
		{			
			return price;		
			}		
		private boolean isMembers() 
		{			
			return members;		
			}	
		}
}
