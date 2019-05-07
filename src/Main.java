import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Asker();	
	}

	private static void Asker() {	
		String[] urls = {"https://en.wikipedia.org/wiki/PATH_(rail_system)",
				"https://en.wikipedia.org/wiki/Main_Page",
				"https://en.wikipedia.org/wiki/Harta_Berdarah",
				"https://en.wikipedia.org/wiki/Portal:Current_events",
				"https://en.wikibooks.org/wiki/Communication_Networks/File_Transfer_Protocol"
		};
		System.out.println("Welcome to cs600 search engine.");
		System.out.println("Here you can search a string in five website I provided which is:");
		System.out.println("https://en.wikipedia.org/wiki/PATH_(rail_system)");
		System.out.println("https://en.wikipedia.org/wiki/Main_Page");
		System.out.println("https://en.wikipedia.org/wiki/Harta_Berdarah");
		System.out.println("https://en.wikipedia.org/wiki/Portal:Current_events");
		System.out.println("https://en.wikibooks.org/wiki/Communication_Networks/File_Transfer_Protocol");
		System.out.println("We will use jsoup to get all the data in these pages and insert these data to trie data structure.");
		System.out.println("Then, you can input a string to search how many this word are in these websites.\n");
		
		System.out.println("Search engine started!");
		
		MyCrawler.crawler(urls);
		System.out.println("Data Inserted to tire data structure.\n");
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("Input a string to search: ");
			String str = "";
			try {
				str = sc.nextLine();
				String result = MyProcessor.searchandrank(str);
				System.out.println(result);
			}catch(Exception e) {
				sc.nextLine();
				System.out.println("Error: " + e.getMessage());
			}	
		}
	}
}
