import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class Tester {
	
	private static void testIEEEFileInputToMap() {
		
		String filepath = "sample_data/cs_170_small80.txt";
//		String filepath = "sample_data/cs_170_large80.txt";
		
		Map<Integer, Set<List<BigDecimal>>> data = DataGetter.parseData(filepath);
		System.out.println("Got input from file " + filepath + ":");
		
		for (Entry<Integer, Set<List<BigDecimal>>> thisEntry : data.entrySet()) {
			
			Integer key = thisEntry.getKey();
			System.out.println(key + ":");
			
			Set<List<BigDecimal>> instances = data.get(key);
			
			for (List<BigDecimal> thisInstance : instances)
				System.out.println(thisInstance);
		}
	}

	private static void testIEEEFileInputToLists() {
		
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
		
//		testIEEEFileInputToLists();
		
		testIEEEFileInputToMap();
	}
}
