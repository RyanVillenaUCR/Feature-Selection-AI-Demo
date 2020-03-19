import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
	
	/**
	 * Finds the maximum and minimum value of the dataset.
	 * @param data Dataset
	 * @return Array of values where arr[0] is the minumum and arr[1] is the maximum
	 */
	public static BigDecimal[] getMinMax(Map<Integer, Set<List<BigDecimal>>> data) {
		
		BigDecimal[] minMax = new BigDecimal[2];
		minMax[0] = minMax[1] = null;
		
		for (Entry<Integer, Set<List<BigDecimal>>> dataEntry : data.entrySet()) {
			
			Set<List<BigDecimal>> rows = dataEntry.getValue();
			for (List<BigDecimal> thisRow : rows) {
				
				for (BigDecimal thisDataPoint : thisRow) {
					
					// If empty, populate with the first data point
					if (minMax[0] == null) {
						minMax[0] = minMax[1] = thisDataPoint;
						continue;
					}
					
					// Check for a new minimum
					if (thisDataPoint.compareTo(minMax[0]) < 0)
						minMax[0] = thisDataPoint;
					
					// Check for a new maximum
					if (thisDataPoint.compareTo(minMax[1]) > 0)
						minMax[1] = thisDataPoint;
				}
			}
			
			
		}
		
		return minMax;
	}

	public static void normalize(Map<Integer, Set<List<BigDecimal>>> data) {
		
		BigDecimal[] minMax = getMinMax(data);
		
		BigDecimal oldLo = minMax[0];
		BigDecimal oldHi = minMax[1];
		BigDecimal newLo = BigDecimal.ZERO;
		BigDecimal newHi = BigDecimal.ONE;
		
		for (Entry<Integer, Set<List<BigDecimal>>> entry : data.entrySet()) {
			
			Set<List<BigDecimal>> thisSet = entry.getValue();
			
			for (List<BigDecimal> thisList : thisSet) {
				
				for (int i = 0; i < thisList.size(); i++) {
					
					BigDecimal normalized = Util.mapToRange(
						oldLo, oldHi, newLo, newHi, thisList.get(i));
					
					thisList.set(i, normalized);
				}
			}
		}
	}
}
