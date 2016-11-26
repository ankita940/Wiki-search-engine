/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package new_mini;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.*;
import java.util.Map;

/**
 *
 * @author ankita
 */
public class HandleQuery {

    public static ArrayList<MainTernary> ternarylist = new ArrayList<>();

    public static void main(String args[]) throws IOException, Exception {

        
        String readter, secondary, primary;
        String qr, rg = "[~,.-_+=/?><'!)*(&%^$#@0123456789]", re = "";

        //int twt=1,bwt=1,cwt=1,inwt=1,rwt=1,ewt=1,tfwt=1;
        int i = 0;
        BufferedReader br1 = new BufferedReader(new FileReader("3level-index"));
        RandomAccessFile rsec = new RandomAccessFile("2level-index", "r");
        RandomAccessFile rpr = new RandomAccessFile("1level-index", "r");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //MainTernary mt= new MainTernary();
        //MainPrimary mp= new MainPrimary();
        Comparator<MainTernary> newt = new TerCompare();
        Comparator<MainPrimary> newp = new PriCompare();

        Map<String, Integer> Storerank = new HashMap<>();
       // Set<Map.Entry<String, Integer>> Storeset = Storerank.entrySet();
        //List<Map.Entry<String, Integer>> Storelist = new ArrayList<>(Storeset);
         qr = br.readLine();
      //  qr = "anistor ball hello deo aaa";
        //qr = "1770 eminem apple ";
        qr = qr.trim();
        String qrarr[] = qr.split(" +");
        //for(i=0;i<qr.length;i++)
        //{

        //}
        i = 0;
        readter = br1.readLine();
        while (readter != null) {
            String terline[] = readter.split("=");
            MainTernary mt = new MainTernary(terline[0], Long.parseLong(terline[1]));
            //  mt.field=terline[0];
            //mt.of=Long.parseLong(terline[1]);
            ternarylist.add(mt);
            readter = br1.readLine();
            // System.out.println(mt.of);
        }
        br1.close();
        i = 0;

        //System.out.println(qrarr.length);
        while (i < qrarr.length) {
            long t = 10, b = 5, c = 3, in = 2, r = 2, e = 2, tfvalue = 0;
            //  t=1000;b=500;c=300;in=100;r=100;e=100;tfvalue=0;
            // Comparator<MainTernary> newt = new TerCompare();
            //Comparator<MainPrimary> newp = new PriCompare();
            rsec.seek(0);
            rpr.seek(0);
            int terret = 0, secret, prret;
            long teroffset, secoffset, proffset;
            boolean terflag = false, secflag = false, priflag = false;
            String prline = "";

            tfvalue = 0;
            //System.out.println(qrarr[i]);
            qrarr[i] = qrarr[i].replace(rg, re).toLowerCase().trim();
            String parse = qrarr[i];
            //System.out.println(qrarr[i]);
            PorterStemmer s = new PorterStemmer();
            s.add(parse.toCharArray(), parse.length());
            String str = s.stem();
            if (Indexp.stopwordsarr.contains(str)) {
                i++;
                continue;
            }

            if (str.contains("t:")) {
                   // System.out.println("note");
                String new1[] = str.split(":");
                qrarr[i] = new1[1];
                t = t * 10;

            } else if (str.contains("b:")) {
                String new1[] = str.split(":");
                qrarr[i] = new1[1];
                b = b * 10;

            } else if (str.contains("c:")) {
                String new1[] = str.split(":");
                qrarr[i] = new1[1];
                c = c * 10;

            } else if (str.contains("i:")) {
                String new1[] = str.split(":");
                qrarr[i] = new1[1];
                in = in * 10;

            } else if (str.contains("r:")) {
                String new1[] = str.split(":");
                qrarr[i] = new1[1];
                r = r * 10;

            } else if (str.contains("e:")) {
                String new1[] = str.split(":");
                qrarr[i] = new1[1];
                e = e * 10;

            }
            //mt.field=qrarr[i];

            //mt.of=0;
            MainTernary mt = new MainTernary(qrarr[i], 0);
            //System.out.println(qrarr[i]);
            terret = Collections.binarySearch(ternarylist, mt, newt);
            if (terret >= 0) {
                //System.out.println("ter" + terret);
                teroffset = ternarylist.get(terret).of;
                rsec.seek(teroffset);
                terflag = true;
                secondary = rsec.readLine();
                //System.out.println(teroffset);
            } else {

                int ab = (-1 * terret) - 2;
                //System.out.println("ab" + ab);
                if (ab < 0) {
                    ab = 0;
                }
                //System.out.println("ab" + ab);
                teroffset = ternarylist.get(ab).of;
                //teroffset is offset in tertiary list
                rsec.seek(teroffset);
                terflag = false;
                secondary = rsec.readLine();
                //System.out.println(teroffset);
            }
            // ternarylist.clear();
            if (terflag == true) {
                // System.out.println("sad");
                secflag = true;
                String new2[] = secondary.split("=");
                secoffset = Long.parseLong(new2[1]);
                //System.out.println(secoffset);
                //secflag=true;
                rpr.seek(secoffset);
                priflag = true;
                //Addd primary
            } else if (terflag == false) {
                //System.out.println("happy");
                ArrayList<MainTernary> secondarylist = new ArrayList<>();
                int j = 0;
                while (j < 50) {
                    secondary = rsec.readLine();
                    if(secondary==null)
                        break;
                    String new3[] = secondary.split("=");
                    ///mt.field=new3[0];
                    //mt.of=Long.parseLong(new3[1]);;
                    MainTernary mm = new MainTernary(new3[0], Long.parseLong(new3[1]));
                    secondarylist.add(mm);
                    j++;
                }
                //rsec.close();
                MainTernary mtt = new MainTernary(qrarr[i], 0);
                //mt.field=qrarr[i];
                //mt.of=0;

                secret = Collections.binarySearch(secondarylist, mtt, newt);
                if (secret >= 0) {
                   // System.out.println("happy");

                    secoffset = secondarylist.get(secret).of;
                    secflag = true;
                    priflag = true;
                    rpr.seek(secoffset);
                   // System.out.println("sds" + secoffset);
                } else {
                    //System.out.println("sad");
                    int ab = (-1 * secret) - 2;
                    if (ab < 0) {
                        ab = 0;
                    }
                    //System.out.println("sec" + ab);
                    secoffset = secondarylist.get(ab).of;
                    //rsec.seek(teroffset); 
                    secflag = false;
                    rpr.seek(secoffset);
                    //System.out.println("sds" + secoffset);
                }

                secondarylist.clear();
            }

            if (!secflag) {
               // System.out.println("Here");
                ArrayList<MainPrimary> primarylist = new ArrayList<>();
                int j = 0;
                while (j < 50) {

                    // String new5[]=prline.split("-");
                    //String new4[]=new5[1].split("\\?");
                    primary = rpr.readLine();
                    if(primary==null)
                    {
                        priflag=false;
                        break;
                    }
                    // System.out.println(primary);
                    //String new4[]=primary.split("-");
                    String new5[] = primary.split("-");
                    String new4[] = new5[1].split("\\?");
                    MainPrimary mp = new MainPrimary(new5[0], new4[1],new4[0]);
                    //mp.field=new4[0];
                    //mp.posting=new4[1];
                    primarylist.add(mp);
                    j++;
                }
                //rpr.close();
                MainPrimary mp = new MainPrimary(qrarr[i], "", "");
                // mp.field=qrarr[i];
                //mp.posting="";
                prret = Collections.binarySearch(primarylist, mp, newp);
                if (prret >= 0) {
                    priflag = true;
                    //proffset=primarylist.get(prret).of;
                    MainPrimary mpp = primarylist.get(prret);
                    prline = mpp.field + "-" + mpp.nodoc + "?" + mpp.posting;
                    // rpr.seek(prret);
                    //System.out.println(prline);
                } else {
                    priflag = false;
                    //word not in primary index too
                }
                // primarylist.clear();        
                    primarylist.clear();
            }
                 
            if (secflag) {

                prline = rpr.readLine();
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

           // System.out.println(prline);
            String new5[] = prline.split("-");
            String new4[] = new5[1].split("\\?");

            //MainPrimary mp= new MainPrimary(new5[0],new4[1]);
            //mp.field=new4[0];
            //mp.posting=new4[1];
            String pairposting[] = new4[1].split(";");
            // System.out.println(pairposting.length);
            for (int k = 0; k < pairposting.length; k++) 
            {
                String pair[] = pairposting[k].split("=");
                String pair1 = pair[1] + " ";
                // for(int l=0;l<pair1.length();l++)
                //{
                //  int kind=0;
                int l = 0;
                long value = 0;
                long finaltf = 0;
                //long finalidf=0;
                if (pair1.charAt(l) == 'T') {
                    l += 1;
                    String x = "";
                    while (pair1.charAt(l) <= 57 && pair1.charAt(l) >= 48) {
                        x = x + pair1.charAt(l);
                        l++;

                    }
                    //    kind=0;
                    value = Long.parseLong(x);
                    finaltf += value * t;
                }

                if (pair1.charAt(l) == 'B') {
                    l += 1;
                    String x = "";
                    while (pair1.charAt(l) <= 57 && pair1.charAt(l) >= 48) {
                        x = x + pair1.charAt(l);
                        l++;

                    }
                    //  kind=1;
                    value = Long.parseLong(x);
                    finaltf += value * b;
                }

                if (pair1.charAt(l) == 'C') {
                    l += 1;
                    String x = "";
                    while (pair1.charAt(l) <= 57 && pair1.charAt(l) >= 48) {
                        x = x + pair1.charAt(l);
                        l++;

                    }
                    //kind=3;
                    value = Long.parseLong(x);
                    finaltf += value * c;
                }

                if (pair1.charAt(l) == 'I') {
                    l += 1;
                    String x = "";
                    while (pair1.charAt(l) <= 57 && pair1.charAt(l) >= 48) {
                        x = x + pair1.charAt(l);
                        l++;

                    }
                    // kind=4;
                    value = Long.parseLong(x);
                    finaltf += value * in;
                }

                if (pair1.charAt(l) == 'E') {
                    l += 1;
                    String x = "";
                    while (pair1.charAt(l) <= 57 && pair1.charAt(l) >= 48) {
                        x = x + pair1.charAt(l);
                        l++;

                    }
                    //kind=5;
                    value = Long.parseLong(x);
                    finaltf += value * e;

                }
                if (pair1.charAt(l) == 'R') {
                    l += 1;
                    String x = "";
                    while (pair1.charAt(l) <= 57 && pair1.charAt(l) >= 48) {
                        x = x + pair1.charAt(l);
                        l++;

                    }
                    //kind=6;
                    value = Long.parseLong(x);
                    finaltf += value * r;

                }
                boolean kflag = false;
                if (Storerank.containsKey(pair[0])) {
                    kflag = true;
                    finaltf = finaltf + Storerank.get(pair[0]) * 10;
                   
                }
                //System.out.println(Long.parseLong(new4[0]));

                long finalidf = (long) 15000000 / Long.parseLong(new4[0]);
                long finalrank = finaltf * (long) Math.log(finalidf);

                if (kflag) {
                    Storerank.remove(pair[0]);
                    Storerank.put(pair[0], (int) finalrank);
                } else {
                    Storerank.put(pair[0], (int) finalrank);
                }
                //  System.out.println(pair[0]);
                 // System.out.println(qrarr[i]+finalrank);
            }

            

            
            i++;
        }
        ternarylist.clear();
        rsec.close();
        rpr.close();
         Set<Map.Entry<String, Integer>> Storeset = Storerank.entrySet();
        List<Map.Entry<String, Integer>> Storelist = new ArrayList<>(Storeset);
        
        
        Collections.sort( Storelist, new Comparator<Map.Entry<String, Integer>>()
        {
            @Override
            public int compare( Map.Entry<String, Integer> a1, Map.Entry<String, Integer> a2 )
            {
                //System.out.println("sa");
                return (a2.getValue()).compareTo( a1.getValue() );
            }
        });
        i=0;
        String array[]=new String[10];
        for (Map.Entry<String,Integer> entry : Storelist) 
        {
                    if(i>=10)
                    {
                        break;
                    }
                    String key = entry.getKey();
                    int  value = entry.getValue();
                   //System.out.println(key +" : "+value );
                    array[i]=key;
                   System.out.println(array[i]);
                     i++;   
        }
        //System.out.println("heyy");
         // Maptitle.titlemapping(array,i);
    }
}

class MainTernary {

    String field;
    long of;

    MainTernary(String xy, long tz) {
        this.field = xy;
        this.of = tz;
    }
}

class TerCompare implements Comparator<MainTernary> {

    @Override
    public int compare(MainTernary x, MainTernary y) {
        return x.field.compareTo(y.field);
    }
}

class MainPrimary {

    String field;
    String nodoc;
    String posting;

    MainPrimary(String s, String s1, String sd) {
        this.field = s;
        this.posting = s1;
        this.nodoc = sd;
    }
}

class PriCompare implements Comparator<MainPrimary> {

    @Override
    public int compare(MainPrimary x, MainPrimary y) {
        return x.field.compareTo(y.field);
    }
}
class TitlePrimary {

    String docid;
    String title;
    //String posting;

    TitlePrimary(String s, String s1) {
        this.docid = s;
        this.title = s1;
        //this.nodoc = sd;
    }
}

class TitleCompare implements Comparator<TitlePrimary> 
{
    @Override
    public int compare(TitlePrimary x, TitlePrimary y) {
        return x.docid.compareTo(y.docid);
    }

   
}
