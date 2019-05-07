import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 * <h3>This Crawler used JSoup to operate</h3>
 * This is the only class need JSoup.
 */
public class MyCrawler {
	public static void crawler(String[] urls) {
		for(String cur:urls) {
				try {
					Document doc = Jsoup.connect(cur).get();
					String page = doc.text();
					MyProcessor.process(page, cur);	
				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
					continue;
				}
		}
		System.out.println ("Trie data inserted. Search Begin");
	}
}
		
		