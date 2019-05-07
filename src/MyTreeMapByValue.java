import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

public class MyTreeMapByValue {
	

	public static TreeMap<String, Integer> sortValue(HashMap<String, Integer> hmap) {
		Comparator<String> comp = new ValueComparator(hmap);
		TreeMap<String,Integer> tree = new TreeMap<>(comp);
		tree.putAll(hmap);
		return tree;
	}
	
	private static class ValueComparator implements Comparator<String> {
		
		private HashMap<String, Integer> hmap = new HashMap<>();
		
		public ValueComparator(HashMap<String, Integer> hmap) {
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
