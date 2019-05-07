import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class MyProcessor {

	private static Trie trie = new Trie();
	
	public static void process(String page, String cur) {
		String data = cleansing(page);
		String[] words = data.split(" ");
		for (String w : words){
			trie.insert(w,cur);
		}	
	}
	
	private static String cleansing(String page) {
		page = page.replaceAll("[^A-Za-z\\s]+"," ");
		page = stopwordsFilter(page);
		page = page.replaceAll("\\s+", " ").trim().toLowerCase();
		return page;
	}
	private static String stopwordsFilter(String page) {
		String[] stopWords = {"a","about","above","after","again","against","all","am","an","and","any",
				"are","aren","t","as","at","be","because","been","before","being","below","between","both",
				"but","by","can","cannot","could","couldn","did","didn","do","does","doesn","doing",
				"don","down","during","each","few","for","from","further","had","hadn","has","hasn",
				"have","haven","having","he","d","ll","s","her","here","hers","herself","him","himself",
				"his","how","i","m","ve","if","in","into","is","isn","it","its","itself","let","me","more",
				"most","mustn","my","myself","no","nor","not","of","off","on","once","only","or","other",
				"ought","our","ours","ourselves","out","over","own","same","shan","she","should","shouldn",
				"so","some","such","than","that","the","their","theirs","them","themselves","then","there",
				"these","they","re","this","those","through","to","too","under","until","up","very","was",
				"wasn","we","were","weren","what","when","where","which","while","who","whom","why","with",
				"won","would","wouldn","you","your","yours","yourself","yourselves"};
		for (int i = 0; i < stopWords.length; i++){
			page = page.replaceAll(("\\b(?i)" + Pattern.quote(stopWords[i]) + "\\b+"), " ");
		}
		return page;
	}
	
	public static String searchandrank(String input) {
		HashMap<String, Integer> oneMap = new HashMap<>();

		Iterator<Entry<String, Integer>> iter;
		int wordsTotalOccurence = 0;
		StringBuilder result = new StringBuilder();
		
		result.append("Searched against: ");
		String newline = System.getProperty("line.separator");		
		try {
			oneMap=trie.get(input);
			wordsTotalOccurence = oneMap.remove("WordCount"); 
			result.append(newline + "Total occurence: " + wordsTotalOccurence+newline);
		} catch (NullPointerException e) {
			result.append(input + "(Not Found) ");
		}

		TreeMap<String, Integer> sortedTree = MyTreeMapByValue.sortValue(oneMap);
		iter = sortedTree.entrySet().iterator();
		int ranking = 1;
		while(iter.hasNext()){
			Entry<String,Integer> entry = iter.next();
			String url = entry.getKey();
			Integer occurence = entry.getValue();
			result.append("Rank " + ranking++ + ": " + url + newline);
			result.append("All words' total occurences on this site is: " + occurence + newline);
		}
		result.append(newline + "This search ends." + newline);
		return result.toString();
	}
}
