import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class WorkbookInputArrayCreation { //Error in the name of this file and its respective function, due to a problem this file no longer returns an arary as its main function.

	static Workbook wb1; //Abbreviation stands for WorkBook1
	static FileInputStream wb1f; //Abbreviation stands for WorkBook1File
	static Workbook wb2;
	static FileInputStream wb2f;
	static Workbook wb3; 
	static FileInputStream wb3f;
	static Workbook wb4;
	static FileInputStream wb4f;
	static Workbook wb5;
	static FileInputStream wb5f;
	static Workbook wb6;
	static FileInputStream wb6f;
	
	public static Workbook WorkbookInputArray(int wbToReturn) throws FileNotFoundException {
		//Was going to return an array but then i didn't, I don't exactly remember why. 
		if (wbToReturn == 0) {
			try {
				wb1f = new FileInputStream(Main.inputLocation + Main.nameOfInputFiles + "1.xlsx"); //Grab the file for an input stream
				wb1 = new XSSFWorkbook(wb1f); //Assign the input stream to a XSSF workbook object
				return wb1; //Return the workbook object to the array
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("File 1 not found ensure that it is called " + Main.nameOfInputFiles+ "1 at" + Main.inputLocation);
				e.printStackTrace();
			}
		} else if(wbToReturn == 1) { //Repeat
			try {
				wb2f = new FileInputStream(Main.inputLocation + Main.nameOfInputFiles + "2.xlsx");
				wb2 = new XSSFWorkbook(wb2f);
				return wb2;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("File 2 not found ensure that it is called " + Main.nameOfInputFiles+ "2 at" + Main.inputLocation);
				e.printStackTrace();
			}
		} else if(wbToReturn == 2) {
			try {
				wb3f = new FileInputStream(Main.inputLocation + Main.nameOfInputFiles + "3.xlsx");
				wb3 = new XSSFWorkbook(wb3f);
				return wb3;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("File 3 not found ensure that it is called " + Main.nameOfInputFiles+ "3 at" + Main.inputLocation);
				e.printStackTrace();
			}
		} else if(wbToReturn == 3) {
			try {
				wb4f = new FileInputStream(Main.inputLocation + Main.nameOfInputFiles + "4.xlsx");
				wb4 = new XSSFWorkbook(wb4f);
				return wb4;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("File 4 not found ensure that it is called " + Main.nameOfInputFiles+ "4 at" + Main.inputLocation);
				e.printStackTrace();
			}
		} else if(wbToReturn == 4) {
			try {
				wb5f = new FileInputStream(Main.inputLocation + Main.nameOfInputFiles + "5.xlsx");
				wb5 = new XSSFWorkbook(wb5f);
				return wb5;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("File 5 not found ensure that it is called " + Main.nameOfInputFiles+ "5 at" + Main.inputLocation);
				e.printStackTrace();
			}
		} else if(wbToReturn == 5) {
			try {
				wb6f = new FileInputStream(Main.inputLocation + Main.nameOfInputFiles + "6.xlsx");
				wb6 = new XSSFWorkbook(wb6f);
				return wb6;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("File 6 not found ensure that it is called " + Main.nameOfInputFiles+ "6 at" + Main.inputLocation);
				e.printStackTrace();
			}
		}
		
		System.out.println("Error that shouldn't be possible, WorkbookInputarayCreation line 74 error message:   " + wbToReturn ); //Reasoning is the for loop calling this method shouldn't ever call a number that isn't between 1-5 (inclusive)
		return null;
	}
	
	
	
	public static void closeWorkbookInputs() {
		
		try {
			wb1.close();
			wb2.close();
			wb3.close();
			wb4.close();
			wb5.close();
			wb6.close();
			wb1f.close();
			wb2f.close();
			wb3f.close();
			wb4f.close();
			wb5f.close();
			wb6f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Failed to close input files");
			e.printStackTrace();
		}
		
		
	}
}
