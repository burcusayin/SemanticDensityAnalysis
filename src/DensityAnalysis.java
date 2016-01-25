import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class DensityAnalysis { 
	
	public void analyzeDensity(ArrayList<ArrayList<ArrayList<String>>> textList, ArrayList<String> vocabWords, String targetWord)
	{
		ArrayList<double[]> contextVectorsListFor1800 = new ArrayList<double[]>();
		ArrayList<ArrayList<String>> textsFor1800 = textList.get(0);
		
		for(int i = 0; i < textsFor1800.size(); i++)
		{
			ArrayList<String> processingText = textsFor1800.get(i);
			for(int j = 0 ; j < processingText.size(); j++)
    		{
				if(processingText.get(j).equals(targetWord))
    			{
					double[] contextVector = new double[vocabWords.size()];
    				for(int q= 0; q < vocabWords.size(); q++)
    				{
    					contextVector[q] = 0.0;
    				}
					for(int h = 0; h < vocabWords.size(); h++)
    				{
    					String vocabularyWord = vocabWords.get(h);
    					for(int k = 0; k < 15; k++)
    					{
    						//for 15 reverse control
    						if((j-k) > 0)
    						{
    							if(processingText.get(j-k).equals(vocabularyWord))
    							{
    								contextVector[h] = contextVector[h] + 1.0;
    							}
    						}
    						//for 15 forward control
    						if((j+k) < processingText.size())
    						{
    							if(processingText.get(j+k).equals(vocabularyWord))
    							{
    								contextVector[h] = contextVector[h] + 1.0;
    							}
    						}
    					}
    				}
					contextVectorsListFor1800.add(contextVector);
    			}
    		}
		}
		System.out.println("contextVectorsListFor1800 size:" + contextVectorsListFor1800.size());
		
		PrintWriter writer;
		try {
			writer = new PrintWriter("C:\\Users\\Burcu\\Downloads\\daOutput.txt", "UTF-8");
			writer.println("contextVectorsListFor1800:");
			for(int x = 0; x < contextVectorsListFor1800.size(); x++)
			{
				writer.print(Arrays.toString(contextVectorsListFor1800.get(x)) + " , ");
			}
		    writer.println();
		    writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// shuffle list
	    Collections.shuffle(contextVectorsListFor1800);

	    // adding defined amount of numbers to target list
	    ArrayList<double[]> targetList1800 = new ArrayList<double[]>();
	    Random random = new Random();
	    ArrayList<Integer> selectionIndex = new ArrayList<Integer>();
	    for (int j = 0; j < (contextVectorsListFor1800.size()/30); j++) 
	    {
	    	int s;
	    	double[] mtx;
	    	do
	    	{
	    		s = random.nextInt(contextVectorsListFor1800.size());
	    		mtx = contextVectorsListFor1800.get(s);
	    	}while(selectionIndex.contains(s) || checkZero(mtx));
	    	selectionIndex.add(s);
	        targetList1800.add(contextVectorsListFor1800.get(s)); 
	    }
	    
	    PrintWriter writer2;
		try {
			writer2 = new PrintWriter(new FileOutputStream(new File("C:\\Users\\Burcu\\Downloads\\daOutput.txt"), true));
			writer2.println("Sampled contextVectorsListFor1800:");
			for(int x = 0; x < targetList1800.size(); x++)
			{
				writer2.print(Arrays.toString(targetList1800.get(x)) + " , ");
			}
		    writer2.println();
		    writer2.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ArrayList<double[]> contextVectorsListFor1900 = new ArrayList<double[]>();
		ArrayList<ArrayList<String>> textsFor1900 = textList.get(1);
		
		for(int i = 0; i < textsFor1900.size(); i++)
		{
			ArrayList<String> processingText = textsFor1900.get(i);
			for(int j = 0 ; j < processingText.size(); j++)
    		{
				if(processingText.get(j).equals(targetWord))
    			{
					double[] contextVector = new double[vocabWords.size()];
    				for(int q= 0; q < vocabWords.size(); q++)
    				{
    					contextVector[q] = 0.0;
    				}
					for(int h = 0; h < vocabWords.size(); h++)
    				{
    					String vocabularyWord = vocabWords.get(h);
    					for(int k = 0; k < 15; k++)
    					{
    						//for 15 reverse control
    						if((j-k) > 0)
    						{
    							if(processingText.get(j-k).equals(vocabularyWord))
    							{
    								contextVector[h] = contextVector[h] + 1.0;
    							}
    						}
    						//for 15 forward control
    						if((j+k) < processingText.size())
    						{
    							if(processingText.get(j+k).equals(vocabularyWord))
    							{
    								contextVector[h] = contextVector[h] + 1.0;
    							}
    						}
    					}
    				}
					contextVectorsListFor1900.add(contextVector);
    			}
    		}
		}
		
		System.out.println("contextVectorsListFor1900 size:" + contextVectorsListFor1900.size());
		
		PrintWriter writer3;
		try {
			writer3 = new PrintWriter(new FileOutputStream(new File("C:\\Users\\Burcu\\Downloads\\daOutput.txt"), true));
			writer3.println("contextVectorsListFor1900:");
			for(int x = 0; x < contextVectorsListFor1900.size(); x++)
			{
				writer3.print(Arrays.toString(contextVectorsListFor1900.get(x)) + " , ");
			}
		    writer3.println();
		    writer3.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// shuffle list
	    Collections.shuffle(contextVectorsListFor1900);

	    // adding defined amount of numbers to target list
	    ArrayList<double[]> targetList1900 = new ArrayList<double[]>();
	    Random random2 = new Random();
	    ArrayList<Integer> selectionIndex2 = new ArrayList<Integer>();
	    for (int j = 0; j < (contextVectorsListFor1900.size()/30); j++) 
	    {
	    	int s;
	    	double[] mtx;
	    	do
	    	{
	    		s = random2.nextInt(contextVectorsListFor1900.size());
	    		mtx = contextVectorsListFor1900.get(s);
	    	}while(selectionIndex2.contains(s) || checkZero(mtx));
	    	selectionIndex2.add(s);
	        targetList1900.add(contextVectorsListFor1900.get(s)); 
	    }
	    
	    PrintWriter writer4;
		try {
			writer4 = new PrintWriter(new FileOutputStream(new File("C:\\Users\\Burcu\\Downloads\\daOutput.txt"), true));
			writer4.println("Sampled contextVectorsListFor1900:");
			for(int x = 0; x < targetList1900.size(); x++)
			{
				writer4.print(Arrays.toString(targetList1900.get(x)) + " , ");
			}
		    writer4.println();
		    writer4.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ArrayList<double[]> contextVectorsListFor2000 = new ArrayList<double[]>();
		ArrayList<ArrayList<String>> textsFor2000 = textList.get(2);
		
		for(int i = 0; i < textsFor2000.size(); i++)
		{
			ArrayList<String> processingText = textsFor2000.get(i);
			for(int j = 0 ; j < processingText.size(); j++)
    		{
				if(processingText.get(j).equals(targetWord))
    			{
					double[] contextVector = new double[vocabWords.size()];
    				for(int q= 0; q < vocabWords.size(); q++)
    				{
    					contextVector[q] = 0.0;
    				}
					for(int h = 0; h < vocabWords.size(); h++)
    				{
    					String vocabularyWord = vocabWords.get(h);
    					for(int k = 0; k < 15; k++)
    					{
    						//for 15 reverse control
    						if((j-k) > 0)
    						{
    							if(processingText.get(j-k).equals(vocabularyWord))
    							{
    								contextVector[h] = contextVector[h] + 1.0;
    							}
    						}
    						//for 15 forward control
    						if((j+k) < processingText.size())
    						{
    							if(processingText.get(j+k).equals(vocabularyWord))
    							{
    								contextVector[h] = contextVector[h] + 1.0;
    							}
    						}
    					}
    				}
					contextVectorsListFor2000.add(contextVector);
    			}
    		}
		}
		System.out.println("contextVectorsListFor2000 size:" + contextVectorsListFor2000.size());
		
		PrintWriter writer5;
		try {
			writer5 = new PrintWriter(new FileOutputStream(new File("C:\\Users\\Burcu\\Downloads\\daOutput.txt"), true));
			writer5.println("contextVectorsListFor2000:");
			for(int x = 0; x < contextVectorsListFor2000.size(); x++)
			{
				writer5.print(Arrays.toString(contextVectorsListFor2000.get(x)) + " , ");
			}
		    writer5.println();
		    writer5.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// shuffle list
	    Collections.shuffle(contextVectorsListFor2000);

	    // adding defined amount of numbers to target list
	    ArrayList<double[]> targetList2000 = new ArrayList<double[]>();
	    Random random3 = new Random();
	    ArrayList<Integer> selectionIndex3 = new ArrayList<Integer>();
	    for (int j = 0; j < (contextVectorsListFor2000.size()/30); j++) 
	    {
	    	int s;
	    	double[] mtx;
	    	do
	    	{
	    		s = random3.nextInt(contextVectorsListFor2000.size());
	    		mtx = contextVectorsListFor2000.get(s);
	    	}while(selectionIndex3.contains(s) || checkZero(mtx));
	    	selectionIndex3.add(s);
	    	targetList2000.add(contextVectorsListFor2000.get(s)); 
	    }
	    
	    PrintWriter writer6;
		try {
			writer6 = new PrintWriter(new FileOutputStream(new File("C:\\Users\\Burcu\\Downloads\\daOutput.txt"), true));
			writer6.println("Sampled contextVectorsListFor2000:");
			for(int x = 0; x < targetList2000.size(); x++)
			{
				writer6.print(Arrays.toString(targetList2000.get(x)) + " , ");
			}
		    writer6.println();
		    writer6.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//find average cosine similarity for group 1800
		CosineSimilarity cs = new CosineSimilarity();
		double averageSim1800 = 0.0;
		for(int i = 0; i < targetList1800.size(); i++)
		{
			for(int j = 0; j < targetList1800.size(); j++)
			{
				if(i != j)
				{
					double similarity = cs.cosineSimilarity(targetList1800.get(i),targetList1800.get(j));
					averageSim1800 = averageSim1800 + similarity;
				}
			}
		}
		System.out.println("averageSim1800 total:" + averageSim1800);
		if(averageSim1800 != 0.0)
		{
			averageSim1800 = (averageSim1800 / (targetList1800.size() * (targetList1800.size()-1)));
		}
		System.out.println("averageSim1800 :" + averageSim1800);
		
		//find average cosine similarity for group 1900
		double averageSim1900 = 0.0;
		for(int i = 0; i < targetList1900.size(); i++)
		{
			for(int j = 0; j < targetList1900.size(); j++)
			{
				if(i != j)
				{
					double similarity = cs.cosineSimilarity(targetList1900.get(i),targetList1900.get(j));
					averageSim1900 = averageSim1900 + similarity;
				}
			}
		}
		System.out.println("averageSim1900 total:" + averageSim1900);
		if(averageSim1900 != 0.0)
		{
			averageSim1900 = (averageSim1900 / (targetList1900.size() * (targetList1900.size() - 1)));
		}
		System.out.println("averageSim1900:" + averageSim1900);
		
		//find average cosine similarity for group 2000
		double averageSim2000 = 0.0;
		for(int i = 0; i < targetList2000.size(); i++)
		{
			for(int j = 0; j < targetList2000.size(); j++)
			{
				if(i != j)
				{
					double similarity = cs.cosineSimilarity(targetList2000.get(i),targetList2000.get(j));
					averageSim2000 = averageSim2000 + similarity;
				}
			}
		}
		System.out.println("averageSim2000 total:" + averageSim2000);
		if(averageSim2000 != 0.0)
		{
			averageSim2000 = (averageSim2000 / (targetList2000.size() * (targetList2000.size() - 1)));
		}
		System.out.println("averageSim2000:" + averageSim2000);
		
		
		BigDecimal bd = new BigDecimal(averageSim1800);
	    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
	    averageSim1800 = bd.doubleValue();
	    BigDecimal bd2 = new BigDecimal(averageSim1900);
	    bd2 = bd2.setScale(2, BigDecimal.ROUND_HALF_UP);
	    averageSim1900 = bd2.doubleValue();
	    BigDecimal bd3 = new BigDecimal(averageSim2000);
	    bd3 = bd3.setScale(2, BigDecimal.ROUND_HALF_UP);
	    averageSim2000 = bd3.doubleValue();
	    
	    
		System.out.println("average similarity for 1800s: " + averageSim1800);
		System.out.println("average similarity for 1900s: " + averageSim1900);
		System.out.println("average similarity for 2000s: " + averageSim2000);
		
		PrintWriter writer7;
		try {
			writer7 = new PrintWriter(new FileOutputStream(new File("C:\\Users\\Burcu\\Downloads\\daOutput.txt"), true));
			writer7.println("average similarity for 1800s:");
			writer7.println(averageSim1800);
		    writer7.println();
		    writer7.println("average similarity for 1900s:");
			writer7.println(averageSim1900);
			writer7.println();
			writer7.println("average similarity for 2000s:");
			writer7.println(averageSim2000);
			writer7.println();
		    writer7.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private boolean checkZero(double[] array)
	{
		int size = array.length;
		for(int i=0; i < size; i++)
		{
			if(array[i] != 0.0)
			{
				return false;
			}
		}
		return true;
	}

}
