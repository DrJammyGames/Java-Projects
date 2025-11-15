//Dr Yemaya Halbrook
package main;

import java.awt.Dimension;
import java.awt.Font;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

//I would normally use different classes (therefore, different Java files for readability),
//but the assignment requires one Java file, so everything is here
public class Main{
	public static void main(String[] args) {
		//Gets settings  
		UIManagerSettings.getInstance().applyUISettings();
		//Run the start method here, which ultimately runs the other methods in order
		//Have separate Start method instead of having the code here for better readability
		start();
		//Each scenario will then be inside specific methods so it proceeds accordingly
	}
	//Method for the start of game
	public static void start() {		
		//Show intro method
		JOptionPane.showMessageDialog(null, "Welcome to the adventure game, please choose a name for your player.");
		//Asks player for userName input--starts blank
		String userName = "";
		//Set boolean to false to determine if player should be asked for their name again
		boolean validName = false;
		//While the boolean is false (name is invalid) go through the while loop
		while(!validName) {
			//try-catch for errors
			try {
				userName = JOptionPane.showInputDialog("Please enter your name: ");				
				//User clicked cancel
				if(userName == null) {
					//userCancel is a separate method for allowing the user to confirm cancellation
					//Separate method so it doesn't have to be written multiple times
					userCancel("Name cancelled");
				}
				//Checks if name is valid with isNameValid method and userName as the String parameter
				else if(isNameValid(userName)) {
					validName = true;
					//Move on to first scenario--userName is the string parameter here as well
					firstScenario(userName);
				}
				//If the user entered something they shouldn't
				else {
					throw new IllegalArgumentException("Incorrect username. Please enter a username that:\n\n"
							+ "Contains only alphanumeric and/or underscore characters, with a minimum\n"
							+ "length of 2 and a maximum length of 15.");
				}
				//Shows user their name is invalid
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			//Catches any other issue
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "An unexpected error occured. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	//Allows name input so it can be referenced throughout
	public static void firstScenario(String name) {
		//Allows for a while loop
		boolean choiceValid = false;
		//If the choice is invalid, continue looping through while loop
		while(!choiceValid) {
			//try-catch for errors
			try {
				//Using a string format with html formatting in order to bold the name like in the assignment example
				String message = String.format("<html><b>%s</b> finds themselves lost in a dense forest with no clear path forward.<br><br>"
						+ "Choices (please enter the corresponding number):<br><br>" //More clear they need to enter a number
						+ "1 - Climb a tree to get a better view<br>" //Using <br><br> as this uses html formatting. Otherwise, it'd be \n
						+ "2 - Build a shelter and wait for rescue<br>"
						+ "3 - Follow a stream to see if it leads to civilisation", name);
				//Displays the message above for the input dialogue with the bolded formatting
				String choice = JOptionPane.showInputDialog(null, message);
				
				//If user cancels
				if(choice == null) {
					userCancel("Choice is blank");
				}
				//Provides the message dialogue if choice entered is valid, checked in isChoiceValid method
				else if(isChoiceValid(choice)) {
					//I know we *can* use the same String name of 'message' for all
					//Since they are enclosed in the if statements. That gets confusing for me
					//So I prefer to label them by each step
					String messageTwo = String.format("<html><b>%s</b> continues their journey", name);
					JOptionPane.showMessageDialog(null, messageTwo);
					//Exits while loop as choice is valid
					choiceValid = true;
					//Inputs name again as the String parameter
					secondScenario(name);
				}				
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			} 
			//Catch anything else unexpected
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "An unexpected error occured. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}		
	}
	public static void secondScenario(String name) {
		//Imports random class so a percentage can be chosen based on the user's choice
		Random rand = new Random();
		int randChance = rand.nextInt(100);
		//Boolean again to check if input is valid
		boolean choiceValid = false;
		while(!choiceValid) {
			try {
				//Again, sets the message with a string format to bold the name like the assignment example
				String message = String.format("<html>While wandering through the forest, <b>%s</b> encounters a mysterious"
						+ " stranger who offers them assistance.<br> <b>%s</b> must decide whether to trust the stranger.<br><br>"
						+ " Choices (please enter the corresponding number):<br><br>"
						+ "1 - Accept the stranger's help<br>"
						+ "2 - Politely decline and continue on your own<br>"
						+ "3 - Confront the stranger and demand answers", name, name);
				//Has choice input for the message above
				String choice = JOptionPane.showInputDialog(null, message);
				
				if(choice == null) {
					//User hit cancel
					userCancel("Empty second scenario");
				}
				else if(isChoiceValid(choice)) {
					//Declared a new int
					int choiceInt;
					//Changed the String choice to choiceInt
					choiceInt = Integer.parseInt(choice);
					//Allows for exit of while loop
					choiceValid = true;
					if(choiceInt == 1) {
						//If chance is greater than 30% (70% chance of this occurring)
						if(randChance > 30) {						
							String messageChoiceOne = String.format("<html><b>%s</b> is saved!",name);
							JOptionPane.showMessageDialog(null, messageChoiceOne);
							//Calls the restart game method with the message as the parameter
							restartGame("You were saved!");
						}
						//If chance is less than 30%
						else if(randChance <= 30) {
							String messageChoiceTwo = String.format("<html><b>%s</b> is killed!",name);
							JOptionPane.showMessageDialog(null, messageChoiceTwo);
							restartGame("You were killed by a stranger!");
						}
					}
					else if(choiceInt == 2) {
						String messageChoiceThree = String.format("<html><b>%s</b> continues their journey",name);
						JOptionPane.showMessageDialog(null, messageChoiceThree);
						//Leads into the third scenario
						thirdScenario(name);
					}
					else if(choiceInt == 3) {
						String messageChoiceFour = String.format("<html>The stranger offers <b>%s</b> to play a game.", name);
						JOptionPane.showMessageDialog(null, messageChoiceFour);
						//Leads into the fourth scenario
						fourthScenario(name);
					}	
				}			
				//Catch error from isChoiceValid method
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			} 
			//Catch anything else that's unexpected
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "An unexpected error occured. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}		
	}
	//Third scenario if player chooses 2 in scenario 2
	public static void thirdScenario(String name) {
		boolean choiceValid = false;
		while(!choiceValid) { 
			try {
				//I'm fine using just message here again as it's a new method. But I prefer to name all my variables differently if
				//it's in a single method
				String message = String.format("<html><b>%s</b> comes across a wide river blocking their path. <b>%s</b> must figure"
						+ " out how to cross it safely.<br><br>"
						+ "Choices (please enter the corresponding number):<br><br>"
						+ "1 - Attempt to swim across the river<br>"
						+ "2 - Look for a shallow area to wade through<br>"
						+ "3 - Build a makeshift raft to cross the river", name, name);
				String choice = JOptionPane.showInputDialog(null, message);
				if(choice == null) {
					//User hits cancel
					userCancel("Cancelled third scenario");
				}
				//Checks if choice is valid in isChoiceValid method
				else if(isChoiceValid(choice)) {
					//Sets to true so while loop can be exited
					choiceValid = true;
					int choiceInt;
					//Changed the String choice to choiceInt
					choiceInt = Integer.parseInt(choice);
					if(choiceInt == 1) {
						String messageOne = String.format("<html><b>%s</b> drowns!", name);
						JOptionPane.showMessageDialog(null, messageOne);
						//Calls restart game method
						restartGame("You drowned!");
					}
					else if(choiceInt == 2 || choiceInt == 3) {
						String messageTwo = String.format("<html><b>%s</b> is saved!", name);
						JOptionPane.showMessageDialog(null, messageTwo);
						restartGame("You were saved!");
					}
				}
				//Catch error from isChoiceValid method
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			} 
			//Catch anything else that's unexpected
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "An unexpected error occured. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	//Game dialogue scenario if player chooses 3 in scenario 2
	public static void fourthScenario(String name) {		
		//Set up how many tries the user has
		int tries = 3;
		Random rand = new Random();
		//Select a random number between 1 and 10
		int randNum = rand.nextInt(1,11);
		//Add the while loop for tries being more than or equal to 1
		while(tries >= 1) {
			try {
				//Set blank String first before changing it according to number selected
				String tryString = "";
				//Changes the int to capital String numbers like in assignment
				if(tries == 1) {
					tryString = "ONE FINAL try";
				}
				else if(tries == 2) {
					tryString = "TWO tries";
				}
				else if(tries == 3) {
					tryString = "THREE tries";
				}
				//Dialogue to show user what they need to select and how many tries they have left
				String choice = JOptionPane.showInputDialog(null,"The game is to guess the number hidden in the mysterious box.\n"
						+ "It should be between 1 and 10 (inclusive). You have " + tryString + " left.");
				
				if(choice == null) {
					//User hits cancel
					userCancel("Cancelled fourth scenario");
				}				
				//If number is in between 1 and 10				
				else if(isGameInputValid(choice)){	
					int choiceInt;
					choiceInt = Integer.parseInt(choice);
					//If the user guesses correctly
					if(choiceInt == randNum) {
						String messageOne = String.format("<html><b>%s</b> is saved!", name);
						JOptionPane.showMessageDialog(null, messageOne);
						//Calls restart game method
						restartGame("You were saved!");
					}
					//If the number is incorrect
					else if(choiceInt != randNum) {
						//Subtract a try each time the player gets it wrong
						tries -= 1;
						//If tries are now over, game over
						if(tries <= 0) {
							//Calls restart game method
							restartGame("You are out of tries!");
						}
						//If number is incorrect, but tries are great than 0, ask question again
						else {
							//Sets blank at first for if the user needs to guess higher or lower
							String numDirection = "";
							
							//Checks if user guess is higher or lower than random number
							if(choiceInt > randNum) {
								//Sets string to tell user which direction to go
								numDirection = "LOWER";
							}
							else {
								numDirection = "HIGHER";
							}
							String messageTwo = String.format("<html>INCORRECT. You should aim [" + numDirection + "] "
									+ "in your next try, <b>%s</b><br>",name); 
							JOptionPane.showMessageDialog(null, messageTwo);
						}
					}					
				}
				//Catch non-numerical or incorrect number			
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			} 		
			//Catch anything unexpected
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "An unexpected error occured. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
			}			
		}
	}
	
	//Method to restart game--String outcome is declared when asking the user if they want to play again
	//This allows for each scenario to have it's own outcome message, i.e., drowned, saved, etc. 
	private static void restartGame(String outcome) {
		//Boolean check to keep while loop running
		boolean checkExit = false;
		while(!checkExit) {
			String message = String.format("<html>Game over! %s<br><br>Would you like to try again?<br>Please enter Y or N", outcome);
			String choice = JOptionPane.showInputDialog(null,message);
			try {
				if(choice == null) {
					//User hits cancel
					userCancel("Cancelled restart");
				}
				//If they want to start again
				else if(choice.equalsIgnoreCase("Y")) {
					//Go back to the start
					start();
					//Exit while loop
					checkExit = true;
				}		
				//They want to end
				else if(choice.equalsIgnoreCase("N")) {
					JOptionPane.showMessageDialog(null, "Thank you for playing!");
					System.exit(0);
				}
				//They enter something other than Y, y, N, or n
				else {
					throw new IllegalArgumentException("Invalid choice. Please enter Y or N");
				}
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}	
			//Catch anything else that's unexpected
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "An unexpected error occured. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	//Checks if username is valid
	private static boolean isNameValid(String name) {
		//Is the name less than 2 characters or greater than 15 OR does it contain something it shouldn't
		//Return false if so
		if(name.length() < 2 || name.length() > 15 || name.matches(".*[^a-zA-Z0-9_].*")) {
			return false;
		}
		//If both those parameters are met, the continue as normal
		else return true;
	}
	//Checks if choice is valid
	private static boolean isChoiceValid(String choice) {
		//Details error catching here as the first three scenarios require 1, 2, or 3 as the choice
		try {
			//Declared a new int
			int choiceInt;
			//Changed the String choice to choiceInt
			choiceInt = Integer.parseInt(choice);
			//If the number is less than 1 or greater than 3
			if(choiceInt < 1 || choiceInt > 3) {
				throw new IllegalArgumentException("Invalid choice. Please select a number between 1 and 3.");
			}
			return true;
			//If user enters something other than a number
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid number. Please enter a numeric value.");
		}		
	}
	//Checks if user inputs a number between 1 and 10 for the fourth scenario
	private static boolean isGameInputValid(String choice) {
		//Details error catching here if under 1 or over 10
		try {
			int choiceInt;
			//Change String to int
			choiceInt = Integer.parseInt(choice);
			//If number is less than 1 or greater than 10
			if(choiceInt < 1 || choiceInt > 10) {
				throw new IllegalArgumentException("Invalid choice. Please select a number between 1 and 10.");
			}
			return true;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid number. Please enter a numeric value.");
		}
	}
	//If at any point the user cancels an input option, perform method below
	private static boolean userCancel(String action) {
		//Check if they want to cancel
		int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the game?","Exit confirmation",
				JOptionPane.YES_NO_OPTION);
		//If they cancel, thank them and exit
		if(confirm == JOptionPane.YES_OPTION) {
			JOptionPane.showMessageDialog(null, "Thank you for playing!");
			System.exit(0);
		}
		return confirm == JOptionPane.YES_OPTION;
	}
	//Set parameters so dialogue box and font are larger
	//Bad eyesight--this is needed for me. Normally, I'd make it it's own class/separate Java file
	public static class UIManagerSettings{
		//Holds a single instance of the class
		private static UIManagerSettings instance;
		private Font messageFont;
		private Font buttonFont;
		private Dimension buttonSize;
		//Set font sizes here so it's easier to change later if needed
		private int messageFontSize = 32;
		private int buttonFontSize = 24;
		private int inputFontSize = 24;
		//Set some default fonts and dimensions
		private UIManagerSettings() {
			//Initialise the default settings
			messageFont = new Font("Arial", Font.PLAIN, messageFontSize);
			buttonFont = new Font("Arial", Font.PLAIN, buttonFontSize);
			buttonSize = new Dimension(120, 40);
		}
		//Returns the instance of the UI settings and provides access to the single instance with the saved settings
		public static UIManagerSettings getInstance() {
			//Checks if it's been created yet
			if(instance == null) {
				//Creates a new one if not
				instance = new UIManagerSettings();
			}
			return instance;
		}
		//Actually applies the settings outlined above
		public void applyUISettings() {
			UIManager.put("OptionPane.messageFont",messageFont);
			UIManager.put("OptionPane.buttonFont", buttonFont);
			UIManager.put("OptionPane.buttonSize", buttonSize);
			UIManager.put("TextField.font", new Font("Arial", Font.PLAIN, inputFontSize));
		}
	}
}

