
package new_mini;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map.Entry;

// In this class we parse the id, tiltle ,text and after getting a word we will call the addxyz function of Map class that will map that word into the hashmap
public class Parser {
    // making stringbuilder for words that belong to deiiferent tag
    public static String uid=null;
    static StringBuilder stitle = new StringBuilder();
    static StringBuilder stext = new StringBuilder();
    static StringBuilder lbody=new StringBuilder();
    static StringBuilder lcat = new StringBuilder();
    static StringBuilder lstr = new StringBuilder();//for infobox
    static StringBuilder lext = new StringBuilder();
    static StringBuilder lref = new StringBuilder();
    public static boolean freference=false;
    public static boolean fexternal=false;
    public static int tt=0; 
    //These are various tags inside text body which we want to ignore so we first match this string and than reject data present in thses tags	
                    static String s1="{infobox";
                    static   String s2="{cite";
                     static   String s3="{gr";
                     static   String s4="{coord";
                     static   String s9="{geobox";
                     static   String s5 ="[Category:";
                     static   String s6="[image:";
                     static   String s7="[file:";
                     static   String s8="gallery";
    
    // Handling doc id
    static void idparse(StringBuilder str){
        uid="";
        uid=""+str.toString();
        
    }
    //Handling tiltle
    static void titleparse(StringBuilder str){
        
        int l=str.length();
        stitle.setLength(0);
        for(int i=0;i<l;i++){
            char cur = str.charAt(i);
            if((cur>='A'&&cur<='Z')||(cur>=97&&cur<=122)||(cur>='0'&&cur<='9'))
            {
        	stitle.append(cur);
            }
            else
            {
                tt=2;
                Map.addxyz(stitle, tt);
        	//Map.addtt(stitle);
        	stitle.setLength(0);
            }
        }
    }
    
    // handling data inside various tags which we want to ignore 
    public static int ignore(String s,StringBuilder data,int i,int len)
{
	int count=2;
    				i=i+s.length()+1;
    				boolean fcite=true;
    				while(fcite&&i<len)
    				{
    					char cur = data.charAt(i);
    					//System.out.print(cur);
    					if(cur=='{'||cur=='[')
    						count++;
    					else if(cur=='}'||cur==']')
    						count--;
    					//System.out.print(cur);
    					if(count==0||i>=len)
    					{
    						fcite=false;
    						i++;
    						break;
    						
    					}
    					i++;
    				}
return i;
}
  //In this we process data inside text tag and if we ignore various sub tags as listed above and then parese the data nd collect words then call the add to map function in Map class with the tag name in which word found  
    static void textparse(StringBuilder data) throws Exception{
        
    	//Parser
    	 
    	try{
        int i=0;
        
    	boolean finfo = false;
        int len = data.length();
       
        while(i<len)
    	{
            
           if(data.charAt(i)=='\n'&&(fexternal||freference))
                {
                    if(fexternal)
                    fexternal=false;
                    if(freference)
                    freference=false;
                }
    		//check for info-box
                
                else if(data.charAt(i)=='{'&&!fexternal)
    		{
                    if(i+9<len&&data.substring(i+1, i+9).equalsIgnoreCase(s1))
    			{
                            fexternal=false;
                            freference=false;
                                finfo=true;
    				boolean fadd=false;
    				i=i+9;
    				int count=2;
        			while(finfo&&i<len)
        			{
        				char cur = data.charAt(i);
        				
        				if(cur=='{')
        					count++;
        				else if(cur=='}')
        					count--;
        			//Handling curly	
        				if(count==0||i>=len)
        				{
        					finfo = false;
        					i++;
        					break;
        				}
                                        //Handling square
        				if(cur=='['&&data.charAt(i+1)=='[')
        				{
        					fadd=true;
        				}
        				else if(cur==']'&&data.charAt(i+1)==']')
        				{
                                                tt=1;
                                                Map.addxyz(lstr, tt);    
                                                //Map.addi(lstr);
        					fadd=false;
        					lstr.setLength(0);
        				}
        				
        				if(fadd)
        				{
        					
        					if((cur>='A'&&cur<='Z')||(cur>='a'&&cur<='z'))
        					{
        						lstr.append(cur);
            					
            				}
        					else
        					{
                                                    tt=3;
                                                    Map.addxyz(lstr, tt);
        						//Map.addi(lstr);
        						lstr.setLength(0);
        					}
        				}
        				i++;
        			}
        			
    			} //Infobox  ending  here
    			
    			else if(i+6<len&&data.substring(i+1, i+6).equalsIgnoreCase(s2))
    			{
                            fexternal=false;
                            freference=false;
                            i=ignore(s2,data,i,len);
    			}
    			else if(i+4<len&&data.substring(i+1, i+4).equalsIgnoreCase(s3))
    			{
                           fexternal=false;
                           freference=false;
    				 i=ignore(s3,data,i,len);
    			}
    			else if(i+7<len&&data.substring(i+1, i+7).equalsIgnoreCase(s4))
    			{
                            fexternal=false;
                            freference=false;
                            i=ignore(s4,data,i,len);
    			}
    			else if(i+8<len&&data.substring(i+1, i+8).equalsIgnoreCase(s9))
    			{
                            fexternal=false;
                            freference=false;
    				 i=ignore(s9,data,i,len);
    			}
    		
    		}//curly  closed ie. tag starts with { closed
                
                //Handling square brackets
    		else if(data.charAt(i)=='[') 
			{
    			
    			if(i+11<len&&data.substring(i+1, i+11).equalsIgnoreCase(s5))
    			{
    				fexternal=false;
                                freference=false;
    				int count=2;
    				i=i+11;
    				boolean fcat=true;
    				//System.out.println("\nhere");
    				while(fcat)
    				{
    					char cur=data.charAt(i);
    					//System.out.print(cur);
    					if(cur==']')
    						count--;
    					else if(cur=='[')
    						count++;
    					else
    					{
    						if((cur>='A'&&cur<='Z')||(cur>='a'&&cur<='z'))
        					{
                                                    //System.out.print(cur);
        						lcat.append(cur);
            					
            				}
    						else
    						{
                                                    tt=4;
                                                    Map.addxyz(lcat, tt);
    							//Map.addc(lcat);
    							lcat.setLength(0);
    						
    						}
    						
    					}
    					if(count==0||i>=len)
    					{
    						//Map.addc(lcat);
                                            tt=4;
                                            Map.addxyz(lcat, tt);
                                            fcat=false;
                                            //System.out.print(cur);
    						lcat.setLength(0);
    					}
    					i++;
    				}
    			}
                            else if(i+8<len&&data.substring(i+1, i+8).equalsIgnoreCase(s6))
                            {
                                fexternal=false;	
                                freference=false;
                                i=ignore(s6,data,i,len);
                            }
                            else if(i+7<len&&data.substring(i+1, i+7).equalsIgnoreCase(s7))
                            {   
                                    i=ignore(s7,data,i,len);
                                 fexternal=false;
                                 freference=false;
                            }
                        }
                        else if(data.charAt(i)=='<') 
			{
                            fexternal=false;
                            freference=false;
                            //Checking followup's of <
                            if (i+4<len&&data.substring(i+1,i+4).equals("!--")) 
                            {
                            	 //Remove comments
    				 i=i+4;
    				 int ending = data.indexOf("-->",i);
                                 //System.out.print(cur);
    				 if(ending+2<len&&ending>0)
    					 i=ending+2;
                            }
    			 
                            else if(i+8<len&&data.substring(i+1,i+8).equalsIgnoreCase("gallery"))
                            {
    				 //Eliminate gallery
    				 i=i+8;
    				 int ending = data.indexOf("</gallery>" , i+1);
                                 //System.out.print(cur);
    				 if(ending+10<len&&ending>0)
    					 i=ending+10;
    				 
                            }
                            else
                            {
    				char cur = data.charAt(i);
    				while(cur!=' '&&i<len)
    				{
                			cur=data.charAt(i);
    					i++;
    				}
                            }
			}
                        else if(i+1<len&&data.charAt(i)=='='&&data.charAt(i+1)=='=')
                {
                   i=i+2;
                    while(data.charAt(i)==' '&&i<len)
                    {
                        i++;
                    }
                    if(i+14<len&&data.substring(i, i+14).equalsIgnoreCase("External links"))
                    {
                             i=i+14;
                             fexternal=true;
                            
                    }
                    
                    if(i+10<len&&data.substring(i, i+10).equalsIgnoreCase("References"))
                    {
                             i=i+10;
                             freference=true;
                             
                            
                    }

                }
                else if(fexternal&&data.charAt(i)=='*')
                {
                     //System.out.println("yess");
                    lext.setLength(0);
                    boolean flag=true;   
                    i++;
                   
                    if(i+1<data.length()&&data.charAt(i+1)=='[')
                    {
                     
                        i=i+1;
                        //System.out.println(data.charAt(i));
                        while(data.charAt(i)!=' ')
                            i++;
                    }
                    
                    char cur = data.charAt(i);
           //System.out.println(cur);
                    while(cur!='\n'||i<len)
                    {
                      // while(cur!=']')
                       //{
                           if((cur>=65&&cur<='Z')||(cur>=97&&cur<='z'))
                           lext.append(cur);
                           
                           else 
                        {	//flag=false;
                           //System.out.println(lext);
                           // Map.adde(lext);
                            tt=6;
                            Map.addxyz(lext, tt); 
                            lext.setLength(0);
                        }
                       i++;
                       cur = data.charAt(i);
                       //}
                        i++;

                    }
                }   
                else if(freference &&data.charAt(i)=='*')
                {
                    
                            i++;
                    lref.setLength(0);
                    if(i+2<data.length()&&data.charAt(i+1)=='{'&&data.charAt(i+2)=='{')
                    {
                     
                        i=i+3;
                        while(data.charAt(i)==' ')
                            i++;
                    }
                    
                    char cur = data.charAt(i);

                    while(cur!='\n'||i<len)
                    {
                       if((cur>='A'&&cur<='Z')||(cur>='a'&&cur<='z'))
                            lref.append(cur);


                        else
                        {	
                            tt=5;
                            Map.addxyz(lref, tt);
                           // Map.addr(lref);
                            lref.setLength(0);
                        }
                       i++;
                       cur = data.charAt(i);
                    
                    }
            }        
                
                else
    		
                {
    			char cur = data.charAt(i);
    		//System.out.print(cur);
    			if(cur=='#')
    			{
    				while(cur!=' '&&i<len)
    				{
    					cur=data.charAt(i);
    					i++;
    					
    				}
    			}
    			else
	    		{
    				//System.out.print(cur);
	    			if((cur>='A'&&cur<='Z')||(cur>='a'&&cur<='z'))
						lbody.append(cur);
	    			
	    			else
	    			{	
	    				//System.out.print(cur);
                                        tt=2;
                                    Map.addxyz(lbody, tt);	
                                    lbody.setLength(0);
	    			}
	    			
	    		}
    			
    		}
    	i++;
        }
	data.setLength(0);
    
    }
        catch(Exception e){}
        
}
}
