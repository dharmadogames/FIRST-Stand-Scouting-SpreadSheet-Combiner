import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class copyTeam {

	//Programmers notes 
		//A majority of the code in this method is rehashed version of the copyRound class
		//This is probably more buggy, as it was written in philosophy class while half paying attention
		//I don't know how many rounds each robot competes in, so instead of looking it up I assumed it changed comp to comp and wrote this so that it can deal with a variable number of matches, different for each team. (even though it isn't I think)
	
	

    public static HashMap<Integer, Sheet> sheetMap  = new HashMap<>(); 
	
	public static void copyEachTeam(Workbook workbooksToCopyFrom[], Workbook finalWorkbookInput, int TotalNumberOfRounds, String headers[]) {
		for(int i = 0; i < TotalNumberOfRounds; i++) {  //I = number of rounds; round counter
			for(int i2 = 0; i2 < workbooksToCopyFrom.length; i2++) { //I2 = current targeted copy form sheet
				System.out.println("Using input sheet " + (i2+1));
				int key = getTeamNumber(workbooksToCopyFrom[i2].getSheetAt(0).getRow(i+1)); //grabs the team number for the current row
				if(key == -1) { //If the team number doesn't load correctly, through errors or missing data
					System.out.println("Error with the team number on sheet " + (i2+1) + " Round " + i );
					System.out.println("The row is probably missing data, program will continue with lost data.");
					Main.numberOfErrors++;
				}
				else {
					Sheet sheet1 = findSheet(key,finalWorkbookInput,headers); //Create or find the sheet
					Row rowToCopyTo = getLastRowInSheet(sheet1); //Get the row to copy to in the copy to sheet
					Row rowToCopyFrom = workbooksToCopyFrom[i2].getSheetAt(0).getRow(i+1); //Get the row to copy from in the copy from sheet
					copyRow(rowToCopyFrom, rowToCopyTo, i+1); //Copy them!
				}
				
			}
			System.out.println("Round " + i);
		}
	}
    
	
	
	private static int getTeamNumber(Row rowToCopy) {
		Cell firstCell = rowToCopy.getCell(0);
		int temp = -1; //If the team number isn't found then be sad and return -1 (error value)
		try {
			CellType cellToCopyFromCellType = firstCell.getCellType();
			switch (cellToCopyFromCellType) { //Assigns the cell value based on data type, there is no basic statement sadly (that I found
				case NUMERIC:
					System.out.println("Cell is numeric, parseing");
					temp = (int) firstCell.getNumericCellValue();
					break;
				case STRING:
					System.out.println("Cell is string, parseing");
					temp = (int) Double.parseDouble(firstCell.getStringCellValue());
					break;
				case _NONE:
					System.out.println("No Cell? shouldn't happend");
					break;
				default:
					System.out.println("No type value possible, big error! May");
					break;
			}
		} catch (java.lang.NullPointerException exception) {
			System.out.println("Null cell in team mode"); //No error increment here because it would be called twice ^.^
		}
		return temp;
	}
	
	private static Sheet findSheet(int key, Workbook finalWorkbookInput,String headers[]) {
		Sheet sheetToCopyTo = null; //Instantiate the Sheet object
		if(sheetMap.containsKey(key)) { //If the sheet exists then find it and assign it to the sheet object
			sheetToCopyTo = sheetMap.get(key); 
		}
		else { //If the sheet doesn't exist then create it
			System.out.println("Sheet doesn't exist at location " + key);
			Sheet sheet1 = finalWorkbookInput.createSheet(String.valueOf(key)); //Create the sheet
			finalWorkbookInput.setSheetName(finalWorkbookInput.getSheetIndex(sheet1.getSheetName()), String.valueOf(key)); //Ensure that it is named correctly
	    	sheetMap.put(key, sheet1); //Put the sheet in the hashmap to make it easy to grab later
	    	sheet1.createRow(0); //Creates the first row
	    	Row row = sheet1.getRow(0); //Grabs the header row
	    	headers[0] = "Round"; //Changing the header for what should be team to round, as the team is stored in the sheet name instead
			for(int i = 0; i < headers.length; i++) {
				Cell cell = row.createCell(i);
				cell.setCellValue(headers[i]);
			} //
			sheetToCopyTo = sheet1;
		}
		return sheetToCopyTo;
	}
    
	
    private static void copyRow(Row rowToCopy, Row rowToCopyTo, int roundNumber) { //Code is a mostly a direct copy from copy Round, as it was written first and functions correctly, some changes have been made to replace the first header (which should be team) to round number
    	int rowWidth = getRowWidth(rowToCopy);
    	System.out.println("Currently on row " + rowToCopy.getRowNum());
    	if(rowWidth > 0) {
    		Cell cellToCopyFrom = null; // Unneeded for first cell transfer
			Cell cellToCopyTo = rowToCopyTo.createCell(0); //Get the copy to cell from the copy to row
			cellToCopyTo.setCellValue(roundNumber);
			for( int i3 = 1; i3 < rowWidth; i3++) { //Skipping zero because it replaces the team header withround
				cellToCopyFrom = rowToCopy.getCell(i3); //Get the copy from cell from the copy from row
				 cellToCopyTo = rowToCopyTo.createCell(i3); //Get the copy to cell from the copy to row
				
				try {
					CellType cellToCopyFromCellType = cellToCopyFrom.getCellType();
					switch (cellToCopyFromCellType) { //Assigns the cell value based on data type, there is no basic statement sadly
						case BLANK:
							System.out.println("Blank Cell");
							break;
						case BOOLEAN:
							cellToCopyTo.setCellValue(cellToCopyFrom.getBooleanCellValue());
							break;
						case ERROR:
							cellToCopyTo.setCellValue(cellToCopyFrom.getErrorCellValue());
							break;
						case FORMULA:
							cellToCopyTo.setCellValue(cellToCopyFrom.getCellFormula());
							break;
						case NUMERIC:
							cellToCopyTo.setCellValue(cellToCopyFrom.getNumericCellValue());
							break;
						case STRING:
							cellToCopyTo.setCellValue(cellToCopyFrom.getStringCellValue());
							break;
						case _NONE:
							System.out.println("No Cell? shouldn't happend");
							break;
						default:
							System.out.println("No type value possible, big error!");
							break;
					}
					System.out.println("Cell has been copied to location " + i3); //Change to display there was an output.
					
				} catch (java.lang.NullPointerException exception) {
					System.out.println("Null cell, this is a problem");
				}
				//System.out.println(i + "  " + i2 + "  " + i3); //Hard to read output
				 
			}
    	}
    }
    
    private static Row getLastRowInSheet(Sheet sheet1) { 
    	//On the output sheet, find the number of rows for the team sheet and create a new one at the end for data input while returning the row
		int x = sheet1.getLastRowNum();
		sheet1.createRow(x+1);
    	Row tempRow = sheet1.getRow(sheet1.getLastRowNum());
		return tempRow;
	}
    
	private static int getRowWidth(Row row){//Custom logic to find the width of a cell, as there isn't a default command
				//Addendum -> There is a command that could be used to simplify this, row.getLastCellNum() + 1;
		//Custom logic to find the width of a cell, didn't know there was a command to find it, and im keeping this code for reference later for other projects :P
		/*   int rowWidth = 0;   
		try {
			for (@SuppressWarnings("unused") Cell cell : row) { //My first for each loop <3
				rowWidth++;
			}
		} catch (java.lang.NullPointerException exception) {
			System.out.println("Row " + row + " has no width");
			rowWidth = 0;
		} */
		
		return row.getLastCellNum();
	}
	
}
