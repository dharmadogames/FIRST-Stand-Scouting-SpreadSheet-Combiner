import java.io.IOException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class Main {

	
	static Workbook wb = new XSSFWorkbook(); 
	static Workbook wbCopyFrom[] = {null,null,null,null,null,null};
	
	//All variables are handled inside program, changing them here may break the program, but probably not.
	public static int numberOfRounds = 3; //Number of rounds
	public static String inputLocation = null; //file location for input
	public static String outputLocation = null; //file location for output
	public static String nameOfInputFiles = null; //used to store file input names
	public static String nameOfOutputFile = null; //used to store file output name
	public static int teamOrRound = 0; //dictates if the program will run with the output being per team or per round. 0 is round, 1 is team
	public static int numberOfWorkbooks = 6; //Input workbooks, this is the only variable that must be changed in the program, also it doesn't have an effect on the program.
	public static int numberOfErrors = 0; //Just a fun little thing that gets incremented whenever there is an error that doesn't kill the program.
	
	public static void main(String[] args){
		System.out.println("Starting Program");
		System.out.println("Reading configuration");
		getUserInput.UserInput(); ///Get the user commands, either manually or from the config file
		
		try {
			System.out.println("Finding inputs");
			for(int i = 0; i < numberOfWorkbooks; i++) { //Get the workbooks to copy from
				wbCopyFrom[i] = WorkbookInputArrayCreation.WorkbookInputArray(i);	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Getting headers");
		String[] Headers = copyRound.gatherHeaders(wbCopyFrom[0]); //Get the headers from the first workbook
		System.out.println("Headers read");
		System.out.println("Making the worksheet");
		try{
		wb = FinalWorkbookCreation.finalWorkbookCreation(numberOfRounds,Headers); //Create the copy to workbook
		} catch(IOException e) {
			e.printStackTrace();
		}
		System.out.println("Output sheet made");
		System.out.println("Copying over");
		if(teamOrRound == 0) {
			copyRound.CopyRound(wbCopyFrom, wb, numberOfRounds); //Copy everything in the round format
		}
		else if (teamOrRound == 1) {
			copyTeam.copyEachTeam(wbCopyFrom, wb, numberOfRounds, Headers); //Copy everything in the team format
		}
		else {
			System.out.println("Error, please run program again and make new config"); //Config is busted, must create a new config
		}
		System.out.println("Copy complete");
		
		
		
		
		
		/*
		<-------------------->
		Close all opened stuff
		*/
		System.out.println("Closing lose ends");
		try {
			WorkbookInputArrayCreation.closeWorkbookInputs();
			FinalWorkbookCreation.closeOutput();
			for(int i = 0; i < wbCopyFrom.length; i++) {
				wbCopyFrom[i].close();
			}
			wb.close();
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		/*
		<-------------------->
		*/
		System.out.println("Program completed succesfully with " + numberOfErrors + " errors ^.^");
	}

}




/*
 Notes to self:
 	:)
 */

