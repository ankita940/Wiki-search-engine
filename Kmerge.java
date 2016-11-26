/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package new_mini;

/**
 *
 * @author ankita
 */

import java.io.BufferedReader;
import java.io.*;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.TreeMap;
public class Kmerge {
 
    public static void main(String args[]) throws IOException
    {
        long prbyte=0,secbyte=0;
        //terbyte=0;
        int i,filecount=2,filedone=0,flag=0;
             long records=0;
        String scan;
        boolean[] complete= new boolean[filecount+1]; 
        FileReader fr[]=new FileReader[filecount+1];
        BufferedReader br[]=new BufferedReader[filecount+1];

        File f1=new File("1level-index");
        File f2=new File("2level-index");
        File f3=new File("3level-index");
        if(!f1.exists())
            f1.createNewFile();
        if(!f2.exists())
            f2.createNewFile();
        if(!f3.exists())
            f3.createNewFile();
        for(i=1;i<=filecount;i++)
        {
            complete[i]=false;
            fr[i]=new FileReader("/home/ankita/IRE/new_mini/"+i+".txt");
            br[i] = new BufferedReader(fr[i]);

        }    
        
        FileWriter fw1 = new FileWriter(f1.getAbsoluteFile());
        BufferedWriter bw1 = new BufferedWriter(fw1);
        FileWriter fw2 = new FileWriter(f2.getAbsoluteFile());
        BufferedWriter bw2 = new BufferedWriter(fw2);
         FileWriter fw3 = new FileWriter(f3.getAbsoluteFile());
        BufferedWriter bw3 = new BufferedWriter(fw3);
        /*
        for(i=1;i<=filecount;i++)
        {
            
             br[i] = new BufferedReader(fr[i]);
        }
        */
        Comparator<line> c = new Compare();
        PriorityQueue<line> data =  new PriorityQueue<line>(filecount,c);
    
        line toprow,nextrow,filerow;
            String toprowfield="",nextrowfield="",filefield;
            String toprowposting="",nextrowposting="",fileposting;
            int toprowfileno=0,nextrowfileno=0,filefileno;
        
        for(i=1;i<=filecount;i++)
        {
            line l= new line();
            scan=br[i].readLine();
            l.field=scan.substring(0,scan.indexOf("-"));
            l.posting=scan.substring(scan.indexOf("-")+1);
            l.fileno=i;
            data.add(l);
        }
        toprow=data.poll();
            toprowfield=toprow.field;
            toprowposting=toprow.posting;
            toprowfileno=toprow.fileno;
            
        
        while(filedone<filecount||flag==1)
        {
            if(complete[toprowfileno])  
            {
              //  System.out.println("oyee");
                    flag=0;
                //System.out.println(data.size());
                if(data.size()!=0) 
                {
                nextrow=data.poll();
                nextrowfield=nextrow.field;
                nextrowposting=nextrow.posting;
                nextrowfileno=nextrow.fileno;
               /* System.out.println("\n\n");
                
                System.out.println(toprowfileno);
                System.out.println(nextrowfileno);
                System.out.println(toprowfield);
                System.out.println(nextrowfield);
                */
                }
            }
            else
            {
            line l= new line();
            scan=br[toprowfileno].readLine();
            if(scan==null)
            {
               //s=1;
               complete[toprowfileno]=true;
               br[toprowfileno].close();
                // System.out.println(toprowfield);
              // System.out.println(nextrowfield);
               filedone++;
                flag=1;
               // System.out.println(flag);
                //System.out.println(toprowfileno);
              // System.out.println(toprowfield);
              // System.out.println(nextrowfield);
               
              // System.out.println(data.size());
              
            }
            else
            {
                l.field=scan.substring(0,scan.indexOf("-"));
                l.posting=scan.substring(scan.indexOf("-")+1);
                l.fileno=toprowfileno;
                data.add(l);
             // System.out.println(toprowfield);
              // System.out.println(nextrowfield);
            nextrow=data.poll();
            nextrowfield=nextrow.field;
            nextrowposting=nextrow.posting;
            nextrowfileno=nextrow.fileno;
            }
            }
            if(flag==0)
            {
              /*  if(s==1)
                {
                    System.out.println("In st  "+toprowfield);
                System.out.println("In sn  "+nextrowfield); 
                }
                */
            if(nextrowfield.equals(toprowfield)&&filedone<filecount)
            { toprowposting=toprowposting+nextrowposting;
               toprowfileno=nextrowfileno; 
            }
            else
            {
               /*   if(s==1)
                {
                    System.out.println("\n");
             
                System.out.println("In st  "+toprowfield);
              
                }
                */
                String write="",newwrite="";
                write=sort(toprowposting);
                newwrite=toprowfield+write+"\n";
                //bw1.write(toprowfield+"-");
                //bw1.write(toprowposting);

		if(records%50==0)
                {
			String ss1=toprowfield+"="+prbyte+"\n";
                        bw2.write(ss1);
			if(records%2500==0)
			{
				bw3.write(toprowfield+"="+secbyte+"\n");
			}
		
			secbyte=secbyte+ss1.length();                
		}

                bw1.write(newwrite);
		prbyte=prbyte+newwrite.length();
                
                records++;  
                toprowfield=nextrowfield;
                    toprowposting=nextrowposting;
                
                    toprowfileno=nextrowfileno;
                    
                   // nextrowfield="";
                    //nextrowposting="";
            }
            }
        }
      
    bw1.close();
    bw2.close();
    bw3.close();
    }
    public static String sort(String s)
    {
	int cut=0,doc_count=0;
        int i=0,j=0, t=100,b=50,c=30,in=10,r=10,e=10,tfvalue=0;
        String ret="";
         Comparator<newline> newc = new NewCompare();
        PriorityQueue<newline> newdata =  new PriorityQueue<>(300,newc);
	String s1[]=s.split(";");
        if(s1.length==1)
        {
            ret="-1"+"?"+s;
            return ret;
        }
        else
	{
	for(i=0;i<s1.length;i++)
	{
            
		String s2[]=s1[i].split("=");
        	tfvalue=0;
		//int id=s2[0];
                String pst="";
		String ps="";
                String id="";
                //System.out.println(s2.length);
                if(s2.length==2)
                {
                    id=s2[0];
                  //  System.out.println(s2.length);
		pst=s2[1];
		ps=s2[1]+" ";
                }
                newline obj1=new newline();
            //obj1.docid=s;
		 j=0;
       			if(ps.charAt(j)=='T')
                        {
				j++;
				String n="";
	                        while(ps.charAt(j)<='9'&&ps.charAt(j)>='0')
	                        {
	                            n=n+ps.charAt(j);
	                            j++;
	                        }
                                 // System.out.println(toprowfield);
              // System.out.println(nextrowfield);
                        	tfvalue=tfvalue+t*Integer.parseInt(n);
                        }
			if(ps.charAt(j)=='B')
                        {
				j++;
				String n="";
	                        while(ps.charAt(j)<='9'&&ps.charAt(j)>='0')
	                        {
	                            n=n+ps.charAt(j);
	                            j++;
	                        }
                        	tfvalue=tfvalue+b*Integer.parseInt(n);
                         // System.out.println(toprowfield);
              // System.out.println(nextrowfield);
                        }
			if(ps.charAt(j)=='C')
                        {
				j++;
				String n="";
	                        while(ps.charAt(j)<='9'&&ps.charAt(j)>='0')
	                        {
	                            n=n+ps.charAt(j);
	                            j++;
	                        }
                         // System.out.println(toprowfield);
              // System.out.println(nextrowfield);
                                tfvalue=tfvalue+c*Integer.parseInt(n);
                        
                        }
			if(ps.charAt(j)=='I')
                        {
				j++;
				String n="";
	                        while(ps.charAt(j)<='9'&&ps.charAt(j)>='0')
	                        {
	                            n=n+ps.charAt(j);
	                            j++;
	                        }
                        	tfvalue=tfvalue+in*Integer.parseInt(n);
                        }
               		if(ps.charAt(j)=='R')
                        {
				j++;
				String n="";
	                        while(ps.charAt(j)<='9'&&ps.charAt(j)>='0')
	                        {
                                     // System.out.println(toprowfield);
              // System.out.println(nextrowfield);
	                            n=n+ps.charAt(j);
	                            j++;
	                        }
                        	tfvalue=tfvalue+r*Integer.parseInt(n);
                        }
			if(ps.charAt(j)=='E')
                        {
				j++;
                                 // System.out.println(toprowfield);
              // System.out.println(nextrowfield);
				String n="";
	                        while(ps.charAt(j)<='9'&&ps.charAt(j)>='0')
	                        {
	                            n=n+ps.charAt(j);
	                            j++;
	                        }
                        	tfvalue=tfvalue+e*Integer.parseInt(n);
                        }
            obj1.docid=id;
            obj1.tf=tfvalue;
		obj1.posting=pst;
                if(cut<300)
                {
            newdata.add(obj1);
                cut++;
                }
                else
                    break;
        }
       
       
       while(!newdata.isEmpty())
       {
           newline nn;
           nn=newdata.poll();
           ret=ret+nn.docid+"="+nn.posting+";";
           doc_count++;
       }
       ret="-"+doc_count+"?"+ret;
       return ret;
	}

    }
    
}
class Compare implements Comparator<line> 
{ 
    @Override
    public int compare(line x, line y) 
    { 
        return x.field.compareTo(y.field);
    } 
}

class line
 {
     String field;
     String posting;
     int fileno;
 }
class NewCompare implements Comparator<newline> 
{ 
    @Override
    public int compare(newline x, newline y) 
    { 
        return y.tf-x.tf;
    } 
}

class newline
 {
     int tf;
     String posting;
    String docid;
 }
