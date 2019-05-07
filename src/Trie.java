import java.util.HashMap;

public class Trie {
    private TrieNode root;
 
    public Trie() {
        root = new TrieNode();
    }
    private class TrieNode {
        TrieNode[] arr;
        boolean isEnd;
    	private TrieNode[] next = new TrieNode[26];
    	private StringBuilder[] edge = new StringBuilder[26];
    	private HashMap<String,Integer> urlsMap = new HashMap<>();
    	private int wordCount;
        public TrieNode() {
            this.arr = new TrieNode[26];
        }
     
    }
 
    public void insert(String word, String url) {
		insert(root,word, url, 0, 0);
	}
	
	private void insert(TrieNode current, String word, String url,
			int wordIndex, int labelIndex) {
		
		while (wordIndex < word.length() 
				&& current.edge[word.charAt(wordIndex) - 'a'] != null) {
			int charIndex = word.charAt(wordIndex) - 'a';
			labelIndex = 0;
			StringBuilder label = current.edge[charIndex];
			
			while (labelIndex < label.length() && wordIndex < word.length()
					&& label.charAt(labelIndex) == word.charAt(wordIndex)) {
				wordIndex++;
				labelIndex++;
			}

			if (labelIndex == label.length()) {
				current = current.next[charIndex];
			} else {
				if (wordIndex == word.length()) { 
					// inserting a prefix of existing word. "face" into "facebook"
					TrieNode oldNext = current.next[charIndex];	// node 'f'
					TrieNode newNext = new TrieNode();
					putData(newNext,url);
					StringBuilder remainingLabel = restString(label, labelIndex);
					int labelAt = remainingLabel.charAt(0) - 'a';
					
					label.setLength(labelIndex); // making "faceboook" as "face"
					current.next[charIndex] = newNext; // new node for "face"
					newNext.next[labelAt] = oldNext;
					newNext.edge[labelAt] = remainingLabel;
				} else { 
					// inserting word which has a partial match with existing word
					TrieNode oldNext = current.next[charIndex];	
					TrieNode newNext = new TrieNode();
					StringBuilder remainingLabel = restString(label, labelIndex);
					StringBuilder remainingWord = restString(word, wordIndex);
					int labelAt = remainingLabel.charAt(0) - 'a';
					int wordAt = remainingWord.charAt(0) - 'a';
					
					label.setLength(labelIndex);
					current.next[charIndex] = newNext;
					newNext.edge[labelAt] = remainingLabel;
					newNext.next[labelAt] = oldNext;
					newNext.edge[wordAt] = remainingWord;
					
					TrieNode temp = newNext.next[wordAt] = new TrieNode();
					putData(temp, url);
				}
				return;
			}
		}//End of while loop

		if (wordIndex < word.length()) { 
			// inserting new node for new word
			int wordAt = word.charAt(wordIndex) - 'a';
			current.edge[wordAt] = restString(word, wordIndex);
			TrieNode temp = current.next[wordAt] = new TrieNode();
			putData(temp, url);
		} else {
			// inserting "there" when "therein" and "thereafter" are existing
			if (current.wordCount == 0){
				current.wordCount = 1;
			}else{
				current.wordCount ++;
			}
			putData(current,url);
		}
	}// End of insert method
	
 
    // Returns if the word is in the trie.
    public boolean search(String word) {
        TrieNode p = searchNode(word);
        if(p==null){
            return false;
        }else{
            if(p.isEnd)
                return true;
        }
 
        return false;
    }
 
    public boolean startsWith(String prefix) {
        TrieNode p = searchNode(prefix);
        if(p==null){
            return false;
        }else{
            return true;
        }
    }
 
    public TrieNode searchNode(String s){
        TrieNode p = root;
        for(int i=0; i<s.length(); i++){
            char c= s.charAt(i);
            int index = c-'a';
            if(p.arr[index]!=null){
                p = p.arr[index];
            }else{
                return null;
            }
        }
 
        if(p==root)
            return null;
 
        return p;
    }
	private StringBuilder restString(CharSequence str, int index) {
		StringBuilder result = new StringBuilder();
		while (index < str.length()){
			result.append(str.charAt(index++));
		}
		return result;
	}
	
	private void putData(TrieNode current, String url) {
		HashMap<String, Integer> h = current.urlsMap;
		if (h.containsKey(url)){
			h.put(url, h.get(url) + 1);
		}else{
			h.put(url, 1);
		}
	}
	public HashMap<String,Integer> get(String word) {
		TrieNode temp = get(root,word,0);
		temp.urlsMap.put("WordCount", temp.wordCount);
		return temp.urlsMap;
	}
	

	private TrieNode get(TrieNode current, String word, int wordIndex) {

		while (wordIndex < word.length()
				&& current.edge[word.charAt(wordIndex) - 'a'] != null) {
			int charIndex = word.charAt(wordIndex) - 'a';
			StringBuilder label = current.edge[charIndex];
			int labelIndex = 0;

			while (wordIndex < word.length() && labelIndex < label.length()) {
				if (word.charAt(wordIndex) != label.charAt(labelIndex)) {
					return null; // character mismatch
				}
				wordIndex++;
				labelIndex++;
			}

			if (labelIndex == label.length() && wordIndex <= word.length()) {
				current = current.next[charIndex]; // traverse further
			} else {
		
				return null;
			}
		}


		if (wordIndex == word.length() && current.wordCount != 0){
			return current;
		}
		return null;
	}
}