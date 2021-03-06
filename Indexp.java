/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package new_mini;
import java.io.File;
import java.io.FileNotFoundException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;

// IN This class will open and read the wiki dump and also open primary secondary and tertiary index file 
// We will instantiate SAXParserFactory which will parse input file and call the datahandler

public class Indexp {
    public static File file ;
    // A set that stores stopwords
    public static Set<String> stopwordsarr = new HashSet<String>();
    static Scanner scan=null;
    //A hash map which has word mapping to various docid and count of word in various docs 
    public static Map<String, HashMap<String, Define>> indexmap= new TreeMap<>();   
    public static File ff1,ff2,ff3;
    public static FileWriter fw1,fw2,fw3;
    public static BufferedWriter bw1,bw2,bw3;
    public static void main(String[] args) throws IOException {
    
    	//creating and opening various level of index file and also setting their BufferedWriter objects 
        try{
                    ff1=new File("TitlePrimary");
                    ff2=new File("TitleSecondary");
                    ff3=new File("TitleTertiary");
                    
                  
        if(!ff1.exists())
            ff1.createNewFile();
        if(!ff2.exists())
            ff2.createNewFile();
        if(!ff3.exists())
            ff3.createNewFile();
            
          fw1 = new FileWriter(ff1.getAbsoluteFile());
        bw1 = new BufferedWriter(fw1);
        fw2 = new FileWriter(ff2.getAbsoluteFile());
         bw2 = new BufferedWriter(fw2);
         fw3 = new FileWriter(ff3.getAbsoluteFile());
        bw3 = new BufferedWriter(fw3);
        
             file = new File(args[0]);//Databse file name
          //Scan the file containing stopword and add it to a set   	
            scan= new Scanner(new File("stopwords.txt"));   
            scan.useDelimiter("\n");
            while(scan.hasNext()){
                stopwordsarr.add(scan.next());
            }
            
           // By using SAXParserFactory we read the XML file with name given as command line argument
            SAXParserFactory Factory = SAXParserFactory.newInstance();
            SAXParser saxParser = Factory.newSAXParser();
            Datahandler handle = new Datahandler();
            saxParser.parse(new File(args[1]), handle);//parsing xml file using saxparser
        }
        catch(Exception e){
            
        }
        bw1.close();
        bw2.close();
        bw3.close();
    }
    
}

// here we define a class which stores the count of a word in a document in various tags 		
class Define {
    int title;
    int body;
    int info;
    int cat;
    int ext;
    int ref;
	Define()
	{
		this.title=0;
		this.body=0;
		this.info=0;
		this.cat=0;
                this.ext=0;
                this.ref=0;
	}
}
