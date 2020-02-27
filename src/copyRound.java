import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class copyRound {
	public static void CopyRound(Workbook workbooksToCopyFrom[], Workbook finalWorkbookInput, int TotalNumberOfRounds) {
		
		Sheet sheetToCopyTo = null;
		Sheet sheetToCopyFrom = null;
		Row rowToCopyTo = null;
		Row rowToCopyFrom = null;
		
		//Plan for function
		//Copy each row from the 6 different workbooks into 6 different rows for each sheet
		
		for(int i = 0; i < TotalNumberOfRounds; i++) { //For each round things need to be copied
			sheetToCopyTo = finalWorkbookInput.getSheetAt(i);	//Grab the sheet to copy to
			System.out.println("Found copy to sheet   :" + i );
			for(int i2 = 0; i2 < workbooksToCopyFrom.length; i2++) { //Input sheet counter
				//Copy each workbook on each row from line i and copy to sheet i2 
				sheetToCopyFrom = workbooksToCopyFrom[i2].getSheetAt(0); // Grab each sheet to copy each row
				System.out.println("Found copy from sheet    :" + i2);
				rowToCopyTo = sheetToCopyTo.getRow(i2+1); //+1 to include header, i2 is because it is transferring each sheet onto a single row
				rowToCopyFrom = sheetToCopyFrom.getRow(i+1); //+1 to include header, i is because it needs to grab the round from the list of all rounds
				int rowWidth = getRowWidth(rowToCopyFrom); 	
				if(rowWidth > 0) {
						for( int i3 = 0; i3 < rowWidth; i3++) {
							Cell cellToCopyFrom = rowToCopyFrom.getCell(i3); //Get the copy from cell from the copy from row
							Cell cellToCopyTo = rowToCopyTo.createCell(i3); //Get the copy to cell from the copy to row
							
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
								System.out.println("Placed a cell on row: " + (i2+1) + " Column: " + (i3+1) + " On sheet: " + i); //easy to read output
								
							} catch (java.lang.NullPointerException exception) {
								System.out.println("Null cell, this is a problem");
							}
							//System.out.println(i + "  " + i2 + "  " + i3); //Hard to read output
							 
						}
				}	
			}
		}
	}
	public static int getRowWidth(Row row){
		//Wrong statement, didn't realise how to correctly utilize the command inside, code has been cleaned. 
			//Custom logic to find the width of a cell, as there isn't a default command
		//Being kept because it makes it easier to read
		return row.getLastCellNum();
	}
	
	public static String[] gatherHeaders(Workbook workbookToCopyHeadersFrom) {
		Sheet sheetToCopyFrom = workbookToCopyHeadersFrom.getSheetAt(0); //Grab the sheet to copy from
		Row rowToCopyFrom = sheetToCopyFrom.getRow(0); //Specifically the top row, the one with headers
		int numberOfCells = getRowWidth(rowToCopyFrom);
		ArrayList<String> tempArrayList = new ArrayList<String>();
		for(int i = 0; i < numberOfCells; i++) {
			tempArrayList.add(rowToCopyFrom.getCell(i).getStringCellValue());
		}
		String temp[] = tempArrayList.toArray(new String[tempArrayList.size()]);
		return temp;
	}
}