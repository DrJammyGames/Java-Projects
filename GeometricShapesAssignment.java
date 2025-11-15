package main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.TexturePaint;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

//Dr Yemaya Halbrook
public class GeometricShapesAssignment {

	public static void main(String[] args) {
		//Gets UI settings  
		UIManagerSettings.getInstance().applyUISettings();	
		//Separate method to start the actual prompts--neater imo
		start();		
	}
	public static void start() {
		//Show user what they will be doing for this activity
		JOptionPane.showMessageDialog(null, "Today, you are going to select some shapes and choose their parameters."
				+ "\nYou will select 1, 2, or 3 for the shape, then enter its parameters."
				+ "\nYou will do this five times.");
		//Array of geo objects--size 5
		GeometricObject[] geo = new GeometricObject[5];
		
		//Add a message here showing them how many they have left--separate method to return the string
		int numberLeftInt = 5;
		
		//region While loop for choosing shapes and parameters
		//Allows for a while loop--exits when they've selected all shapes
		boolean finishChoosing = false;
		while(!finishChoosing) {
			for(int i = 0; i < geo.length; i++) {
				//Separate method to check what the number is and return a string
				String numberLeft = numberToWord(numberLeftInt);			
				String shapeMessage = "You have " + numberLeft + " shapes left to choose."
						+ "\nSelect the cooresponding number for your shape:"
						+ "\n1. Square pyramid\n2. Triangular Pyramid\n3. Triangular Prism"; 
				try {					
					String shapeChoice = JOptionPane.showInputDialog(null, shapeMessage);
					if(shapeChoice == null) {
						//User cancels
						userCancel("Cancelled shape input.");
					}
					//Separate method to ensure the user can only select 1, 2, or 3
					else if(isShapeChoiceValid(shapeChoice)) {
						//Subtract number left
						numberLeftInt -= 1;
						//Declare int and parse the string to an int below
						int shapeChoiceInt;						
						shapeChoiceInt = Integer.parseInt(shapeChoice);		
						//Create a shape based on what number is in the array
						if(shapeChoiceInt == 1) {
							geo[i] = new SquarePyramid();
						}
						else if(shapeChoiceInt == 2) {
							geo[i] = new TriangularPyramid();
						}
						else if(shapeChoiceInt == 3) {
							geo[i] = new TriangularPrism();
						}
					}
					//Catch error for isShapeChoiceValid method
				} catch (IllegalArgumentException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				//Catch anything else that is unexpected
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, "An unexpected error has occurred.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				//Allows for while loop
				boolean squareChoicesValid = false;
				try {
					//Continue in the for loop checking what is in each location using instanceof
					//While choices are false--ensures it restarts if the user doesn't enter anything
					while(!squareChoicesValid && geo[i] instanceof SquarePyramid) {					
						String pyramidMessage = "For a square pyramid, I need three parameters:\nthe side length, slant height, and pyramid height."
								+ "\nPlease enter only numeric values.";
						JOptionPane.showMessageDialog(null, pyramidMessage);
						
						//Gets all inputs and validates it at each step so they are required to enter in a proper answer before moving on
						Double sideLength = validateInput("Square Pyramid:\nPlease enter a side length as a number.");
						//If user cancels instead of entering anything
						if(sideLength == null) {
							if(userCancel("Cancelled square pyramid.")) return;
							//Continue only if valid input and not blank
							continue;
						}
						Double slantHeight = validateInput("Square Pyramid:\nPlease enter a slant height as a number."); 
						if(slantHeight == null) {
							if(userCancel("Cancelled square pyramid")) return;
							continue;
						}
						Double height = validateInput("Square Pyramid:\nPlease enter a pyramid height as a number.");
						if(height == null) {
							if(userCancel("Cancelled square pyramid")) return;
							continue;
						}
						//Only create the object if all previous checks have passed
						geo[i] = new SquarePyramid(sideLength, slantHeight, height);						
						//Exit loop now that shape has been created
						squareChoicesValid = true;					
					}
				} catch (IllegalArgumentException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				//Catch anything else that's unexpected
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, "An unexpected error occured. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				//Same logic as above
				boolean triPyramidValid = false;
				try {
					while(!triPyramidValid && geo[i] instanceof TriangularPyramid) {
						String triPyramidMessage = "For a triangular pyramid, I need three parameters:\nlength, pyramid height, and slant height."
								+ "\nPlease enter only numeric values.";
						JOptionPane.showMessageDialog(null, triPyramidMessage);	
						//Gets all inputs and validates it at each step so they are required to enter in a proper answer
						Double length = validateInput("Triangular Pyramid:\nPlease enter a length as a number.");
						//If user cancels instead of entering anything
						if(length == null) {
							if(userCancel("Cancelled triangular pyramid.")) return;
							//Continue only if valid input and not blank
							continue;
						}
						Double pyramidHeight = validateInput("Triangular Pyramid:\nPlease enter a pyramid height as a number."); 
						if(pyramidHeight == null) {
							if(userCancel("Cancelled triangular pyramid")) return;
							continue;
						}
						Double slantHeight = validateInput("Triangular Pyramid:\nPlease enter a slant height as a number.");
						if(slantHeight == null) {
							if(userCancel("Cancelled square pyramid")) return;
							continue;
						}
						//Only create the object if all previous checks have passed
						//Replacing the one created above with one that has the specified parameters
						geo[i] = new TriangularPyramid(length, pyramidHeight, slantHeight);
						//Exit loop now that shape has been created
						triPyramidValid = true;		
					}
				} catch (IllegalArgumentException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				//Catch anything else that's unexpected
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, "An unexpected error occured. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				boolean triPrismValid = false;
				try {
					while(!triPrismValid && geo[i] instanceof TriangularPrism) {
						String triPrismMessage = "For a triangular prism, I need two parameters:\nlength and prism length";
						JOptionPane.showMessageDialog(null, triPrismMessage);
						//Gets all inputs and validates it at each step so they are required to enter in a proper answer
						Double length = validateInput("Triangular Prism:\nPlease enter a length as a number.");
						//If user cancels instead of entering anything
						if(length == null) {
							if(userCancel("Cancelled triangular prism.")) return;
							//Continue only if valid input and not blank
							continue;
						}
						Double prismLength = validateInput("Triangular Prism:\nPlease enter a prism length as a number."); 
						if(prismLength == null) {
							if(userCancel("Cancelled triangular prism")) return;
							continue;
						}						
						//Replacing the one created above with one that has the specified parameters
						geo[i] = new TriangularPrism(length,prismLength);
						//Exit while loop--all values have been entered
						triPrismValid = true;				
					}				
				} catch (IllegalArgumentException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				//Catch anything else that's unexpected
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, "An unexpected error occured. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
				//If all shapes have been selected and numberLeftInt is 0, exit the for loop
				if(numberLeftInt == 0) {
					finishChoosing = true;
					//Exits both loops
					break;
				}			
			}
			//endregion
		}
		
		//Sort the array based on surface area first
		Arrays.sort(geo, (shapeOne, shapeTwo) ->{
			double areaOne = ((EquilateralTriangle)shapeOne).getSurfaceArea();
			double areaTwo = ((EquilateralTriangle)shapeTwo).getSurfaceArea();
			//Using Double.compare instead of compareTo as it is better at handling errors like NaN cases
			//Still implemented the Comparable in the classes as per the assignment requirements
			return Double.compare(areaTwo,areaOne);
		});		
		//Printing sorted results
		//Biggest based on what is in the first spot
		EquilateralTriangle biggestSurface = (EquilateralTriangle)geo[0];
		//Smallest based on what's in the last spot 
		EquilateralTriangle smallestSurface = (EquilateralTriangle)geo[geo.length - 1];
		String surfaceAreaMessage = String.format("Surface Area Comparison:\n"
				+ "Largest shape: %s (%.2f)\n"
				+ "Smallest shape: %s (%.2f)\n",
				biggestSurface.getClass().getSimpleName(),biggestSurface.getSurfaceArea(),
				smallestSurface.getClass().getSimpleName(),smallestSurface.getSurfaceArea());
		JOptionPane.showMessageDialog(null, surfaceAreaMessage);
		
		
		//Then sort by volume so the information for both can be displayed
		Arrays.sort(geo, (shapeOne, shapeTwo) ->{
			double areaOne = ((EquilateralTriangle)shapeOne).getVolume();
			double areaTwo = ((EquilateralTriangle)shapeTwo).getVolume();
			return Double.compare(areaTwo,areaOne);
		});
		//Printing sorted results
		//Biggest based on what is in the first spot
		EquilateralTriangle biggestVolume = (EquilateralTriangle)geo[0];
		//Smallest based on what's in the last spot 
		EquilateralTriangle smallestVolume = (EquilateralTriangle)geo[geo.length - 1];
		//Set info with simple name so the user knows what the shape is
		String volumeMessage = String.format("Volume Comparison:\n"
				+ "Largest shape: %s (%.2f)\n"
				+ "Smallest shape: %s (%.2f)\n",
				biggestVolume.getClass().getSimpleName(),biggestVolume.getVolume(),
				smallestVolume.getClass().getSimpleName(),smallestVolume.getVolume());
		JOptionPane.showMessageDialog(null, volumeMessage);
		
	}
	//region Validity checks
	//Check for choice 1, 2, or 3
	private static boolean isShapeChoiceValid(String choice) {
		//Details error catching here for selecting 1, 2, or 3
		try {
			//Declare int for choice
			int choiceInt;
			//Change String parameter to int
			choiceInt = Integer.parseInt(choice);
			//If the number is less than 1 or greater than 3
			if(choiceInt < 1 || choiceInt > 3) {
				throw new IllegalArgumentException("Invalid choice. Please select a whole number between 1 and 3.");
			}
			return true;
			//If user enters something other than a number
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid number. Please enter a numeric value.");			
		}
	}
	//Return correct string for dialogue message
	private static String numberToWord(int num) {
		switch(num) {
			case 5: return "five";
			case 4: return "four";
			case 3: return "three";
			case 2: return "two";
			case 1: return "one";
			default: return "";
		}
	}	
	private static boolean userCancel(String action) {
		//Check if they want to cancel
		int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the program?","Exit confirmation",
				JOptionPane.YES_NO_OPTION);
		//If they cancel, thank them and exit
		if(confirm == JOptionPane.YES_OPTION) {
			JOptionPane.showMessageDialog(null, "Have a great day!");
			System.exit(0);
		}
		return confirm == JOptionPane.YES_OPTION;
	}
	//If at any point the user cancels an input option
	private static Double validateInput(String prompt) {
		while(true) {
			String input = JOptionPane.showInputDialog(null, prompt);
			//Handle user cancellation
			if(input == null) {
				return null;
			}
			//Empty input
			if(input.trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Input cannot be empty!","Error",JOptionPane.ERROR_MESSAGE);
				continue;
			}		
			//Numeric conversion from input choice
			try {
				return Double.parseDouble(input);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null,"Invalid input. Please enter a numeric value","Error",JOptionPane.ERROR_MESSAGE);
			}
		}		
	}
	//endregion
}

class EquilateralTriangle extends GeometricObject {
	 
	private TexturePaint colour; 
	protected double length;
	//Default length
	public EquilateralTriangle() {
		length = 1.0;
	}
	//Constructor to set length as a parameter
	public EquilateralTriangle(double length) {
		this.length = length;
	}
	//Getter for length
	public double getLength() {
		return length;
	}
	//Setter for length
	public void setLength(double length) {
		this.length = length;
	}
	//Getter for colour
	public TexturePaint getColour() {
		return colour;
	}
	//Setter for colour
	public void setColour(TexturePaint colour) {
		this.colour = colour;
	}
	//Override the GeometricObject class to calculate the area of an Equilateral Triangle specifically
	@Override
	public double getSurfaceArea() {
		//Calculating the area for an equilateral triangle using the getLength method
		return (Math.sqrt(3) / 4) * Math.pow(getLength(), 2);
	}	
	//Same here, but for perimeter--need for some of the geometry calculations
	@Override
	public double getPerimeter() {
		//Calculating the perimeter of the triangle
		//Using the get method for length so it'll calculate based on whatever length was declared
		return 3 * getLength();
	}	
	//And volume
	@Override
	public double getVolume() {
		return 0;
	}		
}
class SquarePyramid extends EquilateralTriangle implements Comparable<EquilateralTriangle>{
	//Need length of square (different than the length in the super class)
	private double sideLength;
	//Slant height of pyramid
	private double slantHeight;
	//Perpendicular height of the pyramid
	private double height;
	
	//Default constructor
	public SquarePyramid() {
		//Don't need to access length from super class--not relevant to a square pyramid
		sideLength = 1;
		slantHeight = 1;
		height = 1;
	}
	
	//Constructor for parameters
	public SquarePyramid(double sideLength, double slantHeight, double height) {
		this.sideLength = sideLength;
		this.slantHeight = slantHeight;
		this.height = height;
	}
	//Calculate surface area
	@Override
	public double getSurfaceArea() {
		//Base area = sideLength squared
		double baseArea = Math.pow(sideLength, 2);
		//Lateral surface area
		double lateralArea = 2 * sideLength * slantHeight;
		//Total surface area
		return baseArea + lateralArea;
	}
	//Calculate volume
	@Override
	public double getVolume() {
		//Base area = sideLength squared
		double baseArea = Math.pow(sideLength, 2);
		//Total volume 
		return (baseArea * height) / 3;
	}
	//Compare surface area
	public int compareTo(EquilateralTriangle other) {
		return Double.compare(this.getSurfaceArea(),other.getSurfaceArea());
	}
	//Compare volume
	public int compareVolume(EquilateralTriangle other) {
		return Double.compare(this.getVolume(),other.getVolume());
	}
	 
 }
class TriangularPyramid extends EquilateralTriangle implements Comparable<EquilateralTriangle>{
	//Need pyramid and slant heights to calculate the surface area and volume
	private double pyramidHeight;
	private double slantHeight;
	//Default constructor
	public TriangularPyramid() {
		length = 1;
		pyramidHeight = 1;
		slantHeight = 1;
	}
	//Constructor with parameters
	public TriangularPyramid(double length, double pyramidHeight, double slantHeight) {		
		this.length = length;
		this.pyramidHeight = pyramidHeight;
		this.slantHeight = slantHeight;
	}
	@Override
	//Calculate surface area
	public double getSurfaceArea() {
		//Get base area from super class
		double baseArea = super.getSurfaceArea();
		//Perimeter of equilateral triangle
		double basePerimeter = 3 * getLength();
		//Formula for surface area
		return baseArea + 0.5 * basePerimeter * slantHeight;
	}
	//Calculate volume
	@Override
	public double getVolume() {
		//Gets the area from the super class
		double baseArea = super.getSurfaceArea();
		//Calculates the actual volume
		return (baseArea * pyramidHeight) / 3;
	}
	public int compareTo(EquilateralTriangle other) {
		return Double.compare(this.getSurfaceArea(),other.getSurfaceArea());
	}
	public int compareVolume(EquilateralTriangle other) {
		return Double.compare(this.getVolume(),other.getVolume());
	}
	
 }
class TriangularPrism extends EquilateralTriangle implements Comparable<EquilateralTriangle>{	
	//Length of the prism 
	private double prismLength;
	
	//Default constructor
	public TriangularPrism() {
		length = 1;
		prismLength = 1;
	}
	
	public TriangularPrism(double length, double prismLength) {		
		this.length = length;
		this.prismLength = prismLength;
	}
	//Calculate surface area
	@Override
	public double getSurfaceArea() {
		//Get the base area from the super class
		double baseArea = super.getSurfaceArea();
		//Get perimeter from super class
		double basePerimeter = super.getPerimeter();
		//Surface area formula
		return (2 * baseArea) + (basePerimeter * prismLength);
	}
	//Calculate volume
	@Override
	public double getVolume() {
		//Base area from super class
		double baseArea = super.getSurfaceArea();
		//Volume formula
		return baseArea * prismLength;
	}	 
	public int compareTo(EquilateralTriangle other) {
		return Double.compare(this.getSurfaceArea(),other.getSurfaceArea());
	}
	public int compareVolume(EquilateralTriangle other) {
		return Double.compare(this.getVolume(),other.getVolume());
	}
 }
abstract class GeometricObject {
	private String colour;
	 
	//Default constructor
	protected GeometricObject() {}
		public String getColor() {
		return colour;
	}
	abstract public double getPerimeter();
	abstract public double getSurfaceArea();
	abstract public double getVolume();
}
//Set parameters so dialogue box and font are larger	
class UIManagerSettings{
	//Holds a single instance of the class
	private static UIManagerSettings instance;
	private Font messageFont;
	private Font buttonFont;
	private Dimension buttonSize;
	//Set font sizes here so it's easier to change later if needed
	private int messageFontSize = 24;
	private int buttonFontSize = 20;
	private int inputFontSize = 20;
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