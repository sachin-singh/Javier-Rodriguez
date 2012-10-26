import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.BrowserVersion;


public class Test {
	
//	public HtmlUnitDriver driver;
	public WebDriver driver;
    public WebDriverBackedSelenium sel;
    public WebElement element;
    public int pageNumber=1;
    String campus="";
    String lastName="";
    String firstName="";
    String title="";
    String dept="";
    String base="";
    String overtime="";
    String adjustments="";
    String gross=""; 
    
    WritableWorkbook workbook ;
    WritableSheet sheet;
    Label label;
    public Test(){
    	driver= new FirefoxDriver();
//    	 driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_3);
//         driver.setJavascriptEnabled(true);
         sel = new WebDriverBackedSelenium(driver , "");
    }
    public void openExcel() throws Exception{
    	workbook = Workbook.createWorkbook(new File("Results.xls"));
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
    
	public void writeExcel(ArrayList<String> data, int row) {
		try{ 
			label= new Label(0, row, data.get(0));
	    	sheet.addCell(label);
	    	label= new Label(1, row , data.get(1));
	    	sheet.addCell(label);
	    	label= new Label(2, row , data.get(2));
	    	sheet.addCell(label);
	    	label= new Label(3, row , data.get(3));
	    	sheet.addCell(label);
	    	label= new Label(4, row , data.get(4));
	    	sheet.addCell(label);
	    	label= new Label(5, row ,data.get(5));
	    	sheet.addCell(label);
	    	label= new Label(6, row , data.get(6));
	    	sheet.addCell(label);
	    	label= new Label(7, row , data.get(7));
	    	sheet.addCell(label);
	    	label= new Label(8, row ,data.get(8));
	    	sheet.addCell(label); 	
		}
		catch(Exception e){
			
		}
    	 
		
	}
	
	public void crawlPage() throws Exception{
		for(int i=2;i<=16;i++){
			ArrayList<String> list = new ArrayList<String>();
			campus=driver.findElement(By.xpath("//form/table/tbody/tr/td/div/table/tbody/tr["+i+"]/td")).getText();
			lastName=driver.findElement(By.xpath("//form/table/tbody/tr/td/div/table/tbody/tr["+i+"]/td[2]")).getText();
			firstName=driver.findElement(By.xpath("//form/table/tbody/tr/td/div/table/tbody/tr["+i+"]/td[3]")).getText();
			title=driver.findElement(By.xpath("//form/table/tbody/tr/td/div/table/tbody/tr["+i+"]/td[4]")).getText();
			dept=driver.findElement(By.xpath("//form/table/tbody/tr/td/div/table/tbody/tr["+i+"]/td[5]")).getText();
			base= driver.findElement(By.xpath("//form/table/tbody/tr/td/div/table/tbody/tr["+i+"]/td[6]")).getText();
			overtime=driver.findElement(By.xpath("//form/table/tbody/tr/td/div/table/tbody/tr["+i+"]/td[7]")).getText();
			adjustments=driver.findElement(By.xpath("//form/table/tbody/tr/td/div/table/tbody/tr["+i+"]/td[8]")).getText();
			gross=driver.findElement(By.xpath("//form/table/tbody/tr/td/div/table/tbody/tr["+i+"]/td[9]")).getText();
			list.add(campus);
			list.add(lastName);
			list.add(firstName);
			list.add(title);
			list.add(dept);
			list.add(base);
			list.add(overtime);
			list.add(adjustments);
			list.add(gross);
			System.out.println(list);
			writeExcel(list, i-1);
//			System.out.println(campus+" "+lastName+" "+ firstName+" "+title+" "+dept+" "+base+" "+overtime+" "+adjustments+" "+gross);
		}
		workbook.write();
		workbook.close();
	}
	
	public void run() throws Exception{
		System.out.println("Loading....");
		driver.get("http://www.mercurynews.com/salaries/uc/2010/?cpipage="+pageNumber);
		System.out.println("Starting....");
		new WebDriverWait(driver, 200).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//form/table/tbody/tr/td/div/table/tbody/tr[16]/td[9]")));
		crawlPage();
		pageNumber++;
		driver.quit();
		
	}
	
	public static void main(String[] args){
		Logger logger = Logger.getLogger ("");
        logger.setLevel(Level.OFF);
        Test test = new Test();
        try {
        	test.initializeExcel();
			test.run();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

}
