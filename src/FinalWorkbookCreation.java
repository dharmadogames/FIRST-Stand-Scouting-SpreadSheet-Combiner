import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class FinalWorkbookCreation {
	
	static Workbook wb = new XSSFWorkbook(); 
	static OutputStream fileOut = null; 
	
	
	public static Workbook finalWorkbookCreation(int numberOfRounds, String headers[]) throws IOException {
		
		fileOut = new FileOutputStream(Main.outputLocation + Main.nameOfOutputFile + ".xlsx");
		
		if(Main.teamOrRound == 0) { //Used to write the headers, may try to create a program that can write both with a jenk write around? if = 2, run program on setting one then run the program on setting zero
			writeForRound(numberOfRounds, headers); //Calls the write for round, as the basic sheet is different than in team mode.
		}
		else if(Main.teamOrRound == 1) {
			writeForTeam(numberOfRounds, headers); //Calls the write for team, as the basic sheet is different than in round mode. 
		}
		else {
			System.out.println("Error, please don't modify the config file manually"); //Program (I think) can only write it as 0 or 1 currently as of this comment, so if it isn't one of those two then the config was manually adjusted. 
			Main.numberOfErrors++;
		}
		
		return wb;
	}
	
	public static void writeForRound(int numberOfRounds, String headers[]) {
		System.out.println("Writing output for round setup");
		//TODO: 
				//create a sheet with the correct headers
				System.out.println("Creating rounds");
				Sheet sheet1 = wb.createSheet("Round");
					{  //Row creation, 7 rows, first row is for the headers and the other six are for the teams
						for(int i = 0; i < 7;i++) {
							sheet1.createRow(i);
						}
					}
					{  //Create the headers
						Row row = sheet1.getRow(0);
						for(int i = 0; i < headers.length; i++) {
							Cell cell = row.createCell(i);
							cell.setCellValue(headers[i]);
						} //
					}
				
				
				//Clone that sheet for each round - 1 (the original sheet)
				System.out.println("Cloning sheets");
				for(int i = 0; i < numberOfRounds-1; i++) {
					wb.cloneSheet(0);
				}
				
		       
		  
		
	}
	public static void writeForTeam(int numberOfRounds, String headers[]) {
		System.out.println("Writing output for team setup"); //There isn't anything, everything else is written in the copy team one, as it creates each sheet when its needed rather than at the beginning. 
	}
	
	
	public static void closeOutput() {
		
		try {
			System.out.println("Updating the excel file");
			wb.write(fileOut);
			System.out.println("Updated the excel file");
			System.out.println("Closing workbook creation");
		    fileOut.close();
			wb.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
