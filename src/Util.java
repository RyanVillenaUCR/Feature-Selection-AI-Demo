import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class Util {

	public static BigDecimal mapToRange(BigDecimal oldLo, BigDecimal oldHi,
			BigDecimal newLo, BigDecimal newHi,
			BigDecimal x) {
		
		// x -= oldLo; bring lo down to 0
		x = x.subtract(oldLo);
		
		// x /= (oldHi - oldLo); map to range [0, 1]
		x = x.divide(oldHi.subtract(oldLo), RoundingMode.HALF_UP);
		
		// x *= (newHi - newLo); map to range [0, newHi]
		x = x.multiply(newHi.subtract(newLo));
		
		// x += newLo; bring lo up to newLo
		x = x.add(newLo);
		
		return x;
	}

	public static String dataToString(Map<Integer, Set<List<BigDecimal>>> data) {
		
		StringBuilder sb = new StringBuilder();
		
		for (Entry<Integer, Set<List<BigDecimal>>> thisEntry : data.entrySet()) {
			
			Integer key = thisEntry.getKey();
			sb.append("Training data for class " + key + ":\n");
			
			Set<List<BigDecimal>> instances = data.get(key);
			
			for (List<BigDecimal> thisInstance : instances)
				sb.append(thisInstance.toString() + "\n");
			
			sb.append('\n');
		}
		
		return sb.toString();
	}

	public static String dataToString(List<List<BigDecimal>> data) {
		
		StringBuilder sb = new StringBuilder();
		
		for (List<BigDecimal> thisRow : data) {
			
			sb.append(thisRow.toString());
			sb.append('\n');
		}
		
		return sb.toString();
	}
	
	public static BigDecimal getMean(List<BigDecimal> numbers) {
		
		BigDecimal mean = BigDecimal.ZERO;
		
		for (BigDecimal thisDecimal : numbers)
			mean = mean.add(thisDecimal);
		
		mean = mean.divide(new BigDecimal(numbers.size()), RoundingMode.HALF_UP);
		
		return mean;
	}
	
	public static BigDecimal getStdDev(List<BigDecimal> numbers) {
		
		final int PRECISION = 7; // As per the input file
		
		BigDecimal stdDev = BigDecimal.ZERO;
		BigDecimal mean = getMean(numbers);
		
		for (BigDecimal thisDecimal : numbers) {
			
			BigDecimal temp = thisDecimal.subtract(mean);
			temp = temp.pow(2);
			
			stdDev = stdDev.add(temp);
		}
		
		stdDev = stdDev.divide(new BigDecimal(numbers.size()), RoundingMode.HALF_UP);
		stdDev = stdDev.sqrt(new MathContext(PRECISION, RoundingMode.HALF_UP));
		
		return stdDev;
	}

	public static BigDecimal getDistance(List<BigDecimal> pointA, List<BigDecimal> pointB) {
		
		assert(pointA.size() == pointB.size());
		
		BigDecimal dist = BigDecimal.ZERO;
		
		for (int i = 0; i < pointA.size(); i++) {
			
			BigDecimal temp = pointA.get(i).subtract(pointB.get(i));
			temp = temp.pow(2);
			
			dist = dist.add(temp);
		}
		
		final int PRECISION = 7;
		return dist.sqrt(new MathContext(PRECISION, RoundingMode.HALF_UP));
	}

	public static Entry<Integer, Integer> getMode(List<List<BigDecimal>> data) {
		
		// Maps a class name to its frequency
		Map<Integer, Integer> frequencies = new HashMap<Integer, Integer>();
		
		// First, populate map of frequencies
		for (List<BigDecimal> thisInstance : data) {
			
			Integer className = thisInstance.get(0).intValue();
			
			if (!frequencies.containsKey(className)) {
				
				frequencies.put(className, 1);
			}
			
			else {
				
				frequencies.put(className, frequencies.get(className) + 1);
//				System.out.println("Just put { " + className + ", " + (frequencies.get(className) + 1) + " }");
			}
		}
		
		Entry<Integer, Integer> mostFrequentEntry = null;
		Integer mode = 0;
		
		// Then, select the largest frequency and return that class
		for (Entry<Integer, Integer> thisEntry : frequencies.entrySet()) {
			
			if (mostFrequentEntry == null)
				mostFrequentEntry = thisEntry;	// just initialize to be safe
			
			// Save if this is the most frequent entry
			if (thisEntry.getValue() > mode) {
				
				mostFrequentEntry = thisEntry;
				mode = thisEntry.getValue();
			}
		}
		
		assert(mode == mostFrequentEntry.getValue());
		return mostFrequentEntry;
	}
}
