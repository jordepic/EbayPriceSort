import java.io.IOException;
import java.io.*;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {
	public static void main (String[] args) {
		
		
		ArrayList<Double> finalPrice = new ArrayList<Double>();
		ArrayList<String> basePrice = new ArrayList<String>();
		ArrayList<String> shipPrice = new ArrayList<String>();
		System.out.println("Please enter your ebay URL to sort by price with shiping costs! (You better enter the right link)");
		Scanner getLink = new Scanner (System.in);
		String myLink = getLink.nextLine();
		System.out.println("Please enter the maximum price that you would be willing to pay (just a double amount).");
		double myMax = Double.parseDouble(getLink.nextLine());
		
		try {
			org.jsoup.nodes.Document doc = Jsoup.connect(myLink).userAgent("Chrome/63.0.3239.132").get();
			Elements title = doc.select("h3.lvtitle");
			Elements base = doc.select("li.lvprice");
			Elements ship = doc.select("li.lvshipping");
			for (Element number: base) {
				basePrice.add(number.getElementsByTag("span").first().getElementsByTag("span").first().text());
				
			}
			for (Element number: ship) {
				shipPrice.add(number.getElementsByTag("span").first().text());
			}
			
			//Could be impossible to parse due to $
			//https://stackoverflow.com/questions/16787099/how-to-split-the-string-into-string-and-integer-in-java use this to break string up
			
			for (int i = 0; i < shipPrice.size(); i++) {
				if (shipPrice.get(i).replaceAll("[\\D]", "").equals("")) {
					finalPrice.add(Double.parseDouble(basePrice.get(i).replaceAll("[\\D]", ""))/100.);
				}
				else {
					finalPrice.add(Double.parseDouble(basePrice.get(i).replaceAll("[\\D]", ""))/100. + Double.parseDouble(shipPrice.get(i).replaceAll("[\\D]", ""))/100.);
					
				}
				
			}
			//Right now shipping costs are not adding, do a println with shipping costs later in order to determine if they register at all.
			
			int i = 0;
			for (Element listing: title) {
				if (finalPrice.get(i) < myMax) {
					System.out.println(listing.getElementsByTag("a").first().text() + "      " + (finalPrice.get(i)));
				}
				
				i++;
			}
			//INSTEAD USE METHOD FROM TUTORIAL VIDEO WITH SEPARATE ARRAYLISTS FOR EACH AND THEN MODIFY DATA FROM THERE
			
		} 
		
		
		
		
		
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
