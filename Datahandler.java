package new_mini;
import java.io.BufferedReader;
import java.io.*;
import java.io.File;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
//import static new_mini.TitleIndexCreation.title_primary;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Datahandler extends DefaultHandler{
   
public static int counter=0;
    public static int filecount=1;
    // defining various flag that can be there in a page
    boolean title=false;
    boolean revision=false;
    boolean id=false;
    boolean text=false;
    boolean page=false;
    StringBuilder textData=new StringBuilder();
    StringBuilder titleData=new StringBuilder();
    StringBuilder idData=new StringBuilder();
    String titleId="";
    
    //startElement method handles various  tags start in the wikidump and set the corresponding flag to true
    //It is a standard method in SAXparser factory
    @Override
    public void startElement(String uri, String localName, String q, Attributes attributes) throws SAXException 
    {
    	//maches the page tag
        if(q.equalsIgnoreCase("page"))
        {
            //After every 5000 page writing data in index file
                counter++;
                if(counter%5000==0&&counter!=1)
                {
                  
                    try {
                        
                        IndexWrite.writeFile();
                         
                    } catch (Exception ex) {
                        Logger.getLogger(Datahandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    filecount++;
                    counter=0;
                }
         
        }
        if(q.equalsIgnoreCase("title")){
            titleData.setLength(0);
            title=true;
        }
        if(q.equalsIgnoreCase("revision")){
            title=false;
            revision=true;
        }
        if(q.equalsIgnoreCase("id")&&!revision){
            revision=true;
            id=true;
        }
        if(q.equalsIgnoreCase("text")){
            textData.setLength(0);
            id=false;
            text=true;
        }
    }
    
    //Matches end of the particular tag and setting flag to false inorder to set it for next page
    @Override
    public void endElement(String uri, String localName, String q) throws SAXException 
    {
        try{
            if(q.equalsIgnoreCase("text")){
                Parser.textparse(textData);
                text=false;
                revision=false;
            }
            if(q.equalsIgnoreCase("id")){
                id=false;
            }
            if(q.equalsIgnoreCase("title")){
                title=false;
            }
            if(q.equalsIgnoreCase("mediawiki")){
               
            }
        }
        catch(Exception e){
            
        }
    }
    
    //This method process every character between the tags
    @Override
    public void characters(char ch[], int start, int length) throws SAXException 
    {
    //in this method based on the set flags we are appending character to that data field  
        if(id){
            idData.setLength(0);
            idData.append(ch, start, length);
           Parser.idparse(idData);
            Parser.titleparse(titleData);
             
            titleId=idData.toString()+"="+titleId+"\n";
           try {
                TitleIndexCreation.storetitle(titleId);
            } catch (IOException ex) {
                Logger.getLogger(Datahandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(text){	//If text flag is set append characters to text data
            
            textData.append(ch,start,length);
        }
        if(title){  //If title flag is set append characters to title data
            
            titleData.append(ch,start,length);
             
             titleId="";
             titleId=titleId+titleData.toString();
        }
    }
    
}

