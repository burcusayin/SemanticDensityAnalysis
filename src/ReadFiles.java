import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

public class ReadFiles { 
	
	 public ArrayList<ArrayList<String>> read() throws IOException {
		    ArrayList<ArrayList<String>> texts = new ArrayList<ArrayList<String>>();
		    
		    //read 1800s
	        String target_dir = "C:\\Users\\...";
	        File dir = new File(target_dir);
	        File[] files = dir.listFiles();
	        
	        ArrayList<String> resultingTextsFor1800 = new ArrayList<String>();

	        for (File f : files) {
	        	String text = null;
	            if(f.isFile()) {
	                BufferedReader inputStream = null;

	                try {
	                    inputStream = new BufferedReader(
	                                    new FileReader(f));
	                    String line;

	                    while ((line = inputStream.readLine()) != null) {
	                        text = text + line;
	                    }
	                }
	                finally {
	                    if (inputStream != null) {
	                        inputStream.close();
	                    }
	                }
	            }
	            resultingTextsFor1800.add(text);
	        }
	        
	      //read 1900s
	        String target_dir2 = "C:\\Users\\...";
	        File dir2 = new File(target_dir2);
	        File[] files2 = dir2.listFiles();
	        
	        ArrayList<String> resultingTextsFor1900 = new ArrayList<String>();

	        for (File f : files2) {
	        	String text = null;
	            if(f.isFile()) {
	                BufferedReader inputStream = null;

	                try {
	                    inputStream = new BufferedReader(
	                                    new FileReader(f));
	                    String line;

	                    while ((line = inputStream.readLine()) != null) {
	                        text = text + line;
	                    }
	                }
	                finally {
	                    if (inputStream != null) {
	                        inputStream.close();
	                    }
	                }
	            }
	            resultingTextsFor1900.add(text);
	        }
	        
	      //read 2000s
	        String target_dir3 = "C:\\Users\\...";
	        File dir3 = new File(target_dir3);
	        File[] files3 = dir3.listFiles();
	        
	        ArrayList<String> resultingTextsFor2000 = new ArrayList<String>();

	        for (File f : files3) {
	        	String text = null;
	            if(f.isFile()) {
	                BufferedReader inputStream = null;

	                try {
	                    inputStream = new BufferedReader(
	                                    new FileReader(f));
	                    String line;

	                    while ((line = inputStream.readLine()) != null) {
	                        text = text + line;
	                    }
	                }
	                finally {
	                    if (inputStream != null) {
	                        inputStream.close();
	                    }
	                }
	            }
	            resultingTextsFor2000.add(text);
	        }
	        
	        texts.add(resultingTextsFor1800);
	        texts.add(resultingTextsFor1900);
	        texts.add(resultingTextsFor2000);
	        
	        return texts;
	    }

}
