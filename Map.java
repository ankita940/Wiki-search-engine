/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package new_mini;



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

//In this class we will map the word store the word into map ie. indexp
public class Map {
    
  // processing a word which is recieved as stringBulider and type represent its tag
   static void addxyz(StringBuilder str,int type){
        try{
        //converting to string than to lowercase and than trimming	
            String ttl=str.toString().toLowerCase().trim();	
         // if the word has length less than 2 or if a stopword than ignoring it
            if(ttl.length()<=2||Indexp.stopwordsarr.contains(ttl))
                return;
           // Stemming the word in its root form using Porterstemmer     
            PorterStemmer s = new PorterStemmer();
    	    s.add(ttl.toCharArray(),ttl.length());
    	    String str1=s.stem();
    	
    	    
    	// updating the entry of word in map and count of tag in which it is present 	
	    if(Indexp.indexmap.get(str1)==null)
			{
	        	HashMap <String,Define> lmap=new HashMap<String,Define>();
	        	Indexp.indexmap.put(str1, lmap);
	        	}
	        
	        if(Indexp.indexmap.get(str1)==null||!Indexp.indexmap.get(str1).containsKey(Parser.uid))
	        {
	        	Indexp.indexmap.get(str1).put(Parser.uid, new Define());
	        }
                if(type==1)	
	        Indexp.indexmap.get(str1).get(Parser.uid).title++;
                else if(type==2)	
	        Indexp.indexmap.get(str1).get(Parser.uid).body++;
                else if(type==3)	
	        Indexp.indexmap.get(str1).get(Parser.uid).info++;
                else if(type==4)	
	        Indexp.indexmap.get(str1).get(Parser.uid).cat++;
                else if(type==5)	
	        Indexp.indexmap.get(str1).get(Parser.uid).ref++;
                else if(type==6)	
	        Indexp.indexmap.get(str1).get(Parser.uid).ext++;
        }
        catch(Exception e){
            
        } 
}
}


