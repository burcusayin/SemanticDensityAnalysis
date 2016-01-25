import java.io.IOException;
import java.io.StringReader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.core.LetterTokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;

import java.util.*;

public class LuceneMethods extends Analyzer {
	 	 
	   @Override
	   protected TokenStreamComponents createComponents(String fieldName) {
	     return new TokenStreamComponents(new LetterTokenizer());
	   }
	   
	   public ArrayList<String> tokenizeRemoveStopWordsAndStem(String text) throws IOException {

		   
		   ArrayList<String> tokenList = new ArrayList<String>();
		   CharArraySet stopWords = EnglishAnalyzer.getDefaultStopSet();
	      
		   @SuppressWarnings("resource")
		   LuceneMethods analyzer = new LuceneMethods();
		   TokenStream stream = analyzer.tokenStream("field", new StringReader(text));
		   stream = new LowerCaseFilter(stream);
		   stream = new StopFilter(stream, stopWords);
		   stream = new PorterStemFilter(stream);
	     
		   // get the CharTermAttribute from the TokenStream
		   CharTermAttribute termAtt = stream.addAttribute(CharTermAttribute.class);
	 
		   try {
			   stream.reset();
			   // print all tokens until stream is exhausted
			   while (stream.incrementToken()) {
				   tokenList.add(termAtt.toString());
			   }
	     
			   stream.end();
		   } finally {
			   stream.close();
		   }
		   
		   ArrayList<String> finalTokenList = new ArrayList<String>();
		   for(int i = 0; i < tokenList.size(); i++)
		   {
			   if((tokenList.get(i).length() > 1))
			   {
				   finalTokenList.add(tokenList.get(i)) ;
			   }
		   }
		   return finalTokenList;
	   }
}