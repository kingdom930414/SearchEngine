package cs600finalproject.yangyangliu;
import java.util.HashMap;

class Trie {
	private final Node root = new Node(0);   
    private static class Node {
    	protected HashMap<String,Integer> urlsMap = new HashMap<>();
    	protected Node[] next = new Node[26];
    	protected StringBuilder[] edge = new StringBuilder[26];
    	protected int wordCount;
		
		private Node(int wordCount) {
			this.wordCount = wordCount;
		}
    }
    

    public void insert(String word, String url) {
        Node trav = root;
        int i = 0;

        while (i < word.length() && trav.edge[word.charAt(i) - 'a'] != null) {
            int index = word.charAt(i) - 'a', j = 0;
            StringBuilder label = trav.edge[index];

            while (j < label.length() && i < word.length() && label.charAt(j) == word.charAt(i)) {
                ++i;
                ++j;
            }

            if (j == label.length()) {
                trav = trav.next[index];
            } else {
                if (i == word.length()) {  
         
                	setChild( trav, index,  url, label, j, url);
                } else {     
                	
                	pushChild( trav, index,  url, label, j, i, word);
                }

                return;
            }
        }

        if (i < word.length()) {   
            trav.edge[word.charAt(i) - 'a'] = strCopy(word, i);
            Node temp = trav.next[word.charAt(i) - 'a'] = new Node(1);
            putData(temp, url);
        } else {    
        	if (trav.wordCount == 0){
        		trav.wordCount = 1;
			}else{
				trav.wordCount ++;
			}
			putData(trav,url);
        }
    }
    
    protected void setChild(Node trav,int index, String url,StringBuilder label,int j,String word){
    	 Node existingChild = trav.next[index];
         Node newChild = new Node(1);
         putData(newChild,url);
         StringBuilder remainingLabel = strCopy(label, j);

         label.setLength(j);  
         trav.next[index] = newChild; 
         newChild.next[remainingLabel.charAt(0) - 'a'] = existingChild;
         newChild.edge[remainingLabel.charAt(0) - 'a'] = remainingLabel;
    }
    
    protected void pushChild(Node trav,int index, String url,StringBuilder label,int j,int i,String word){
    	StringBuilder remainingLabel = strCopy(label, j);
        Node newChild = new Node(0);
        StringBuilder remainingWord = strCopy(word, i);
        Node temp = trav.next[index];

        label.setLength(j);
        trav.next[index] = newChild;
        newChild.edge[remainingLabel.charAt(0) - 'a'] = remainingLabel;
        newChild.next[remainingLabel.charAt(0) - 'a'] = temp;
        newChild.edge[remainingWord.charAt(0) - 'a'] = remainingWord;
        newChild.next[remainingWord.charAt(0) - 'a'] = new Node(1);
        putData(temp,url);
    }
    
	protected void putData(Node current, String url) {
		HashMap<String, Integer> h = current.urlsMap;
		if (h.containsKey(url)){
			h.put(url, h.get(url) + 1);
		}else{
			h.put(url, 1);
		}
	}

    private StringBuilder strCopy(CharSequence str, int index) {
        StringBuilder result = new StringBuilder(100);

        while (index != str.length()) {
            result.append(str.charAt(index++));
        }

        return result;
    }

    public void print() {
        printUtil(root, new StringBuilder());
    }

    private void printUtil(Node node, StringBuilder str) {
        if (node.next[25] != null) {
            System.out.println(str);
        }

        for (int i = 0; i < node.edge.length; ++i) {
            if (node.edge[i] != null) {
                int length = str.length();

                str = str.append(node.edge[i]);
                printUtil(node.next[i], str);
                str = str.delete(length, str.length());
            }
        }
    }

    public Node search( String word, int wordIndex) {
        int i = wordIndex;
        Node trav = root;

        while (i < word.length() && trav.edge[word.charAt(i) - 'a'] != null) {
            int index = word.charAt(i) - 'a';
            StringBuilder label = trav.edge[index];
            int j = 0;

            while (i < word.length() && j < label.length()) {
                if (word.charAt(i) != label.charAt(j)) {
                    return null;
                }

                i++;
                j++;
            }

            if (j == label.length() && i <= word.length()) {
                trav = trav.next[index];   
            } else {
                return null;
            }
        }

        if(i == word.length() && trav.wordCount != 0){
        	return trav;
        }
        return null;
    }

    public boolean startsWith(String prefix) {
        int i = 0;
        Node trav = root;

        while (i < prefix.length() && trav.edge[prefix.charAt(i) - 'a'] != null) {
            int index = prefix.charAt(i) - 'a';
            StringBuilder label = trav.edge[index];
            int j = 0;

            while (i < prefix.length() && j < label.length()) {
                if (prefix.charAt(i) != label.charAt(j)) {
                    return false; 
                }

                ++i;
                ++j;
            }

            if (j == label.length() && i <= prefix.length()) {
                trav = trav.next[index];  
            } else {
                return true;
            }
        }

        return i == prefix.length();
    }
    
    public HashMap<String,Integer> get(String word) {
    	Node temp = search(word,0);
    	temp.urlsMap.put("WordCount", temp.wordCount);
    	return temp.urlsMap;
    }
}
