import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tester {

	private static void testIEEEFileInput() {
		
		String filepath = "sample_data/cs_170_small80.txt";
		File dataFile = new File(filepath);
		Scanner sc;
		try {
			sc = new Scanner(dataFile);
		} catch (FileNotFoundException e) {
			System.err.println("File " + filepath + " not found.");
			System.err.println("pwd: " + System.getProperty("user.dir"));
			e.printStackTrace();
			return;
		}
		
		List< List<BigDecimal>> doubles = new ArrayList< List<BigDecimal>>();
		
		while (sc.hasNextLine()) {
			
			Scanner lineScanner = new Scanner(sc.nextLine());
			List<BigDecimal> thisRow = new ArrayList<BigDecimal>();
			
			while (lineScanner.hasNextBigDecimal())
				thisRow.add(lineScanner.nextBigDecimal());
			
			doubles.add(thisRow);
			
			lineScanner.close();
		}
		sc.close();	
		
		System.out.println("Got input from file " + filepath + ":");
		for (List<BigDecimal> thisRow : doubles)	
			System.out.println(thisRow);
	}
	
	public static void runTests() {
		
		testIEEEFileInput();
	}
}
