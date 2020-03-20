import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class FeatureSelector {

	private static enum SearchAlg {
		FORWARD_SELECTION,
		BACKWARD_ELIMINATION
	}
	
	private static Scanner cin = null;
	
	private static SearchAlg promptAlgorithm() {
		
		System.out.println("Type the number of the algorithm you want to run:");
		System.out.println("1: Forward Selection");
		System.out.println("2: Backward Elimination");
		
		Set<Integer> validChoices = new HashSet<Integer>();
		validChoices.add(1);
		validChoices.add(2);
		
		int choice = cin.nextInt();
		while (!validChoices.contains(choice)) {
			
			System.out.println("Invalid choice " + Integer.toString(choice) + ".");
			System.out.println("Enter a choice from this selection: " + validChoices);
			
			choice = cin.nextInt();
		}
		
		switch (choice) {
		case 1:
			return SearchAlg.FORWARD_SELECTION;
		case 2:
			return SearchAlg.BACKWARD_ELIMINATION;
		default:
				return null;
		}
	}
	
	private static Scanner chooseFile() {
		
		System.out.println("Type in the name of the file to test,");
		System.out.println("or enter a number to use a built-in data set:");
		System.out.println("1: Small dataset (all students)");
		System.out.println("2: Large dataset (all students)");
		System.out.println("3: Small dataset (Ryan only)");
		System.out.println("4: Large dataset (Ryan only)");
		
		Set<Integer> validOptions = new HashSet<Integer>();
		for (Integer i = 1; i <= 4; i++)
			validOptions.add(i);
		
		// If a number for a default fileset was given
		if (cin.hasNextInt()) {
			
			Integer input_int = cin.nextInt();
			
			while (!validOptions.contains(input_int)) {
				
				System.out.println("Invalid number " + input_int + " detected. Try again.");
				input_int = cin.nextInt();
			}
			
			try {
				switch(input_int) {
				
				case 1:
					return new Scanner(new File("sample_data/cs_170_small80.txt"));
				case 2:
					return new Scanner(new File("sample_data/cs_170_large80.txt"));
				case 3:
					return new Scanner(new File("sample_data/cs_170_small25.txt"));
				case 4:
					return new Scanner(new File("sample_data/cs_170_large25.txt"));
				}
			} catch ( FileNotFoundException e ) {
				
				System.err.println("File not found. Verify sample_data/ exists and is populated.");
				e.printStackTrace();
			}
		}
		
		// If a custom filepath was given
		String custom_filepath = cin.next();
		try {
			return new Scanner(new File(custom_filepath));
		} catch (FileNotFoundException e) {
			System.out.println("File " + custom_filepath + " not found.");
			e.printStackTrace();
		}
		return null;
	}
	
	public static void run() {
		
		cin = new Scanner(System.in);
		
		System.out.println("Welcome to Ryan Villena's Feature Selection Algorithm.");
		Scanner file_sc = chooseFile();
		SearchAlg alg = promptAlgorithm();
		
		

		cin.close();
		file_sc.close();
	}
	
}
