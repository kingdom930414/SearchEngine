package cs600finalproject.yangyangliu;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class App 
{
    public static void main( String[] args ) throws IOException
    {
        System.out.println( "----------------Trie Search Engine ------------------" );
        System.out.println( "--------------By yangyang liu in cs600 course--------");

    	Scanner sc = new Scanner(System.in);
    	String url = "http://tutorials.jenkov.com/";
    	String urlDefault = "http://tutorials.jenkov.com/";
    	int max = 0;
    	System.out.println( "> This is a search engine to search any words from a website. ");
    	
    	try {
    		System.out.println( "> Input the website url you what to search or press 'Enter' to search the default website:" );
    		url = sc.nextLine();
    		
    		//if the url input is not empty use the url you input, otherwise use the default website.
    		if(!url.equals("")) {
    			System.out.println("> The website you want to search is  "+url);
    		}else {
    			System.out.println("> The website you want to search is  "+urlDefault);
    			url=urlDefault;
    			
    			List<Integer> list= new ArrayList<>();
    			list.toArray();
    		}
    	} catch (Exception e) {
    		System.out.print(e);
    	}
    	try {
    		System.out.println("> Input a number as the maxmium search time:");
    		max = Integer.parseInt(sc.nextLine());
    		if(max>0) {
    			System.out.println("> The maximum search time is "+max);
    		}else {
    			System.out.println("> Come on! You have to search at least once.");
    			return;
    		}
    	} catch (Exception e) {
    		System.out.print(e);
    		return;
    	}
    	System.out.println(">> [Web Crawler Running]");
    	//use jsoup to get the data from website and insert data into trie data structure
    	WebCrawler.getData(url,max);
    	System.out.println(">> [Web Crawler Complete]");
    	
    	while(true) {
    		String keyword = new String();
    		System.out.println("> Input some word to search: ");
    		try {
    			keyword = sc.nextLine();
    			//search a keyword and get the count of this word on each website.
    			String result = DataHandler.search(keyword);
    			System.out.println(result);
    		}catch(Exception e) {
    			System.out.println(e);
    		}
    	}
    	
    }

}
