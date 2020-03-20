import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Node {

	public Node(Set<Integer> features, List<List<BigDecimal>> data) {
		
		this.features = new HashSet<Integer>(features);	// Make our own copy of features
		this.data = data;	// Don't keep copying data though
	}
	
	/**
	 * Compares this node's Euclidean Distance with all data points and returns the class of the closest data point.
	 * @param instance The data instance to be calculated
	 * @return The class (1 or 2) of the closest instance in data
	 */
	private Integer nearestNeighbor(List<BigDecimal> instance) {
		
		BigDecimal lowestDist = Util.getDistance(instance, data.get(0));
		List<BigDecimal> bestInstance = data.get(0);
		
		// Iterate through all data points
		for (List<BigDecimal> dataInstance : data) {
			
			// Take only the features necessary from each instance
			List<BigDecimal> instanceFeatures = new ArrayList<BigDecimal>();
			List<BigDecimal> dataInstanceFeatures = new ArrayList<BigDecimal>();
			for (Integer feature : features) {
				instanceFeatures.add(instance.get(feature));
				dataInstanceFeatures.add(dataInstance.get(feature));
			}
			
			// Find the distance using only the features in this node
			BigDecimal thisDistance = Util.getDistance(instanceFeatures, dataInstanceFeatures);
			
			// If we've found a new closest node, keep track of it
			if (thisDistance.compareTo(lowestDist) < 0
					&& !instance.equals(dataInstance)) {
				
				lowestDist = thisDistance;
				bestInstance = dataInstance;
			}
		}
		
		return bestInstance.get(0).intValue();
	}
	
	@Override
	public String toString() {
		
		return "Node with features " + features.toString();
	}
	
	@Override
	public boolean equals(Object other) {
		
		if (!(other instanceof Node)) return false;
		
		Node otherNode = (Node) other;
		return features.equals(otherNode.features);
	}
	
	@Override
	public int hashCode() {
		return features.hashCode();
	}
	
	private Set<Integer> features;
	private List<List<BigDecimal>> data;
}
