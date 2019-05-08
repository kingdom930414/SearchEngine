package cs600finalproject.yangyangliu;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class WebCrawler {
	public static void getData(String url, int limit) {
		ArrayList<String> links = new ArrayList<String>();
		ArrayList<String> historyLinks = new ArrayList<String>();
		links.add(url);

		while (!links.isEmpty() && historyLinks.size() < limit) {
			String curLink = links.remove(0);
			if (!historyLinks.contains(curLink)) {
				historyLinks.add(curLink);
				System.out.println("> Craw: " + curLink);
				try {
					String htmlString = Jsoup.connect(curLink).get().text();
					DataHandler.getToken(htmlString, curLink);	
				} catch (Exception e) {
					System.out.println("> Error: " + e.getMessage());
					continue;
				}
				for (String s : getLinks(curLink)) {
					if (!historyLinks.contains(s)) {
						links.add(s);
					}
				}
			}
		}
		System.out.println("> There are " + historyLinks.size()+ " URLs ");
		System.out.println (">> [Begin searching]");
	}

	private static LinkedList<String> getLinks(String curLink) {
		LinkedList<String> list = new LinkedList<String>();
		try {
			for (Element e : Jsoup.connect(curLink).get().select("a[href]")) {
				list.add(e.absUrl("href"));
			}
		} catch (Exception e) {
			System.out.println("> Error: " + e.getMessage());
		}		
		return list;
	}
}
