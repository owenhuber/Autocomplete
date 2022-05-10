import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class HashListAutocomplete implements Autocompletor {

    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;
    private int mySize;


    public HashListAutocomplete(String[] terms, double[] weights) {
    
        if (terms == null || weights == null){
            throw new NullPointerException("One or more arguments null");
            }

        initialize(terms, weights);
        
    }

    @Override
    public List<Term> topMatches(String prefix, int k) {
        // DONE Auto-generated method stub
        if (prefix.length() >= MAX_PREFIX){
            prefix = prefix.substring(0,MAX_PREFIX);
        }
        if (k == 0){
            return new ArrayList<>();
        }
        if (!(myMap.containsKey(prefix))){
			return new ArrayList<Term>();
        }
        List<Term> thePre = myMap.get(prefix);
		List<Term> ret3 = thePre.subList(0, Math.min(k, thePre.size()));
		return ret3;
    }

    @Override
    public void initialize(String[] terms, double[] weights) {
        // DONE Auto-generated method stub
        myMap = new HashMap<String, List<Term>>();
		for (int i = 0; i < terms.length; i++){
			String dogS = terms[i];
            double theWs = weights[i];
            Term theT = new Term(dogS, theWs);
			for (int j = 0; j <= Math.min(MAX_PREFIX, dogS.length()); j++) {
				String sub1 = dogS.substring(0, j);
				myMap.putIfAbsent(sub1, new ArrayList<Term>());
				myMap.get(sub1).add(theT);
				}
			}
		
		for (String cat : myMap.keySet()) {
			Collections.sort(myMap.get(cat), Comparator.comparing(Term::getWeight).reversed());
		}
        
    }

    @Override
    public int sizeInBytes() {
        // DONE Auto-generated method stub
        if (mySize == 0) {
			
			for(String s : myMap.keySet()) {
			    mySize += s.length() * BYTES_PER_CHAR;;	
                List<Term> theST = myMap.get(s);
				for (int i = 0; i < theST.size(); i++) {
					Term theT2 = theST.get(i);
					mySize = mySize + BYTES_PER_DOUBLE + BYTES_PER_CHAR*theT2.getWord().length();
				}
	
			}
		}
		return mySize;
    }
    
}

