import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class DataGetter {

	public static Map<Integer, Set<List<BigDecimal>>> parseData(String filepath) {
		
		File datafile = new File(filepath);
		Scanner fileScanner;
		try {
			fileScanner = new Scanner(datafile);
		} catch (FileNotFoundException e) {
			System.err.println("File " + filepath + " not found.");
			System.err.println("pwd: " + System.getProperty("user.dir"));
			e.printStackTrace();
			return null;
		}
		
		System.out.print("Fetching data... ");
		
		Map<Integer, Set<List<BigDecimal>>> data = new HashMap<Integer, Set<List<BigDecimal>>>();
		while (fileScanner.hasNextLine()) {
			
			// First, read this line
			Scanner sc = new Scanner(fileScanner.nextLine());

			List<BigDecimal> decimals = new ArrayList<BigDecimal>();
			
			Integer key = (int) Math.round(sc.nextDouble());
			
			while (sc.hasNextBigDecimal())
				decimals.add(sc.nextBigDecimal());
			sc.close();
			
			
			
			// If Integer isn't yet in data,
			// Insert it and give it its first set and list
			if (!data.containsKey(key)) {
				
				Set<List<BigDecimal>> s = new HashSet<List<BigDecimal>>();
				s.add(decimals);
				
				data.put(key, s);
			}
			
			// Else, just add the list to the current set
			else {
				
				Set<List<BigDecimal>> s = data.get(key);
				s.add(decimals);
			}
		}
		fileScanner.close();
		
		System.out.println("done!");
		
		return data;
	}
	
	public static BigDecimal[] getMaxMin(Map<Integer, Set<List<BigDecimal>>> data) {
		
		return null;
	}
}
