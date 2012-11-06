import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.internal.seleniumemulation.IsElementPresent;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.gargoylesoftware.htmlunit.BrowserVersion;


public class SalaryState2010 {

//	public HtmlUnitDriver driver;
	public WebDriver driver;
    public WebDriverBackedSelenium sel;
    public WebElement element;
    public int pageNumber =1;
    public int endPage=1;
    String dept="";
    String name="";
    String title="";
    String base="";
    String other="";
    String gross="";
    String mdv="";
    String er="";
    String epepsn="";
    String dc="";
    String misc="";
    String tcoe="";
    public int row=1;
    
    WritableWorkbook workbook ;
    WritableSheet sheet;
    Label label;
    
    
    
    
    public SalaryState2010(){ 
    	
    	 driver= new FirefoxDriver(); 
//    	 driver= new HtmlUnitDriver(BrowserVersion.FIREFOX_3_6);
//    	 driver.manage().timeouts().pageLoadTimeout(80, TimeUnit.SECONDS); 
//    	 driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
    }
    public void openExcel() throws Exception{
    	workbook = Workbook.createWorkbook(new File("Page"+pageNumber+"To"+endPage+".xls"));
    	sheet = workbook.createSheet("First Sheet", 0);
    }
    
  

    public void initializeExcel() throws Exception{
    	openExcel();
    	label= new Label(0, 0 , "Campus");
    	sheet.addCell(label);
    	label= new Label(1, 0, "Last");
    	sheet.addCell(label);
    	label= new Label(2, 0 , "First");
    	sheet.addCell(label);
    	label= new Label(3, 0 , "Title");
    	sheet.addCell(label);
    	label= new Label(4, 0, "Dept");
    	sheet.addCell(label);
    	label= new Label(5, 0 , "Base");
    	sheet.addCell(label);
    	label= new Label(6, 0 , "OT");
    	sheet.addCell(label);
    	label= new Label(7, 0 , "Adjustments");
    	sheet.addCell(label);
    	label= new Label(8, 0 , "Gross");
    	sheet.addCell(label); 
    }
    
	public void writeExcel(ArrayList<String> data) {
		try{ 
			label= new Label(0, row, data.get(0));
	    	sheet.addCell(label);
	    	label= new Label(1, row , data.get(1));
	    	sheet.addCell(label);
	    	label= new Label(2, row , data.get(2));
	    	sheet.addCell(label);
	    	label= new Label(3, row , data.get(3));
	    	sheet.addCell(label);
	    	label= new Label(4, row  , data.get(4));
	    	sheet.addCell(label);
	    	label= new Label(5, row  ,data.get(5));
	    	sheet.addCell(label);
	    	label= new Label(6, row  , data.get(6));
	    	sheet.addCell(label);
	    	label= new Label(7, row  , data.get(7));
	    	sheet.addCell(label);
	    	label= new Label(8, row  ,data.get(8));
	    	sheet.addCell(label); 	
		}
		catch(Exception e){
			System.out.println("Error: in writing "+data);
		}
    	 

	}

	public int[] readCSV() throws Exception{
		int[] resume= new int[2];
		try {
			CsvReader results = new CsvReader("SalaryState2010.csv");
			results.readHeaders();
			int recorded=0;

			while(results.readRecord()){ 
				pageNumber = new Integer(results.get("PageNumber"));
				recorded++;

			}
			results.close();
//			System.out.println(recorded);
			resume[0]=pageNumber;
			System.out.println(resume[0]);
			resume[1]=(recorded % 10) + 2;
			System.out.println(resume[1]);
			return resume;
		}
		catch(Exception e){ 
			e.printStackTrace();
			resume[0]=-1;
			resume[1]=1;
			System.out.println("No Previous data exist!");
			return resume;
		}

	}

	public void writeCSV(ArrayList<String> list) throws Exception{
		boolean alreadyExists = new File("SalaryState2010.csv").exists();
		try{
			CsvWriter csvOutput = new CsvWriter(new FileWriter("SalaryState2010.csv", true), ',');
			if (!alreadyExists)
			{
				csvOutput.write("PageNumber");
				csvOutput.write("Dept.");
				csvOutput.write("Last Name");
				csvOutput.write("First Name");
				csvOutput.write("Middle Name");
				csvOutput.write("Title");
				csvOutput.write("Base");
				csvOutput.write("Other");  
				csvOutput.write("Grosss");
				csvOutput.write("MDV");
				csvOutput.write("ER");	
				csvOutput.write("EPEPsn");	
				csvOutput.write("DC");	
				csvOutput.write("Misc");	
				csvOutput.write("TCOE");	
				csvOutput.endRecord();
			}

			csvOutput.write(list.get(0));
			csvOutput.write(list.get(1));
			csvOutput.write(list.get(2));
			csvOutput.write(list.get(3));
			csvOutput.write(list.get(4));
			csvOutput.write(list.get(5));
			csvOutput.write(list.get(6));
			csvOutput.write(list.get(7));
			csvOutput.write(list.get(8));
			csvOutput.write(list.get(9));
			csvOutput.write(list.get(10));
			csvOutput.write(list.get(11));
			csvOutput.write(list.get(12));
			csvOutput.write(list.get(13));
			csvOutput.write(list.get(14));
			csvOutput.endRecord();
			csvOutput.close();
		}
		catch(Exception e){

		}

	}
	
public String[] findname(String name){
		
		String lastName="";
		try{
		lastName= name.substring(0, name.indexOf(","));
		}
		catch(Exception e){
			
		}
		String rest="";
		try{
			rest=name.substring(name.indexOf(",")+2);
		}
		catch(Exception e){
			
		}
		String middlename="";
		try{
			if(rest.indexOf(" ")>-1){
			middlename=rest.substring(rest.indexOf(" ")+1);
			}
		}
		catch(Exception e){
			
		}
		String firstname="";
		try{
			firstname=rest.substring(0,rest.indexOf(" "));
		}
		catch(Exception e){
			firstname=rest;
		} 
		String[] namebreakup= {lastName, middlename, firstname};
		return namebreakup;
		
	}

	public void crawlPage(int pageNumber, int startIndex) throws Exception{

		for(int i=startIndex;i<=11;i++){
			ArrayList<String> list = new ArrayList<String>();
			try{
					dept=driver.findElement(By.xpath("//form/table/tbody/tr/td/div/table/tbody/tr["+i+"]/td")).getText();
					name=driver.findElement(By.xpath("//form/table/tbody/tr/td/div/table/tbody/tr["+i+"]/td[2]")).getText();
					title=driver.findElement(By.xpath("//form/table/tbody/tr/td/div/table/tbody/tr["+i+"]/td[3]")).getText();
					base=driver.findElement(By.xpath("//form/table/tbody/tr/td/div/table/tbody/tr["+i+"]/td[4]")).getText();
					other=driver.findElement(By.xpath("//form/table/tbody/tr/td/div/table/tbody/tr["+i+"]/td[5]")).getText();
					gross= driver.findElement(By.xpath("//form/table/tbody/tr/td/div/table/tbody/tr["+i+"]/td[6]")).getText();
					mdv=driver.findElement(By.xpath("//form/table/tbody/tr/td/div/table/tbody/tr["+i+"]/td[7]")).getText();
					er=driver.findElement(By.xpath("//form/table/tbody/tr/td/div/table/tbody/tr["+i+"]/td[8]")).getText();
					epepsn=driver.findElement(By.xpath("//form/table/tbody/tr/td/div/table/tbody/tr["+i+"]/td[9]")).getText();
					dc=driver.findElement(By.xpath("//form/table/tbody/tr/td/div/table/tbody/tr["+i+"]/td[10]")).getText();
					misc=driver.findElement(By.xpath("//form/table/tbody/tr/td/div/table/tbody/tr["+i+"]/td[11]")).getText();
					tcoe=driver.findElement(By.xpath("//form/table/tbody/tr/td/div/table/tbody/tr["+i+"]/td[12]")).getText();
					String[] names= findname(name);
					
					list.add(new Integer(pageNumber).toString());
					list.add(dept);
					list.add(names[0]);
					list.add(names[1]);
					list.add(names[2]);
					list.add(title);
					list.add(base);
					list.add(other); 
					list.add(gross);
					list.add(mdv);
					list.add(er);
					list.add(epepsn);
					list.add(dc);
					list.add(misc);
					list.add(tcoe);
					System.out.println(list);
		//			writeExcel(list);
					writeCSV(list);
					row++;
			}
			catch(Exception e){
				e.printStackTrace();
				mainRun();
			}

//			System.out.println(campus+" "+lastName+" "+ firstName+" "+title+" "+dept+" "+base+" "+overtime+" "+adjustments+" "+gross);
		}


	}

	public void run(int pageNumber,int startIndex) throws Exception{
		System.out.println("Loading....");

		try{
			driver.get("http://www.mercurynews.com/salaries/state/2010?cpipage="+pageNumber);
//			sel.open("http://www.mercurynews.com/salaries/state/2010?cpipage="+pageNumber);
			System.out.println("Reading Page Number :"+pageNumber); 
			System.out.println("check");
			driver.getPageSource();
			new WebDriverWait(driver, 200).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form/table/tbody/tr/td/div/table/tbody/tr[11]/td[12]")));

		}
		catch(Exception e){
			e.printStackTrace();
			driver.get("http://www.mercurynews.com/salaries/state/2010?cpipage="+pageNumber);
//			sel.open("http://www.mercurynews.com/salaries/state/2010?cpipage="+pageNumber);
			System.out.println("Reading Page Number :"+pageNumber);
			new WebDriverWait(driver, 200).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form/table/tbody/tr/td/div/table/tbody/tr[11]/td[12]")));
		}
		crawlPage(pageNumber, startIndex);
		startIndex=2;
		pageNumber++;
		while( (isElementPresent(By.cssSelector("img[alt=\"Next10\"]"))) ){
			System.out.println("Loading....");
			try{
				driver.get("http://www.mercurynews.com/salaries/state/2010?cpipage="+pageNumber);
				System.out.println("Reading Page Number :"+pageNumber);
				new WebDriverWait(driver, 200).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form/table/tbody/tr/td/div/table/tbody/tr[11]/td[12]")));
			}
			catch(Exception e){
				driver.get("http://www.mercurynews.com/salaries/state/2010?cpipage="+pageNumber);
				System.out.println("Reading Page Number :"+pageNumber);
			}
			crawlPage(pageNumber,startIndex);
			pageNumber++;
		}
		System.out.println("Done !");
//		workbook.write();
//		workbook.close();
		driver.quit();

	}

	public void takeInput() throws Exception{
		System.out.println("Enter Starting Page No: ");
    	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		pageNumber = new Integer(reader.readLine());
		System.out.println("Enter Last Page No: ");
    	BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));
		endPage = new Integer(reader2.readLine());
	}

	public void mainRun() throws Exception {

//       	 test.readCSV();
		 	 int[] resume =  readCSV();
		 	 if(resume[0]<0){
		 		System.out.println("Starting Fresh");
		 		 run(1, 2);
		 	 }
		 	 else{
				 	 if((resume[1]<10)&&(resume[1]>2)){
				 		 System.out.println("Resuming from Page Number "+resume[0]+ " and index "+resume[1]);
				 		 run(resume[0], resume[1]);
				 	 }else{
				 		System.out.println("Resuming from Page Number "+(resume[0]+1)+ " and index "+2);
				 		 run(resume[0]+1, 2);
				 	 }
		 	 } 

	}



	public static void main(String[] args){
		Logger logger = Logger.getLogger ("");
        logger.setLevel(Level.OFF);
        SalaryState2010 test = new SalaryState2010();
//        try {
//        	test.takeInput();
//        	test.initializeExcel();
//			test.run();
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        try{
        	test.mainRun();
        }
        catch(Exception e){
        	e.printStackTrace();
        	try {
				test.mainRun();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				try {
					test.mainRun();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					try {
						test.mainRun();
					} catch (Exception e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
				}
			}
        }

        
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

}