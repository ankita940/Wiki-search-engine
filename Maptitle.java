/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package new_mini;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import static new_mini.HandleQuery.ternarylist;

/**
 *
 * @author ankita
 */
public class Maptitle {

    public static ArrayList<MainTernary> ternarylist1 = new ArrayList<>();
    public static void titlemapping(String args[],int len) throws FileNotFoundException, IOException
    {
        int i=0;
        //for(;i<args.length;i++)
          //  System.out.println("In title"+args[i]);
        
        BufferedReader br1 = new BufferedReader(new FileReader("TitleTertiary"));
        RandomAccessFile rsec1 = new RandomAccessFile("TitleSecondary", "r");
        RandomAccessFile rpr1 = new RandomAccessFile("TitlePrimary", "r");
        Comparator<MainTernary> newt = new TerCompare();
        Comparator<TitlePrimary> newp = new TitleCompare();
        String readter = null;
        
        //System.out.println(br1);
       // System.out.println(rsec1.readLine(100));
        //System.out.println(rpr1);
         i = 0;
        try {
            readter = br1.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Maptitle.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (readter != null) {
            String terline[] = readter.split("=");
            MainTernary mt = new MainTernary(terline[0], Long.parseLong(terline[1]));
            //  mt.field=terline[0];
            //mt.of=Long.parseLong(terline[1]);
            ternarylist1.add(mt);
            readter = br1.readLine();
          //  System.out.println(terline[0]);
        }
        br1.close();
//System.out.println("Size"+ternarylist1.size());
 //System.out.println(len);

        i=0;
        while (i < len)
        {
            rsec1.seek(0);
            rpr1.seek(0);
            // System.out.println(args[i]);
            int terret = 0, secret, prret;
            long teroffset=0, secoffset=0, proffset;
            boolean terflag = false, secflag = false, priflag = false;
            String prline = "";
            String secondary="", primary="";
            MainTernary mt = new MainTernary(args[i], 0);
          //  System.out.println("heyy=="+args[i]);
            terret = Collections.binarySearch(ternarylist1, mt, newt);
            if (terret >= 0) {
                //System.out.println("ter" + terret);
                teroffset = ternarylist1.get(terret).of;
                rsec1.seek(teroffset);
                terflag = true;
                secondary = rsec1.readLine();
                //System.out.println(teroffset);
            } else {

                int ab = (-1 * terret) - 2;
                //System.out.println("ab" + ab);
                if (ab < 0) {
                    ab = 0;
                }
                //System.out.println("ab" + ab);
                teroffset = ternarylist1.get(ab).of;
                //teroffset is offset in tertiary list
               // System.out.println("heyy"+teroffset);
                
                rsec1.seek(secoffset);
                
                terflag = false;
                secondary = rsec1.readLine();
                 // System.out.println("first"+secondary);
               // System.out.println(teroffset);
            }
            // ternarylist1.clear();
            if (terflag == true) {
                // System.out.println("sad");
                secflag = true;
                String new2[] = secondary.split("=");
                secoffset = Long.parseLong(new2[1]);
                //System.out.println(secoffset);
                //secflag=true;
                rpr1.seek(secoffset);
                priflag = true;
                //Addd primary
            } else if (terflag == false) {
                //System.out.println("happy");
                ArrayList<MainTernary> secondarylist = new ArrayList<>();
                int j = 0;
                while (j < 100) {
                    secondary = rsec1.readLine();
                  // System.out.println(secondary);

                    
                    if(secondary==null)
                        break;
                    String new3[] = secondary.split("=");
                    ///mt.field=new3[0];
                    //mt.of=Long.parseLong(new3[1]);;
                //    System.out.println(new3[0]);
                  //  System.out.println(new3[1]);
                    
                    MainTernary mm = new MainTernary(new3[0], Long.parseLong(new3[1]));
                    secondarylist.add(mm);
                   
                    
                    //System.out.println(secondary);
                   // System.out.println(secondarylist.get(j).field+" : "+secondarylist.get(j).of);
               j++;
                }
                System.out.println("iiii"+secondarylist.size());
                
                //rsec1.close();
               // System.out.println(args[i]);
                MainTernary mttp = new MainTernary(args[i], 0);
                //mt.field=qrarr[i];
                //mt.of=0;

                secret = Collections.binarySearch(secondarylist, mttp, newt);
                //System.out.println("secret  "+secret);
                
                if (secret >= 0) {
                  // System.out.println("happy");

                    secoffset = secondarylist.get(secret).of;
                    secflag = true;
                    priflag = true;
                    rpr1.seek(secoffset);
                   // System.out.println("sds" + secoffset);
                } else {
                    //System.out.println("sad");
                    int ab = (-1 * secret) - 2;
                    if (ab < 0) {
                        ab = 0;
                    }
                   // System.out.println("sec" + ab);
                    secoffset = secondarylist.get(ab).of;
                    //rsec1.seek(teroffset); 
                    secflag = false;
                    rpr1.seek(secoffset);
                   System.out.println("sds" + secoffset);
                }
                  //  System.out.println(secflag);
                //System.out.println("secret"+secret);
                secondarylist.clear();
            }
             //System.out.println("offset");
            //System.out.println("tof"+teroffset);
           
            
            if (!secflag) {
                //System.out.println("Here   asda");
                ArrayList<TitlePrimary> primarylist = new ArrayList<>();
                int j = 0;
                while (j < 1000) {

                    // String new5[]=prline.split("-");
                    //String new4[]=new5[1].split("\\?");
                    primary = rpr1.readLine();
                    if(primary==null)
                    {
                        //priflag=false;
                        break;
                    }
                     //System.out.println(primary);
                    //String new4[]=primary.split("-");
                    //String new5[] = primary.split("-");
                    String new4[] = primary.split("=");
                    TitlePrimary mpp = new TitlePrimary(new4[0], new4[1]);
                    
                    //mp.field=new4[0];
                    //mp.posting=new4[1];
                    primarylist.add(mpp);
                    
                 //   System.out.println(primarylist.get(j).docid+" : "+primarylist.get(j).title);
                    j++;
                }
                System.out.println("size"+primarylist.size());
             //   System.out.println("priflag1"+priflag);
                //rpr.close();
                TitlePrimary mp = new TitlePrimary(args[i], "");
                // mp.field=qrarr[i];
                //mp.posting="";
                prret = Collections.binarySearch(primarylist, mp, newp);
               //  System.out.println("prret"+prret);
                if (prret >= 0) {
                    priflag = true;
                    //proffset=primarylist.get(prret).of;
                    TitlePrimary mpp = primarylist.get(prret);
                    prline = mpp.docid+"="+mpp.title;
                    // rpr.seek(prret);
                    //System.out.println(prline);
                } else {
                    priflag = false;
                    //word not in primary index too
                }
               // System.out.println(prline);
                // primarylist.clear();        

            }
            
            

            if (secflag) {

                prline = rpr1.readLine();
                
                //System.out.println(prline);
                //rpr.close();
            }
            if (!priflag) {
                i++;
                continue;
            }

            //System.out.println(terflag);
            //System.out.println(secflag);
            //System.out.println(priflag);

            System.out.println(prline);
            
          i++; 
         // System.out.println(i);
        }
        rsec1.close();
        rpr1.close();
}
}

