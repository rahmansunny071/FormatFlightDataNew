/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formatairportdata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author srahman7
 */
public class FormatAirportData {

    /**
     * @param date
     * @return 
     */
	 public static Date parseDate(String date) {
     try {
         return new SimpleDateFormat("yyyy-MM-dd").parse(date);
     } catch (ParseException e) {
         return null;
     }
  }
    
    
    
    private static long nextLong(Random rng,long n) {
		// TODO Auto-generated method stub
		long bits, val;
		   do {
		      bits = (rng.nextLong() << 1) >>> 1;
		      val = bits % n;
		   } while (bits-val+(n-1) < 0L);
		   return val;
	}
    
    public static  String getOsName()
	   {
		String OS = null;
	      if(OS == null) {
	    	  OS = System.getProperty("os.name"); 
	    	  }
	      return OS;
	   }
    
    public static void createNT() throws ParseException {
        
        
        String csvFile;
        BufferedReader br = null;
        int date;
        Date myDate;
        Calendar cal = new GregorianCalendar();
        Map<String,String> carrierMap = new HashMap<String,String>();
        Map<String,Integer> airportMap = new HashMap<String,Integer>();
        String[] sample; 
        String line = "";
        String schemafile;
   		String datafile;
   		String outputfile;
   		String data = "flight";
        HashMap<String,Integer> ym = new HashMap<String,Integer>();   
        
        try {
           
           int count=0;
           if(getOsName().toLowerCase().contains("windows"))
        	   br = new BufferedReader(new FileReader(".\\data\\airports.csv")); 
           else
        	   br = new BufferedReader(new FileReader("airports.csv"));  
           
            while ((line = br.readLine()) != null) {
                if(count > 0)
                {
                    sample = line.split(",");
                    //System.out.println(line);
                    if(sample[4].trim().equals("USA"))
                        airportMap.put(sample[0].trim(), 1);
                }
                count++;
            }
           
            /*for(String key:airportMap.keySet())
            {
                System.out.println(key);
            }*/
           
           br.close();

           
            long randomNum =0,min=1,max=Long.MAX_VALUE;
            Random rand = new Random();
            BufferedWriter bw = new BufferedWriter(new FileWriter("flightrand.csv"));
        
            boolean leapYrCheck;
            int day_of_year;
            int totalCount=0;
            String carrierD,weatherD,nasD,secD,aircraftD,taxOut,taxiIn;
            for(int filename = 1;filename<=333;filename++)
            {
                
            	leapYrCheck=true;
            	csvFile = filename+".csv";  

                //br = new BufferedReader(new FileReader(csvFile));
                line = "";

                br = new BufferedReader(new FileReader(csvFile));
                    count=0;
                    
                    while ((line = br.readLine()) != null) {

                                        // use comma as separator
                    	                   	
                    	
                        sample = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                        if(count > 0)
                        {
                            //ym.put(sample[0].trim()+","+sample[2].trim(),1);
				/*if(true)
				{
					System.out.println(line);
					System.out.println(sample.length);
					for(int i=0;i<sample.length;i++)
						System.out.println(i+"---"+sample[i]);
					return;			
					
				}*/
		            long cancelled = (long) Double.parseDouble(sample[41].trim());
			    //System.out.println(count+"--"+cancelled);
                            if(cancelled == 0)
                            {
                                randomNum=nextLong(rand,max-min)+min;
                                myDate = parseDate(sample[0].trim()+"-"+sample[2].trim()+"-"+sample[3].trim());
                                
                                cal.setTime(myDate);
                                //date = 365*(Integer.parseInt(sample[0].trim())-1987)+Integer.parseInt(sample[1].trim());
                                //System.out.println(line+"--"+sample[17].trim());
                                if(isLeapYear(Integer.parseInt(sample[0].trim())))
                                	day_of_year = cal.get(Calendar.DAY_OF_YEAR);
                                else
                                {
                                	if(Integer.parseInt(sample[2].trim())>2)
                                		day_of_year = cal.get(Calendar.DAY_OF_YEAR)+1;
                                	else
                                		day_of_year = cal.get(Calendar.DAY_OF_YEAR);
                                }
                                
                                //carrierMap.get(sample[8].trim())
				//System.out.println(sample[11].trim()+"--"+sample[17].trim());
                                if(airportMap.containsKey(sample[11].trim().replaceAll("^\"|\"$", "")) && airportMap.containsKey(sample[17].trim().replaceAll("^\"|\"$", ""))) // && carrierMap.containsKey(sample[8].trim())
                                {
                                	if(sample[30].trim().equals(""))
                                		taxOut = "0";
                                	else
                                		taxOut = sample[30].trim();
                                	if(sample[33].trim().equals(""))
                                		taxiIn = "0";
                                	else
                                		taxiIn = sample[33].trim();
                                	
                                	
                                	if(sample[50].trim().equals(""))
                                		carrierD = "0";
                                	else
                                		carrierD = sample[50].trim();
                                	if(sample[51].trim().equals(""))
                                		weatherD = "0";
                                	else
                                		weatherD = sample[51].trim();
                                	if(sample[52].trim().equals(""))
                                		nasD = "0";
                                	else
                                		nasD = sample[52].trim();
                                	if(sample[53].trim().equals(""))
                                		secD = "0";
                                	else
                                		secD = sample[53].trim();
                                	if(sample[54].trim().equals(""))
                                		aircraftD = "0";
                                	else
                                		aircraftD = sample[54].trim();
                                	
                                    bw.write(day_of_year+","+sample[2].trim()+","+sample[3].trim()+","+cal.get(Calendar.DAY_OF_WEEK)+","
                                        +cal.get(Calendar.WEEK_OF_YEAR)+","+sample[0].trim()+","+sample[6].trim()+","+sample[11].trim()+","+sample[12].trim()+","
                                        +sample[13].trim()+","+sample[17].trim()+","
                                        +sample[18].trim()+","
                                        +sample[19].trim()+","
                                        +sample[25].trim()+","
                                        +taxOut+","
                                        +taxiIn+","
                                        +sample[36].trim()+","
                                        +carrierD+","
                                        +weatherD+","
                                        +nasD+","
                                        +secD+","
                                        +aircraftD+","
                                        +randomNum+"\n");
                                    totalCount++;
                                    //System.out.println("here");
                                }
                                
				
                                
                                
                            }
                            
                           	
                            
                        }

                        count++;


                    }

                    //System.out.println(count);
                    br.close();
                }
            
            //for(String key:ym.keySet())
            	//System.out.println(key);
            System.out.println(totalCount);
            bw.close();
            } catch (Exception e) {
                System.out.println(line);
                    e.printStackTrace();
            } finally {
                    if (br != null) {
                            try {
                                    br.close();
                            } catch (IOException e) {
                                    e.printStackTrace();
                            }
                    }
            }
            
            
        
        
        
    }
    
    private static boolean isLeapYear(int filename) {
		// TODO Auto-generated method stub
    	if(filename%400 == 0)
    		return true;
    	else if( filename%4==0 && filename%100!=0)
    		return true;
    	
		return false;
	}


	private static void copyRandomized() {
		// TODO Auto-generated method stub
        try{
                BufferedReader br = null;
                BufferedWriter bw = new BufferedWriter(new FileWriter("flight.csv")); ;
                String line = "";
                String [] sample;
                
                int count = 0;
                
                bw.write("Day,Month,Day Of Month,Day Of Week,Week Of Year,Year,Carrier,"
                		+ "Origin Airport,Origin City,Origin State,Destination Airport,"
                		+ "Destination City,Destination State,"
                		 + "Departure Delay (minutes),Taxi Out (minutes),Taxi In (minutes),"
                		 + "Arrival Delay (minutes),Carrier Delay (minutes),Weather Delay (minutes),"
                		 + "NAS Delay (minutes),Security Delay (minutes),Aircraft Delay (minutes)\n"
            		);

                br = new BufferedReader(new FileReader("flightrand.csv"));
                	while ((line = br.readLine()) != null ) {

                        // use comma as separator
                        sample = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                        if(!(sample[13].trim().equals("") || sample[14].trim().equals("") || sample[15].trim().equals("") || sample[16].trim().equals("") || sample[17].trim().equals("") || sample[18].trim().equals("") || sample[19].trim().equals("") || sample[20].trim().equals("") || sample[21].trim().equals("")))
                        {
                    		bw.write(sample[0].trim()+","+sample[1].trim()+","+sample[2].trim()+","+sample[3].trim()+","                           
                            		+sample[4].trim()+","+sample[5].trim()+","+sample[6].replace("\"", "").trim()+","
					+sample[7].replace("\"", "").trim()+","
                            		+sample[8].replace("\"", "").replace(",", "-").trim()+","+sample[9].replace("\"", "").trim()+","
					+sample[10].replace("\"", "").trim()+","+sample[11].replace("\"", "").replace(",", "-").trim()+","
                            		+sample[12].replace("\"", "").trim()+","+sample[13].trim()+","+sample[14].trim()+","
					+sample[15].trim()+","
                            		+sample[16].trim()+","+sample[17].trim()+","+sample[18].trim()+","+sample[19].trim()+","
                            		+sample[20].trim()+","+sample[21].trim()+"\n");
	                        count++;
                        }
	                       
			
	                }
                	
	                br.close();
                	
               
                
                
			    System.out.println(count);
			
			    bw.close();
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        } finally {

        }
    }

    	private static void removeQuotes() {
		// TODO Auto-generated method stub
        try{
                BufferedReader br = null;
                BufferedWriter bw = new BufferedWriter(new FileWriter("flight.csv")); ;
                String line = "";
                String [] sample;
                
                int count = 0;
                
               

                br = new BufferedReader(new FileReader("flightAll_.csv"));
                	while ((line = br.readLine()) != null ) {

                        // use comma as separator
                        sample = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                        bw.write(sample[0].trim()+","+sample[1].trim()+","+sample[2].trim()+","+sample[3].trim()+","                           
                            		+sample[4].trim()+","+sample[5].trim()+","+sample[6].replace("\"", "").trim()+","
					+sample[7].replace("\"", "").trim()+","
                            		+sample[8].replace("\"", "").replace(",", "-").trim()+","+sample[9].replace("\"", "").trim()+","
					+sample[10].replace("\"", "").trim()+","+sample[11].replace("\"", "").replace(",", "-").trim()+","
                            		+sample[12].replace("\"", "").trim()+","+sample[13].trim()+","+sample[14].trim()+","
					+sample[15].trim()+","
                            		+sample[16].trim()+","+sample[17].trim()+","+sample[18].trim()+","+sample[19].trim()+","
                            		+sample[20].trim()+","+sample[21].trim()+"\n");
	                        count++;
  
	                       
			
	                }
                	
	                br.close();
                	
               
                
                
			    System.out.println(count);
			
			    bw.close();
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        } finally {

        }
    }
    
    public static void main(String[] args) {
       /* try {
             //TODO code application logic here
            createNT();
            
            //calcAvg();
        } catch (ParseException ex) {
            Logger.getLogger(FormatAirportData.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        //calcAvg();
        String [] data = {"flightWeed"};//flightWeed,taxitrip2011Weed
        
    	//weedOutliers(data[0]);
    	// calcAgg(data);
        calcAgg2D(data);
   //     readFile(); 
     // copyRandomized();
    	//withAllData();
    	//countrows();
	//removeQuotes();
    }
    
    
    
    private static void weedOutliers(String data) {
		// TODO Auto-generated method stub
    	// TODO Auto-generated method stub
    	String dir;
    	if(getOsName().toLowerCase().contains("windows"))
		{
			dir = "C:\\Users\\srahman7\\workspace\\datarepo\\";
			
		}
		else
		{
			dir = "/media/srahman7/OS/Users/srahman7/workspace/datarepo/";
			
		}
        try{
                BufferedReader br = null;
                BufferedWriter bw = new BufferedWriter(new FileWriter("taxitrip2011Weed.csv")); ;
                String line = "";
                String [] sample;
                
                int count = 0;
                
                /*
                {-12,153},
                		{5,55},
                		{1,50},
                		{-31,158},
                		{0,76},
                		{0,11},
                		{0,64},
                		{0,1},
                		{0,98} 
                 */
                double [] [] c = {
                		{0,5},
                        {1,46},
                        {0.01,17.82}
                		
                };
               /* bw.write("Day,Month,Day Of Month,Day Of Week,Week Of Year,Year,Carrier,"
                		+ "Origin Airport,Origin City,Origin State,Destination Airport,"
                		+ "Destination City,Destination State,"
                		 + "Departure Delay (minutes),Taxi Out (minutes),Taxi In (minutes),"
                		 + "Arrival Delay (minutes),Carrier Delay (minutes),Weather Delay (minutes),"
                		 + "NAS Delay (minutes),Security Delay (minutes),Aircraft Delay (minutes)\n"
            		);*/
                bw.write("Day,Day of Week,Month,Week of Year,Year,Hour,Passenger Count,Time (minutes),Distance (miles)\n");
                br = new BufferedReader(new FileReader(dir+data+".csv"));
                int lineNo = 0;
                	while ((line = br.readLine()) != null ) {

                        // use comma as separator
                        sample = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                        if(lineNo > 1)
	                        if(!isOutlier(c,sample))
	                        {
	                    		/*bw.write(sample[0].trim()+","+sample[1].trim()+","+sample[2].trim()+","+sample[3].trim()+","                           
	                            		+sample[4].trim()+","+sample[5].trim()+","+sample[6].replace("\"", "").trim()+","
						+sample[7].replace("\"", "").trim()+","
	                            		+sample[8].replace("\"", "").replace(",", "-").trim()+","+sample[9].replace("\"", "").trim()+","
						+sample[10].replace("\"", "").trim()+","+sample[11].replace("\"", "").replace(",", "-").trim()+","
	                            		+sample[12].replace("\"", "").trim()+","+sample[13].trim()+","+sample[14].trim()+","
						+sample[15].trim()+","
	                            		+sample[16].trim()+","+sample[17].trim()+","+sample[18].trim()+","+sample[19].trim()+","
	                            		+sample[20].trim()+","+sample[21].trim()+"\n");*/
	                        	bw.write(sample[0].trim()+","+sample[1].trim()+","+sample[2].trim()+","+sample[3].trim()+","
	                        			+sample[4].trim()+","+sample[5].trim()+","+sample[6].trim()+","+sample[7].trim()+","+sample[8].trim()+"\n");
		                        count++;
	                        }
	                      lineNo++; 
			
	                }
                	
	                br.close();
                	
               
                
                
			    System.out.println(count);
			
			    bw.close();
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        } finally {

        }
	}



	private static boolean isOutlier(double[][] c, String[] sample) {
		// TODO Auto-generated method stub
		
		for(int i=6;i<sample.length;i++)
		{
			if(Double.parseDouble(sample[i]) < c[i-6][0] || Double.parseDouble(sample[i]) > c[i-6][1])
				return true;
		}
		
		return false;
	}



	private static void countrows() {
		// TODO Auto-generated method stub
    	try{
            BufferedReader br = null;
            //BufferedWriter bw = new BufferedWriter(new FileWriter("flight2003.csv")); ;
            String line = "";
            String [] sample;
            
            int count = 0;
            
            br = new BufferedReader(new FileReader("flight.csv"));
            	while ((line = br.readLine()) != null ) {

                    // use comma as separator
            		count++;
                       
		
                }
            	
                br.close();
            	
           
            
            
		    System.out.println(count);
		
		    //bw.close();
	    } catch (FileNotFoundException e) {
	            e.printStackTrace();
	    } catch (IOException e) {
	            e.printStackTrace();
	    } finally {
	
	    }
	}



	private static void withAllData() {
		// TODO Auto-generated method stub
    	try{
            BufferedReader br = null;
            BufferedWriter bw = new BufferedWriter(new FileWriter("flight2003.csv")); ;
            String line = "";
            String [] sample;
            
            int count = 0;
            
            br = new BufferedReader(new FileReader("flight.csv"));
            	while ((line = br.readLine()) != null ) {

                    // use comma as separator
            		if(count==0)
            		{
            			bw.write("Day,Month,Day Of Month,Day Of Week,Week Of Year,Year,Carrier,"
                        		+ "Origin Airport,Origin City,Origin State,Destination Airport,"
                        		+ "Destination City,Destination State,"
                        		 + "Departure Delay (minutes),Taxi Out (minutes),Taxi In (minutes),"
                        		 + "Arrival Delay (minutes),Carrier Delay (minutes),Weather Delay (minutes),"
                        		 + "NAS Delay (minutes),Security Delay (minutes),Aircraft Delay (minutes)\n"
                    		);

                        
            		}
            		else
            		{
	                    sample = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	                    if(Integer.parseInt(sample[5].trim()) == 2003 && Integer.parseInt(sample[1].trim()) >= 6)
	                    {
	                		bw.write(sample[0].trim()+","+sample[1].trim()+","+sample[2].trim()+","+sample[3].trim()+","                           
	                        		+sample[4].trim()+","+sample[5].trim()+","+sample[6].trim()+","+sample[7].trim()+","
	                        		+sample[8].trim()+","+sample[9].trim()+","+sample[10].trim()+","+sample[11].trim()+","
	                        		+sample[12].trim()+","+sample[13].trim()+","+sample[14].trim()+","+sample[15].trim()+","
	                        		+sample[16].trim()+","+sample[17].trim()+","+sample[18].trim()+","+sample[19].trim()+","
	                        		+sample[20].trim()+","+sample[21].trim()+"\n");
	                		count++;
	                        
	                    }
	                    else if(Integer.parseInt(sample[5].trim()) > 2003)
	                    {
	                		bw.write(sample[0].trim()+","+sample[1].trim()+","+sample[2].trim()+","+sample[3].trim()+","                           
	                        		+sample[4].trim()+","+sample[5].trim()+","+sample[6].trim()+","+sample[7].trim()+","
	                        		+sample[8].trim()+","+sample[9].trim()+","+sample[10].trim()+","+sample[11].trim()+","
	                        		+sample[12].trim()+","+sample[13].trim()+","+sample[14].trim()+","+sample[15].trim()+","
	                        		+sample[16].trim()+","+sample[17].trim()+","+sample[18].trim()+","+sample[19].trim()+","
	                        		+sample[20].trim()+","+sample[21].trim()+"\n");
	                		count++;
	                        
	                    }
	                    
	                    
	                    
            		}
            		
                       
		
                }
            	
                br.close();
            	
           
            
            
		    System.out.println(count);
		
		    bw.close();
	    } catch (FileNotFoundException e) {
	            e.printStackTrace();
	    } catch (IOException e) {
	            e.printStackTrace();
	    } finally {
	
	    }
	}



	private static void calcAvg() {
        String csvFile;
        BufferedReader br = null;
        int date;
        Date myDate;
        Calendar cal = new GregorianCalendar();
        double [] avg = new double[366];
        int [] counts = new int[366];
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("/media/srahman7/OS/Users/srahman7/workspace/datarepo/fligth_agg_Security Delay (minutes).csv"));
        
            
            csvFile = "/media/srahman7/OS/Users/srahman7/workspace/datarepo/flight.csv";  

                //br = new BufferedReader(new FileReader(csvFile));
            String line = "";


            int index;
            double value;
            br = new BufferedReader(new FileReader(csvFile));
            int count=0;
            String[] sample;
            while ((line = br.readLine()) != null  && count <=15000000) {

                                // use comma as separator
            	
                //sample = line.split(",");
            	if(count>1)
            	{
            		//System.out.println(line);
            		sample = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	                index=Integer.parseInt(sample[0].trim());
	                value = Double.parseDouble(sample[20].trim());
	                counts[index-1]++;
	                avg[index-1] = ((avg[index-1]*(counts[index-1]-1)+value)*1.0)/counts[index-1];
            	}
            	
            	count++;
                

            }

            System.out.println(count);
            br.close();

            for(int i=0;i<avg.length;i++)
            {
                bw.write((i+1)+","+avg[i]+"\n");
                   
            }
            
            
            bw.close();
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        } finally {
                if (br != null) {
                        try {
                                br.close();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
        }
    }

	private static void calcAgg(String [] data) {
		// TODO Auto-generated method stub
		BufferedReader br = null;
        BufferedWriter bw = null;
        BufferedWriter bw1 = null;
        Map<Integer, Double> HashTab;
        String line;

        int key;
        double value;

        
        double sum;
        
        ArrayList<String> dim = new ArrayList<String>();
        ArrayList<String> measure = new ArrayList<String>();
        String dir;
		
        if(getOsName().toLowerCase().contains("windows"))
		{
			dir = "C:\\Users\\srahman7\\workspace\\datarepo\\";
			
		}
		else
		{
			dir = "/media/srahman7/OS/Users/srahman7/workspace/datarepo/";
			
		}
        
        
        try {
            for (String data1 : data) {
                System.out.println("-------------------------" + data1 + "-------------------------");

                //read schema
                br = new BufferedReader(new FileReader(dir + data1 + "_schema.csv"));
                int crossOver=0;
                int measStart=0;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    String[] sections = line.split(":");
                    String[] terms = sections[1].split(",");

                    if ("indexed".equals(terms[1])) {
                        dim.add(sections[0]);
                        crossOver++;
                    } else {
                        measStart = crossOver;
                        //System.out.println(measStart+sections[0]);
                       
                        measure.add(sections[0]);
                        
                    }
                }

                br.close();

               
                
                for (int m = 0; m < measure.size(); m++) {
                	
                	HashTab = new TreeMap<Integer, Double>();
                	int day = 0;
                    double mean = 0;
                    double variance = 0;
                    double sum1 = 0;
                    double sumSq = 0;
                    int [] counts = new int[366];
                    
                    int lineNo = 0;
                    int totalInstances=0;
                    sum=0;
                    br = new BufferedReader(new FileReader(dir + data1 + ".csv"));
                    while ((line = br.readLine()) != null && lineNo < 15000002) {
                        if (lineNo == 0) {
                            System.out.println(line.split(",")[measStart + m].trim());
                        }
                        if (lineNo > 0) {
                            //key = Integer.parseInt(line.split(",")[0]);

                            value = Double.parseDouble(line.split(",")[measStart + m].trim());
                            day = Integer.parseInt(line.split(",")[0].trim());
                            
                            sum1 += value;
                            sumSq += value*value;
                            totalInstances++;
                            
                            if (!HashTab.containsKey(day)) {
                                HashTab.put(day, value);
                            } else {
                                sum = HashTab.get(day);
                                sum += value;
                                HashTab.put(day, sum);
                                
                                counts[day-1]++;
                            }
                        }

                        lineNo++;
                    }

                    br.close();
                    variance = (sumSq - sum1*sum1/(totalInstances))/((totalInstances));
                    bw = new BufferedWriter(new FileWriter(data1+"_agg_"+measure.get(m)+".csv")); 
                    System.out.println(variance);
                    for(int i=0;i<counts.length;i++)
                    {
                    	//System.out.println(i+","+counts.length+","+HashTab.keySet());
                    	if(HashTab.containsKey(i+1))
                    		bw.write((i+1)+","+HashTab.get(i+1)/counts[i]+"\n"); 
                    }
                    bw.close();
                }

            }
        } catch (Exception ex) {
            Logger.getLogger(FormatAirportData.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
	
	private static void calcAgg2D(String [] data) {
		// TODO Auto-generated method stub
		BufferedReader br = null;
        BufferedWriter bw = null;
        BufferedWriter bw1 = null;
        Map<String, Double> HashTab;
        String line;

        String key;
        double value;

        
        double sum;
        
        ArrayList<String> dim = new ArrayList<String>();
        ArrayList<String> measure = new ArrayList<String>();
        String dir;
		
        if(getOsName().toLowerCase().contains("windows"))
		{
			dir = "C:\\Users\\srahman7\\workspace\\datarepo\\";
			
		}
		else
		{
			dir = "/media/srahman7/OS/Users/srahman7/workspace/datarepo/";
			
		}
        
        
        try {
            for (String data1 : data) {
                System.out.println("-------------------------" + data1 + "-------------------------");

                //read schema
                br = new BufferedReader(new FileReader(dir + data1 + "_schema.csv"));
                int crossOver=0;
                int measStart=0;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    String[] sections = line.split(":");
                    String[] terms = sections[1].split(",");

                    if ("indexed".equals(terms[1])) {
                        dim.add(sections[0]);
                        crossOver++;
                    } else {
                        measStart = crossOver;
                        //System.out.println(measStart+sections[0]);
                       
                        measure.add(sections[0]);
                        
                    }
                }

                br.close();

               
                
                for (int m = 0; m < measure.size(); m++) {
                	
                	HashTab = new TreeMap<String, Double>();
                	int dayOfMonth = 0;
                	int month = 0;
                    
                    int [][] counts = new int[31][12];
                    
                    int lineNo = 0;
                    int totalInstances=0;
                    sum=0;
                    br = new BufferedReader(new FileReader(dir + data1 + ".csv"));
                    while ((line = br.readLine()) != null && lineNo < 15000002) {
                        if (lineNo == 0) {
                            System.out.println(line.split(",")[measStart + m].trim());
                        }
                        if (lineNo > 0) {
                            //key = Integer.parseInt(line.split(",")[0]);

                            value = Double.parseDouble(line.split(",")[measStart + m].trim());
                            dayOfMonth = Integer.parseInt(line.split(",")[2].trim());
                            month = Integer.parseInt(line.split(",")[1].trim());
                            
                            key =  Integer.toString(dayOfMonth)+","+Integer.toString(month); 
                            
                            if(!HashTab.containsKey(key))
                            {
                            	HashTab.put(key, value);
                            	counts[dayOfMonth-1][month-1] = 1;
                            }
                            else
                            {
                            	sum = HashTab.get(key);
                            	
                            	sum += value;
                            	
                            	HashTab.put(key, sum);
                            	counts[dayOfMonth-1][month-1] += 1;
                            }
                        }

                        lineNo++;
                    }

                    br.close();
                   
                    bw = new BufferedWriter(new FileWriter(data1+"_aggHeat_"+measure.get(m)+".csv")); 
                    for(int i=0;i<counts.length;i++)
                    	for(int j=0;j<counts[i].length;j++)
                        {
                    		key = Integer.toString(i+1)+","+Integer.toString(j+1);
	                    	if(HashTab.containsKey(key))
	                    		bw.write((i+1)+","+(j+1)+","+HashTab.get(key)/counts[i][j]+"\n"); 
	                    }
                    bw.close();
                }

            }
        } catch (Exception ex) {
            Logger.getLogger(FormatAirportData.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
	
	
}
