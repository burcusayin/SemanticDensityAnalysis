import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ejml.data.DenseMatrix64F;
import org.ejml.factory.DecompositionFactory;
import org.ejml.interfaces.decomposition.SingularValueDecomposition;
import org.ejml.ops.SingularOps;
import org.ejml.simple.SimpleMatrix;

public class Simulation {
	 
	static LuceneMethods lm = new LuceneMethods();
	
	public static void main(String[] args) throws IOException {
		
		ReadFiles rf = new ReadFiles();
		ArrayList<ArrayList<String>> textList = rf.read();
		System.out.println("textlist size:  " + textList.size());
		
		ArrayList<ArrayList<ArrayList<String>>> timeTexts = new ArrayList<ArrayList<ArrayList<String>>>();
		ArrayList<ArrayList<String>> processedTextList = new ArrayList<ArrayList<String>>();
		for(int u = 0; u < 3; u++)
		{
			ArrayList<String> list = textList.get(u);
			ArrayList<ArrayList<String>> processedTextListByTime = new ArrayList<ArrayList<String>>();
			for(int p = 0; p < list.size(); p++)
			{
				ArrayList<String> processedText = lm.tokenizeRemoveStopWordsAndStem(list.get(p));
				processedTextList.add(processedText);
				processedTextListByTime.add(processedText);
			}
			timeTexts.add(processedTextListByTime);
		}
		System.out.println("prtexlist size:  " + processedTextList.size());
		
		// find the frequency of words
		ArrayList<Integer> frequency = new ArrayList<Integer>();
		ArrayList<String> remainingText = new ArrayList<String>();
		
		for(int b = 0; b < processedTextList.size(); b++) 
		{   
			ArrayList<String> text = processedTextList.get(b);
			for(int c = 0; c < text.size(); c++) 
			{
				String term = text.get(c);
				if(!remainingText.contains(term))
				{
					remainingText.add(term);
					frequency.add(Collections.frequency(text, term));
				}
			} 
		}
		
		System.out.println("remaining text size: " + remainingText.size());
		System.out.println("frequency size: " + frequency.size());
		
		// construct the hashmap
		
		HashMap<String, Integer> termFrequencymap = new HashMap<String, Integer>();

		for(int h = 0; h < remainingText.size(); h++)
		{
			termFrequencymap.put(remainingText.get(h), frequency.get(h));
		}
		
		System.out.println("Here hashmap comes!!!!");
		
		printMap(termFrequencymap);
		
		// Sort the hashmap
		
		HashMap<String, Integer> sortedTermFrequencyMap = sortByComparator(termFrequencymap);
		System.out.println("Here sorted hashmap comes!!!!");
	    printMap(sortedTermFrequencyMap);
	    
	    //create content-bearing words - vocabulary pairs and initialize the cooccurence matrix
	    ArrayList<String> sortedTermList = new ArrayList<String>();
	    ArrayList<Integer> sortedFrequencyList = new ArrayList<Integer>();
	   
	    int noOfcontentBearingWords = 1000;
	    int noOfvocabWords = 4000;
	    ArrayList<String> vocabWords = new ArrayList<String>();
	    ArrayList<String> contentBearingWords = new ArrayList<String>();
	    
	    for ( String key : sortedTermFrequencyMap.keySet() ) {
	    	sortedTermList.add(key);
	    	sortedFrequencyList.add(sortedTermFrequencyMap.get(key));
	    }
	    
	    for(int t = 0; t < noOfcontentBearingWords; t++)
	    {
	    	String left = sortedTermList.get(t);
	    	for(int g = 0; g < noOfvocabWords; g++)
	    	{
	    		String right = sortedTermList.get(g);
	    		
	    		if(!vocabWords.contains(right))
	    		{
	    			vocabWords.add(right);
	    		}
	    		if(!contentBearingWords.contains(left))
	    		{
	    			contentBearingWords.add(left);
	    		}
	    		
	    	}
	    }

	    PrintWriter writer = new PrintWriter("C:\\Users\\Burcu\\Downloads\\sdaOutput.txt", "UTF-8");
	    writer.println("Content Bearing Words:");
	    writer.println(contentBearingWords);
	    writer.println();
	    writer.println("Vocabulary Words:");
	    writer.println(vocabWords);
	    writer.println();
	    writer.close();
	   
	    //define the cooccurence matrix 
	    double[][] cooccurenceMatrix = new double[vocabWords.size()][contentBearingWords.size()];
	    
	    //initialize the cooccurence matrix 
	    int rowSize = vocabWords.size();
		int columnSize = contentBearingWords.size();
		int p,t;
		for(p = 0; p < rowSize; p++)
		{
			for(t = 0; t < columnSize; t++)
			{
				cooccurenceMatrix[p][t] = 0.0;
			}
		}
	    
	    // fill the cooccurence matrix and context vectors list 
	    for(int s = 0; s < contentBearingWords.size(); s++)
	    {
	    	String contentBearingWord = contentBearingWords.get(s);
	    	for(int i = 0; i < processedTextList.size(); i++)
	    	{
	    		ArrayList<String> processedText = processedTextList.get(i);
	    		for(int j = 0 ; j < processedText.size(); j++)
	    		{
	    			if(processedText.get(j).equals(contentBearingWord))
	    			{
	    				for(int h = 0; h < vocabWords.size(); h++)
	    				{
	    					
	    					String vocabularyWord = vocabWords.get(h);
	    					for(int k = 0; k < 15; k++)
	    					{
	    						//for 15 reverse control
	    						if((j-k) > 0)
	    						{
	    							if(processedText.get(j-k).equals(vocabularyWord))
	    							{
	    								cooccurenceMatrix[h][s] = cooccurenceMatrix[h][s] + 1.0;
	    							}
	    						}
	    						//for 15 forward control
	    						if((j+k) < processedText.size())
	    						{
	    							if(processedText.get(j+k).equals(vocabularyWord))
	    							{
	    								cooccurenceMatrix[h][s] = cooccurenceMatrix[h][s] + 1.0;
	    							}
	    						}
	    					}
	    				}
	    			}
	    		}
	    	}
	    } 
	    
	    //print the cooccurence matrix
	    PrintWriter writer2 = new PrintWriter(new FileOutputStream(new File("C:\\Users\\Burcu\\Downloads\\sdaOutput.txt"), true)); 
	    writer2.println("Cooccurence Matrix:");
	    writer2.println(Arrays.deepToString(cooccurenceMatrix));
	    writer2.println();
	    writer2.close();
	    
	    //optimize the cooccurence matrix
	    ArrayList<Integer> idfList = new ArrayList<Integer>();
	    int idf = 0;
		for(int i = 0 ; i < columnSize; i++)
		{
			for(p = 0; p < rowSize; p++)
			{
				if(cooccurenceMatrix[p][i] != 0.0)
				{
					idf++;
				}
			}
			idfList.add(idf);
			idf = 0;
		}
	    System.out.println("idf list:" + idfList);
		for(p = 0; p < rowSize; p++)
		{
			for(t = 0; t < columnSize; t++)
			{
				int idfValue = idfList.get(t); 
				if(idfValue == 0)
				{
					idfValue = 1;
				}
				double normalizedValue = cooccurenceMatrix[p][t] * (Math.log(noOfvocabWords+1) - Math.log(idfValue));
				BigDecimal bd = new BigDecimal(normalizedValue);
			    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			    double d = bd.doubleValue();
				cooccurenceMatrix[p][t] = d;
			}
		}
		
		PrintWriter writer3 = new PrintWriter(new FileOutputStream(new File("C:\\Users\\Burcu\\Downloads\\sdaOutput.txt"), true));
	    writer3.println("Optimized Cooccurence Matrix:");
	    writer3.println(Arrays.deepToString(cooccurenceMatrix));
	    writer3.println();
	    writer3.close();
		
		
		System.out.println("row number before svd: " + cooccurenceMatrix.length);
		System.out.println("column number before svd: " + cooccurenceMatrix[0].length);
		
		// Implement Singular Value Decomposition for matrix
		DenseMatrix64F dm = new DenseMatrix64F(cooccurenceMatrix);
		SimpleMatrix simpleDmMatrix = new SimpleMatrix(dm);
		DenseMatrix64F transposedDmMatrix = simpleDmMatrix.transpose().getMatrix();
		SingularValueDecomposition<DenseMatrix64F> svd = DecompositionFactory.svd(transposedDmMatrix.numRows,transposedDmMatrix.numCols,true,true,true);
		 
        if( !svd.decompose(transposedDmMatrix) )
            throw new RuntimeException("Decomposition failed");
 
        DenseMatrix64F U = svd.getU(null,false);
        DenseMatrix64F W = svd.getW(null);
        DenseMatrix64F V = svd.getV(null,false);
        int noOfSingularValues = svd.numberOfSingularValues();
        
     // order singular values from largest to smallest
        SingularOps.descendingOrder(U,false,W,V,false);
        System.out.println("number of singular values: " + noOfSingularValues);
        double decomposedWMatrix[][] = toArray(W);
        SimpleMatrix simpleMatrix = new SimpleMatrix(V);
        SimpleMatrix transposedMatrix = simpleMatrix.transpose();
        DenseMatrix64F transposedDenseMatrix = transposedMatrix.getMatrix();
        double vTranspose[][] = toArray(transposedDenseMatrix);
        
        //truncate W matrix 
        int truncateNumber = 500;
        double truncatedWMatrix[][] = new double[truncateNumber][truncateNumber];
        
        for(int i = 0; i < truncateNumber; i++)
        {
        	for(int j = 0; j < truncateNumber; j++)
        	{
        		truncatedWMatrix[i][j] = decomposedWMatrix[i][j];
        	}
        }
        
        //truncate VT matrix
        double truncatedVTMatrix[][] = new double[truncateNumber][noOfvocabWords];
        for(int i = 0; i < truncateNumber; i++)
        {
        	for(int j = 0; j < noOfvocabWords; j++)
        	{
        		truncatedVTMatrix[i][j] = vTranspose[i][j];
        	}
        }

        //apply multiplication
        DenseMatrix64F dW = new DenseMatrix64F(truncatedWMatrix);
        DenseMatrix64F dVT = new DenseMatrix64F(truncatedVTMatrix);
        SimpleMatrix sW = new SimpleMatrix(dW);
        SimpleMatrix sVT = new SimpleMatrix(dVT);
        SimpleMatrix multiplication =  sW.mult(sVT);
        DenseMatrix64F denseMult = multiplication.transpose().getMatrix();
        double truncatedSVDMatrix[][] = toArray(denseMult);

        for(int i = 0; i < vocabWords.size(); i++)
        {
        	for(int j= 0; j < truncateNumber; j++)
        	{
        		BigDecimal bd = new BigDecimal(truncatedSVDMatrix[i][j]);
			    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			    double d = bd.doubleValue();
        		truncatedSVDMatrix[i][j] = d;
        				
        	}
        }
	    
	    //Here, compare the similarities of some words:
        CosineSimilarity cs = new CosineSimilarity();
	    int indOfFirstWord = vocabWords.indexOf("count");
	    int indOfSecondWord = vocabWords.indexOf("car");
	    double similarity = cs.cosineSimilarity(truncatedSVDMatrix[indOfFirstWord], truncatedSVDMatrix[indOfSecondWord]);
	    
	    //shoulder,arm,sofa,seat
	    int ind1 = vocabWords.indexOf("schnitzel");
	    int ind2 = vocabWords.indexOf("baker");
	    double similarity2 = cs.cosineSimilarity(truncatedSVDMatrix[ind1], truncatedSVDMatrix[ind2]);
	    
	    ind1 = vocabWords.indexOf("letter");
	    ind2 = vocabWords.indexOf("word");
	    double similarity3 = cs.cosineSimilarity(truncatedSVDMatrix[ind1], truncatedSVDMatrix[ind2]);
	    
	    ind1 = vocabWords.indexOf("medicin");
	    ind2 = vocabWords.indexOf("word");
	    double similarity4 = cs.cosineSimilarity(truncatedSVDMatrix[ind1], truncatedSVDMatrix[ind2]);
	    
	    ind1 = vocabWords.indexOf("orangutan");
	    ind2 = vocabWords.indexOf("mountain");
	    double similarity5 = cs.cosineSimilarity(truncatedSVDMatrix[ind1], truncatedSVDMatrix[ind2]);
	    
	    ind1 = vocabWords.indexOf("orangutan");
	    ind2 = vocabWords.indexOf("crocodil");
	    double similarity6 = cs.cosineSimilarity(truncatedSVDMatrix[ind1], truncatedSVDMatrix[ind2]);
	    
	    ind1 = vocabWords.indexOf("girl");
	    ind2 = vocabWords.indexOf("daughter");
	    double similarity7 = cs.cosineSimilarity(truncatedSVDMatrix[ind1], truncatedSVDMatrix[ind2]);
	    
	    ind1 = vocabWords.indexOf("sofa");
	    ind2 = vocabWords.indexOf("seat");
	    double similarity8 = cs.cosineSimilarity(truncatedSVDMatrix[ind1], truncatedSVDMatrix[ind2]);
	   
	    ind1 = vocabWords.indexOf("shoulder");
	    ind2 = vocabWords.indexOf("arm");
	    double similarity9 = cs.cosineSimilarity(truncatedSVDMatrix[ind1], truncatedSVDMatrix[ind2]);
	   
	   
	    PrintWriter writer4 = new PrintWriter(new FileOutputStream(new File("C:\\Users\\Burcu\\Downloads\\sdaOutput.txt"), true));
	    writer4.println("Cooccurence Matrix after SVD:");
	    writer4.println(Arrays.deepToString(truncatedSVDMatrix));
	    writer4.println();
	    writer4.println("Similarity of count and car: " + similarity);
	    writer4.println();
	    writer4.println("Similarity of schnitzel and baker: " + similarity2);
	    writer4.println();
	    writer4.println("Similarity of letter and word: " + similarity3);
	    writer4.println();
	    writer4.println("Similarity of medicin and word: " + similarity4);
	    writer4.println();
	    writer4.println("Similarity of orangutan and mountain: " + similarity5);
	    writer4.println();
	    writer4.println("Similarity of orangutan and crocodile: " + similarity6);
	    writer4.println();
	    writer4.println("Similarity of girl and daughter: " + similarity7);
	    writer4.println();
	    writer4.println("Similarity of sofa and seat: " + similarity8);
	    writer4.println();
	    writer4.println("Similarity of shoulder and arm: " + similarity9);
	    writer4.println();
	    writer4.close();
	   
        //here I'm expecting to find a target word and then apply density analysis

	    DensityAnalysis da = new DensityAnalysis();
	    da.analyzeDensity(timeTexts, vocabWords, "dog");
	    
	}
	
	private static void printMap(HashMap<String, Integer> map)
    {
		Iterator<String> iterator = map.keySet().iterator();
		  
		while (iterator.hasNext()) {
		   String key = iterator.next().toString();
		   String value = map.get(key).toString();
		  
		   System.out.println(key + " " + value);
		}
    }
	
	private static HashMap<String, Integer> sortByComparator(Map<String, Integer> unsortMap)
    {

        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Integer>>()
        {
            public int compare(Entry<String, Integer> o1,
                    Entry<String, Integer> o2)
            {
            	return o2.getValue().compareTo(o1.getValue());
            }
        });

        // Maintaining insertion order with the help of LinkedList
        HashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Entry<String, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
	
	private static double[][] toArray(DenseMatrix64F matrix) 
	{
	    double array[][] = new double[matrix.getNumRows()][matrix.getNumCols()];
	    for (int r=0; r<matrix.getNumRows(); r++)
	    { 
	        for (int c=0; c<matrix.getNumCols(); c++)
	        {
	            array[r][c] = matrix.get(r,c);
	        }
	    }
	    return array;
	}	
	
}
