import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class FeatureSelector {

	private static enum SearchAlg {
		FORWARD_SELECTION,
		BACKWARD_ELIMINATION
	}
	
	private static Scanner sc = null;
	
	private static SearchAlg promptAlgorithm() {
		
		System.out.println("Type the number of the algorithm you want to run:");
		System.out.println("1: Forward Selection");
		System.out.println("2: Backward Elimination");
		
		Set<Integer> validChoices = new HashSet<Integer>();
		validChoices.add(1);
		validChoices.add(2);
		
		int choice = sc.nextInt();
		while (!validChoices.contains(choice)) {
			
			System.out.println("Invalid choice " + Integer.toString(choice) + ".");
			System.out.println("Enter a choice from this selection: " + validChoices);
			
			choice = sc.nextInt();
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
	
	private static File chooseFile() {
		
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
		if (sc.hasNextInt()) {
			
			Integer input_int = sc.nextInt();
			
			while (!validOptions.contains(input_int)) {
				
				System.out.println("Invalid number " + input_int + " detected. Try again.");
				input_int = sc.nextInt();
			}
			
			switch(input_int) {
			
			case 1:
				return new File("sample_data/cs_170_small80.txt");
			case 2:
				return new File("sample_data/cs_170_large80.txt");
			case 3:
				return new File("sample_data/cs_170_small25.txt");
			case 4:
				return new File("sample_data/cs_170_large25.txt");
			}
		}
		
		// If a custom filepath was given
		return new File(sc.next());
	}
	
	public static void run() {
		
		sc = new Scanner(System.in);
		
		System.out.println("Welcome to Ryan Villena's Feature Selection Algorithm.");
		File inputFile = chooseFile();
		SearchAlg alg = promptAlgorithm();

		sc.close();
	}
	
}
