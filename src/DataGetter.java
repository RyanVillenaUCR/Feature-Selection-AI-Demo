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

	/**
	 * DEPRACATED: Wrong way to normalize.
	 * @param data 
	 */
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

	public static void normalize(List<List<BigDecimal>> data) {
		
		assert (data.size() >= 2) :
			"Input must have a column for class name and at least one feature\n";
		
		// Iterate through columns, excluding the class name
		for (int col = 1; col < data.get(0).size(); col++) {
			
			// First, get mean and stddev
			// These require the current colummn to function
			List<BigDecimal> column = new ArrayList<BigDecimal>();
			for (int row = 0; row < data.size(); row++)
				column.add(data.get(row).get(col));
			
			BigDecimal mean   = Util.getMean(column);
			BigDecimal stdDev = Util.getStdDev(column);
			
			
			
			// Now, iterate through rows again,
			// setting each element to its normalized value
			for (int row = 0; row < data.size(); row++) {
				
				BigDecimal normalizedValue = data.get(row).get(col);
				normalizedValue = normalizedValue.subtract(mean);
				normalizedValue = normalizedValue.divide(stdDev, RoundingMode.HALF_UP);
				
				data.get(row).set(col, normalizedValue);
			}
		}
	}
	
	public static List<List<BigDecimal>> parseDataList(Scanner fileScanner) {
		
		System.out.print("Fetching data... ");
		
		List<List<BigDecimal>> data = new ArrayList<List<BigDecimal>>();
		while (fileScanner.hasNextLine()) {
			
			// First, read this line
			Scanner sc = new Scanner(fileScanner.nextLine());

			List<BigDecimal> decimals = new ArrayList<BigDecimal>();
			
			while (sc.hasNextBigDecimal())
				decimals.add(sc.nextBigDecimal());
			sc.close();
			
			
			
			data.add(decimals);
		}
		fileScanner.close();
		
		System.out.println("done!");
		
		return data;
	}

	public static Integer getNumberOfFeatures(List<List<BigDecimal>> data) {
		return data.get(0).size() - 1;
	}

	public static Integer getNumberOfInstances(List<List<BigDecimal>> data) {
		return data.size();
	}

	public static void printBeginStats(List<List<BigDecimal>> data) {
		
		System.out.println("The dataset has " + getNumberOfFeatures(data).toString() + " features,");
		System.out.println("with " + getNumberOfInstances(data).toString() + " instances.");
	}

}
