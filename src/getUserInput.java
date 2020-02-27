import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class getUserInput {
	
	
	private static Scanner UserInput = new Scanner(System.in);
	private static Scanner FileInput;
	private static FileInputStream FileInputS;
	private static Boolean fileFound = false;
	public static void UserInput() {
		//Variables used to do random stuff, instead of having 3-4 different variables these three get reused
		Boolean settingChanging = false;
		Boolean temp = false;
		
		System.out.println("Would you like to use the preset configuration? (y or n)");
		String temp2 = UserInput.nextLine(); //<------ number 3
		
		do { //Whenever there is a do while loop utilizing variable temp, it is used to verify inputs. 
			if(temp2.equalsIgnoreCase("y") || temp2.equalsIgnoreCase("yes")) {
				try{  //This is a pretty jenk thing, it trys to find the file, and if it does then it continues, if it doesn't it errors out, moving to the catch segment which continues on like normal. GENIUS
					System.out.println("Checking for configuration file");
					FileInputS = new FileInputStream("./configuration.txt"); 
					FileInput = new Scanner(FileInputS);
					//Interestingly enough, if it errors out at any point copying over the settings the program switches over to manual input
					System.out.println("Configuration file found");
					Main.numberOfRounds = Integer.parseInt(FileInput.nextLine());
					Main.inputLocation = FileInput.nextLine();
					Main.outputLocation = FileInput.nextLine();
					Main.nameOfInputFiles = FileInput.nextLine();
					Main.nameOfOutputFile = FileInput.nextLine();
					Main.teamOrRound = Integer.parseInt(FileInput.nextLine());
					fileFound = true; //Marks in the program that the file was found and setting copied over successfully
					System.out.println("Settings copied over as follows: ");
					System.out.println("Rounds: " + Main.numberOfRounds //Output the settings, formatted like this to make it easier for me to read
							+ "\ninput location: " + Main.inputLocation
							+ "\noutput location: " + Main.outputLocation
							+ "\ninput workbook names: " + Main.nameOfInputFiles
							+ "\noutput workbook name: " + Main.nameOfOutputFile
							+ "\noutput by team or by round (0 = round, 1 = team): " + Main.teamOrRound);
					System.out.println("Would you like to change settings? (Y or N)");
					temp2 = UserInput.nextLine();
					Boolean temp3 = true; //Bad extra variable ;-;
					do {
						if(temp2.equalsIgnoreCase("y") || temp2.equalsIgnoreCase("yes")) {
							fileFound = false;
							temp3 = false;
							System.out.println("Leave the input blank for anything you want to keep the same.");
							settingChanging = true;
						}
						else if(temp2.equalsIgnoreCase("n") || temp2.equalsIgnoreCase("no") ) {temp3 = false;} // Do nothing
						else {
							System.out.println("Error on input, (y or n)");
							temp = true;
							Main.numberOfErrors++;
						}
					} while(temp3);
				} catch(IOException e) {
					fileFound = false;
					System.out.println("configuration.txt not found, switching to manual setting adjustment");
					Main.numberOfErrors++; //User error?
				}
			}
			else if(temp2.equalsIgnoreCase("n") || temp2.equalsIgnoreCase("no") ) {temp = false;} // Do nothing
			else {
	
			}
		} while(temp);
		
		
		
		if(!fileFound) {
			System.out.println("Please enter the number of rounds");
			temp2 = UserInput.nextLine();
			if(temp2.equals("") && settingChanging) {} //used to changing settings, if input is blank and setting changing is enabled
			else {
				Main.numberOfRounds = Integer.parseInt(temp2);
			}
			System.out.println("Please enter the complete file path towards where you want the completed workbook (ensure it ends with a slash)");
			temp2 = UserInput.nextLine();
			if(temp2.equals("") && settingChanging) {} //used to changing settings, if input is blank and setting changing is enabled
			else {
				Main.outputLocation = temp2;
			}
			System.out.println("Please enter the complete file path towards the location of the input workbooks (ensure it ends with a slash)");
			temp2 = UserInput.nextLine();
			if(temp2.equals("") && settingChanging) {} //used to changing settings, if input is blank and setting changing is enabled
			else {
				Main.inputLocation = temp2;
			}
			System.out.println("Please enter the name of the input files excluding the iteration number and file extension");
			temp2 = UserInput.nextLine();
			if(temp2.equals("") && settingChanging) {} //used to changing settings, if input is blank and setting changing is enabled
			else {
				Main.nameOfInputFiles = temp2;
			}
			System.out.println("Please enter the name of your desired completed workbook excluding the file extension");
			temp2 = UserInput.nextLine();
			if(temp2.equals("") && settingChanging) {} //used to changing settings, if input is blank and setting changing is enabled
			else {
				Main.nameOfOutputFile = temp2;
			}
			temp = false;
			do{ //Gets if the user wants to be in team or round mode, thinking about making the program have dual functionality. (inefficiently)
				System.out.println("Would you like to output by team or round? (type the word or abbreviate to the first letter)");
				temp2 = UserInput.nextLine();
				if(temp2.equalsIgnoreCase("t") || temp2.equalsIgnoreCase("team")) {
					Main.teamOrRound = 1;
					temp = false;
					System.out.println("Output is in team mode, please insure that the first column of each row has the team number");
				}
				else if(temp2.equalsIgnoreCase("r") || temp2.equalsIgnoreCase("round")) {
					Main.teamOrRound = 0;
					System.out.println("Output is in round mode");
					temp = false;
				}
				else {
					System.out.println("Error on input, please reinput");
					temp = true;
					Main.numberOfErrors++;
				}
			} while(temp);
			
			
			temp = false;
			do { //Saves the config for later. 
				System.out.println("Would you like to save this configuration for later? (y or n)");
				temp2 = UserInput.nextLine();
				if(temp2.equalsIgnoreCase("y") || temp2.equalsIgnoreCase("yes")) {
					
					try { //Try to make the file out stream
						System.out.println("Writing File");
						 FileWriter fileWriter = new FileWriter("./configuration.txt");
						 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
						 System.out.println("writer connected");
						 bufferedWriter.write(Main.numberOfRounds + "\n");
						 bufferedWriter.write(Main.inputLocation + "\n");
						 bufferedWriter.write(Main.outputLocation + "\n");
						 bufferedWriter.write(Main.nameOfInputFiles + "\n");
						 bufferedWriter.write(Main.nameOfOutputFile + "\n");
						 bufferedWriter.write(Main.teamOrRound + "\n");
						 System.out.println("lines written");
						 bufferedWriter.close();
						 fileWriter.close();
						System.out.println("File written and connection closed, continuing with rest of program.");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("Error making the file out stream");
						e.printStackTrace();
					}
					temp = false;
					
				}
				else if(temp2.equalsIgnoreCase("n") || temp2.equalsIgnoreCase("no") ) {temp = false;} // Do nothing
				else {
					System.out.println("Error on input, (y or n)");
					temp = true;
					Main.numberOfErrors++;
				}
			} while(temp);
		}
		
		
		try { //Closes the user input stream and writers, as they are no longer needed.
			System.out.println("Closing User Input readers and writers");
			if(fileFound) {
				FileInputS.close();
				FileInput.close();
			}
			UserInput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Failed closing in User Input, this is probably a big problem.");
			Main.numberOfErrors++;
		}
	}
}
