package cs600finalproject.yangyangliu;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.lang.model.util.Elements;
import javax.swing.text.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import opennlp.tools.tokenize.TokenizerME; 
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer; 
/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
//	public static void main(String args[]) throws Exception{     
//         
//          String sentence = "Hi. How are you? Welcome to Tutorialspoint. " 
//                + "We provide free tutorials on various technologies"; 
//           
//          //Loading the Tokenizer model 
//          InputStream inputStream = new FileInputStream("C:/OpenNLP_models/en-token.bin"); 
//          TokenizerModel tokenModel = new TokenizerModel(inputStream); 
//           
//          //Instantiating the TokenizerME class 
//          TokenizerME tokenizer = new TokenizerME(tokenModel); 
//           
//          //Tokenizing the given raw text 
//          String tokens[] = tokenizer.tokenize(sentence);       
//              
//          //Printing the tokens  
//          for (String a : tokens) 
//             System.out.println(a); 
//    } 
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public AppTest( String testName )
	{
	    super( testName );
	} 
    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     * @throws IOException 
     */
    public void testApp() throws IOException
    {
    	String sentence = "Hello, I'm Yangyang Liu from Stevens Institute of Technology and this is my cs600 project. Hope I could get a high score. Best Wishes"; 
           
        //Loading the Tokenizer model 
        InputStream inputStream = new FileInputStream("en-token.bin"); 
        TokenizerModel tokenModel = new TokenizerModel(inputStream); 
           
        //Instantiating the TokenizerME class 
        TokenizerME tokenizer = new TokenizerME(tokenModel); 
           
        //Tokenizing the given raw text 
        String tokens[] = tokenizer.tokenize(sentence);       
        System.out.println("-----------------\n");    
        //Printing the tokens  
        for (String a : tokens) 
        	System.out.println(a); 
        
        System.out.println("-----------------\n");
      //Instantiating whitespaceTokenizer class 
        WhitespaceTokenizer whitespaceTokenizer = WhitespaceTokenizer.INSTANCE;  
        
       //Tokenizing the given paragraph 
       String tokens1[] = whitespaceTokenizer.tokenize(sentence);  
        
       //Printing the tokens 
       for(String token : tokens1)     
          System.out.println(token); 
       
       System.out.println("-----------------\n");
       

       org.jsoup.select.Elements newsHeadlines = Jsoup.connect("http://en.wikipedia.org/").get().select("#mp-itn b a");
       for (Element headline : newsHeadlines) {
    	   System.out.println(headline.attr("title"));
    	   System.out.println(headline.absUrl("href"));
       }
    }
}
