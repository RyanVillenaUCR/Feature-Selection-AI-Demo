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
	
	private static void testNormalizeLists() {
		
		String filepath = "sample_data/cs_170_small80.txt";
		Scanner file_sc;
		try {
			file_sc = new Scanner(new File(filepath));
		} catch (FileNotFoundException e) {
			System.err.println("File " + filepath + " not found.");
			e.printStackTrace();
			return;
		}
		List<List<BigDecimal>> data = DataGetter.parseDataList(file_sc);
		file_sc.close();
		
		System.out.println("Before normalization:\n" + Util.dataToString(data));
		DataGetter.normalize(data);
		System.out.println("After normalization:\n" + Util.dataToString(data));
	}
	
	private static void testNormalize() {
		
		String filepath = "sample_data/cs_170_small80.txt";
		
		Map<Integer, Set<List<BigDecimal>>> data = DataGetter.parseData(filepath);
		
		DataGetter.normalize(data);
		
		System.out.println("Normalized data:");
		System.out.println(Util.dataToString(data));
	}
	
	private static void testMapToRange() {
		
		BigDecimal x = new BigDecimal(4.75);
		BigDecimal oldLo = new BigDecimal(4);
		BigDecimal oldHi = new BigDecimal(8);
		BigDecimal newLo = new BigDecimal(-1);
		BigDecimal newHi = new BigDecimal(-9);
		
		System.out.println("Mapping " + x + " from [ " + oldLo + ", " + oldHi + " ]");
		System.out.println("to [ " + newLo + ", " + newHi + " ]: ");
		
		x = Util.mapToRange(oldLo, oldHi, newLo, newHi, x);
		
		System.out.println("Now, x = " + x);
	}
	
	private static void testFindMinMax() {
		
		String filepath = "sample_data/cs_170_small80.txt";
		
		Map<Integer, Set<List<BigDecimal>>> data = DataGetter.parseData(filepath);
		BigDecimal[] minMax = DataGetter.getMinMax(data);
		
		System.out.println("Minumum of data: " + minMax[0]);
		System.out.println("Maximum of data: " + minMax[1]);
	}
	
	private static void testIEEEFileInputToMap() {
		
		String filepath = "sample_data/cs_170_small80.txt";
//		String filepath = "sample_data/cs_170_large80.txt";
		
		Map<Integer, Set<List<BigDecimal>>> data = DataGetter.parseData(filepath);
		System.out.println("Got input from file " + filepath + ":");
		System.out.println(Util.dataToString(data));
	}

	private static void testIEEEFileInputToLists() {
		
		String filepath = "sample_data/cs_170_small80.txt";
		Scanner file_sc;
		try {
			file_sc = new Scanner(new File(filepath));
		} catch (FileNotFoundException e) {
			System.err.println("File " + filepath + " not found.");
			e.printStackTrace();
			return;
		}
		List<List<BigDecimal>> data = DataGetter.parseDataList(file_sc);
		file_sc.close();
		
		System.out.println("Got input from file " + filepath + ":");
		for (List<BigDecimal> thisRow : data)	
			System.out.println(thisRow);
	}
	
	
	public static void runTests() {
		
//		testIEEEFileInputToLists();
		
//		testIEEEFileInputToMap();
		
//		testFindMinMax();
		
//		testMapToRange();
		
//		testNormalize();
		
		testNormalizeLists();
	}
}
