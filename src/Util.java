import java.math.BigDecimal;
import java.math.RoundingMode;
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
			sb.append(key + ":\n");
			
			Set<List<BigDecimal>> instances = data.get(key);
			
			for (List<BigDecimal> thisInstance : instances)
				sb.append(thisInstance.toString() + "\n");
		}
		
		return sb.toString();
	}
}
