import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Node implements Comparable {

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
					&& !instance.equals(dataInstance)) {	// Don't compare to yourself! That's cheating
				
				lowestDist = thisDistance;
				bestInstance = dataInstance;
			}
		}
		
		return bestInstance.get(0).intValue();
	}
	
	public BigDecimal evaluate() {
		
		// If no features, use the default rate
		if (features == null || features.isEmpty()) {
			
			Integer mode = Util.getMode(data).getValue();
			BigDecimal numerator   = new BigDecimal(mode);
			BigDecimal denominator = new BigDecimal(data.size());
			return numerator.divide(denominator, 2, RoundingMode.HALF_UP);
		}
		
		// If there are features, do leave-one-out validator
		else {
			BigDecimal correctGuesses = BigDecimal.ZERO;
			BigDecimal totalGuesses   = new BigDecimal(data.size());
			
			for (int i = 0; i < data.size(); i++) {
				
				Integer correctClass = data.get(i).get(0).intValue();
				Integer guessedClass = nearestNeighbor(data.get(i));
				
				if (correctClass.equals(guessedClass))
					correctGuesses = correctGuesses.add(BigDecimal.ONE);
			}
			
			return correctGuesses.divide(totalGuesses, new MathContext(7, RoundingMode.HALF_UP));
		}
	}
	
	public Set<Node> generateChildren() {
		
		int totalFeatures = DataGetter.getNumberOfFeatures(data);
		
		int childrenToGenerate = totalFeatures - features.size();
		if (childrenToGenerate <= 0) return null;	// For the bottom node
		
		Set<Node> children = new HashSet<Node>();
		
//		System.out.println("Current features is " + features + ",");
		
		for (int i = 1; i < totalFeatures; i++) {
			
			// If there's a feature we haven't included yet,
			// make a child of the current features and feature i
			if (!features.contains(i)) {
				
				Set<Integer> newFeatures = new HashSet<Integer>(features);
				newFeatures.add(i);
				
				children.add(new Node(newFeatures, data));
//				System.out.println("Generated child " + newFeatures);
			}
		}
		

		System.out.println("\n");
		return children;
	}
	
	public String generateStatus() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("Using feature" + (features.size() == 1 ? " " : "s ") + features + ", ");
		sb.append("accuracy is " + evaluate().toString());
		
		return sb.toString();
	}
	
	@Override
	public int compareTo(Object o) {

		if (!(o instanceof Node)) return -1;
		Node other = (Node) o;
		
		BigDecimal thisEval  = evaluate();
		BigDecimal otherEval = other.evaluate();
		
		return otherEval.compareTo(thisEval);
		
//		return (int) Math.signum(thisEval.subtract(otherEval).doubleValue());
	}
	
	@Override
	public String toString() {
		
		return features.toString();
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
