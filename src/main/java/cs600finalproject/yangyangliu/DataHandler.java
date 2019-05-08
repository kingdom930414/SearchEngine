package cs600finalproject.yangyangliu;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map.Entry;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class DataHandler {
	
	private static Trie trie = new Trie();

	public static void getToken(String htmlString, String currentURL) throws IOException {
		// TODO Auto-generated method stub
		
		//replace all the upper case to lower case
		htmlString = htmlString.replaceAll("[^A-Za-z\\s]+"," ");
		//trim the string
		htmlString = htmlString.replaceAll("\\s+", " ").trim().toLowerCase();
		//filter stopwords
		String[] stopWords = new String[]{"a", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against", "all"};
		String stopWordsPattern = String.join("|", stopWords);
		Pattern pattern = Pattern.compile("\\b(?:" + stopWordsPattern + ")\\b\\s*", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(htmlString);
		htmlString = matcher.replaceAll("");
		InputStream is = new FileInputStream("en-token.bin");
        TokenizerModel model = new TokenizerModel(is);
        Tokenizer tokenizer = new TokenizerME(model);
        //use Opennlp package to handle the stirng and get the tokens
        String tokens[] = tokenizer.tokenize(htmlString);
		for (String w : tokens){
			//insert all the tokens to trie data structure
			trie.insert(w,currentURL);
//			System.out.println(w);
		}
	}

	public static String search(String keyword) {
		// TODO Auto-generated method stub
		
		//replace all the upper case to lower case
		keyword = keyword.replaceAll("[^A-Za-z\\s]+"," ");
		//trim the string
		keyword = keyword.replaceAll("\\s+", " ").trim().toLowerCase();
		//split the keyword string to words and search each word and get result and filter stopwords
		String[] stopWords = new String[]{"a", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against", "all"};
		String stopWordsPattern = String.join("|", stopWords);
		Pattern pattern = Pattern.compile("\\b(?:" + stopWordsPattern + ")\\b\\s*", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(keyword);
		keyword = matcher.replaceAll("");
		String[] words = keyword.split(" ");
		//check if the input is empty
		if (words.length == 0 || (words.length == 1 && words[0].isEmpty())){	
			return "> The word you want to search is not validate.";
		}
		
		//The search engin we did can contain multiple words searching.
		//This the the map for each of the word counting numbers
		HashMap<String, Integer> tempMap = new HashMap<String, Integer>();
		//This is the map for all words counting and for ranking usage
		HashMap<String, Integer> allWordsMap = new HashMap<String, Integer>();
		Iterator<Entry<String, Integer>> iter;
		int wordCounts = 0;
		String result = new String();
		
		result += "> Searched against: ";
		for (String word : words){
			try {
				//get the current word counts in trie data structure
				tempMap = trie.get(word);
			} catch (NullPointerException e) {
				result += (word + "(Not Found) ");
				if (word.equals(words[words.length-1])) {
					break;
				}else {
					continue;					
				}
			}
			//accumulate the wordcounts 
			wordCounts += tempMap.remove("WordCount");
			wordCounts++;
			iter = tempMap.entrySet().iterator();
			while (iter.hasNext()){
				Entry<String,Integer> e = iter.next();
				allWordsMap.merge(e.getKey(), e.getValue(), 
						(value, newValue) -> value + e.getValue()); 
			}
			result += (word + " ");
		}

		result += ( "> Total occurence: " + wordCounts+"\n");

		
		Comparator<String> comp = new ComparatorForMap(allWordsMap);
		TreeMap<String,Integer> tree = new TreeMap<>(comp);
		tree.putAll(allWordsMap);
		iter = tree.entrySet().iterator();
		int ranking = 1;
		//iterater all the words and get the ranking for wordcounts
		while(iter.hasNext()){
			Entry<String,Integer> entry = iter.next();
			String url = entry.getKey();
			Integer occurence = entry.getValue();
			result += ("> Rank " + ranking++ + ": " + url + "\n");
			result += ("> Total counts for these words appear on this site is: " + occurence + "\n");
		}
		result += ("> [End of Current Seach]" + "\n");
		return result;
	}
	
	private static class ComparatorForMap implements Comparator<String> {
		
		private HashMap<String, Integer> hmap = new HashMap<>();
		public ComparatorForMap(HashMap<String, Integer> hmap) {
			this.hmap.putAll(hmap);
		}
		@Override
		public int compare(String s1, String s2) {
			if(hmap.get(s1) >= hmap.get(s2)){
				return -1;
			}else{
				return 1;
			}
		}	
	}

}
