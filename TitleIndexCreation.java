/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package new_mini;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ankita
 */
public class TitleIndexCreation {
   // public static List<String> title_primary=new ArrayList<>();
    public static long seekpoint1=0;
	public static long seekpoint2=0;
public static long seekpoint3=0;
 public static int tcount=0;
  public static void storetitle(String sp) throws IOException
    {
        int i;
        //long  bytecount;
       // RandomAccessFile f1=new RandomAccessFile("TitlePrimary","rw");
	//RandomAccessFile f2=new RandomAccessFile("TitleSecondary","rw");
	//RandomAccessFile f3=new RandomAccessFile("TitleTertiary","rw");
        //f1.seek(seekpoint1);
        // FileWriter fw = new FileWriter(f1.getAbsoluteFile());
          //  BufferedWriter bw = new BufferedWriter(fw); 
            //for(i=0;i<title_primary.size();i++)
            //{
              //  System.out.println(title_primary.get(i));
             //bw.write(title_primary.get(i));
            //}
            
            //title_primary.clear();
            //bw.close();
            
            
            int j;
            String s="";
             for(j=0;j<sp.length();j++)
             {
                 if(sp.charAt(j)=='\n'||sp.charAt(j)=='='||(sp.charAt(j)>='a'&&sp.charAt(j)<='z')||(sp.charAt(j)>='A'&&sp.charAt(j)<='Z')||(sp.charAt(j)>='0'&&sp.charAt(j)<='9'))
                 {
                    s=s+sp.charAt(j);
                 }
             }
	
	if(tcount%1000==0)
	{
		String s1[]=s.split("=");
		String secwrite=s1[0]+"="+seekpoint1+"\n";
		//f2.seek(seekpoint2);
                
		if(tcount%100000==0)
		{
			//String s2[]=secwrite.split("=");
			String terwrite=s1[0]+"="+seekpoint2+"\n";
                        //f3.seek(seekpoint3);
			Indexp.bw3.write(terwrite);
			seekpoint3=seekpoint3+terwrite.length();
		}
                Indexp.bw2.write(secwrite);
	seekpoint2=seekpoint2+secwrite.length();	
	}	
	Indexp.bw1.write(s);
	seekpoint1=seekpoint1+s.length();
	tcount++;
    }
}
