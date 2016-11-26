/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package new_mini;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map.Entry;

public class IndexWrite 
{
	public  static void writeFile()  throws Exception
	    {
	    	try
	    	{
			// if index file doesnt exists, then create it
			if (!Indexp.file.exists()) {
				Indexp.file.createNewFile();
			}
			// setting the BufferedWriter to a particular index file since we cant process index from single file  
			//writing 5000 page data in one index file than next 5000 pade in other
			FileWriter fwrite = new FileWriter(Datahandler.filecount+".txt");
			BufferedWriter bwrite = new BufferedWriter(fwrite);
		
		// iterating over the map and storing word to docid mapping in particular format in the above created index file
		// structure of entry in index file=  word-docid=Ttiltlecount Ccategorycount;
		//here T B C represent various tags in which we are storing count of word in particular doc
		
	    	for (Entry<String, HashMap<String, Define>> mainiter : Indexp.indexmap.entrySet()) 
	    	{
	    		String word=mainiter.getKey();
                        HashMap<String,Define> iner = new HashMap<String,Define>(mainiter.getValue());
	    		bwrite.write(word+"-");
                        for(Entry<String,Define> ineriter:iner.entrySet())
	    		{
	    			String doc = ineriter.getKey();
	    			Define object = ineriter.getValue();
	    			
                                String append="" ;
	    			append = append+doc+"=";
                                if(object.title!=0)
	    				append=append+"T"+object.title;
	    			if(object.body!=0)
	    				append=append+"B"+object.body;
	    			if(object.cat!=0)
	    				append=append+"C"+object.cat;
	    			if(object.info!=0)
	    				append=append+"I"+object.info;
                                if(object.ext!=0)
	    				append=append+"E"+object.ext;
                                if(object.ref!=0)
	    				append=append+"R"+object.ref;
	    	              append=append+";";
                              bwrite.write(append);
                              // System.out.print("Here");
	    		}
	    		bwrite.write("\n");
	    	}
		//Closing the index file and also clearing the map
			bwrite.close();
			Indexp.indexmap.clear();
		
		
		}catch(Exception e){}
		}

}
