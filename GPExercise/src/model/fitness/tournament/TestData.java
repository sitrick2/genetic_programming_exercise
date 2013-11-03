package model.fitness.tournament;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class TestData {

	public static HashMap<Integer, Double> getTestValues(File f)
			throws FileNotFoundException {
		Scanner s = new Scanner(f);
		HashMap<Integer, Double> solution = new HashMap<Integer, Double>();

		while (s.hasNextLine()) {
			String line = s.nextLine();
			String[] values = line.split("\t");

			solution.put(Integer.parseInt(values[0]),
					Double.parseDouble(values[1]));

		}

		s.close();

		return solution;
	}
}
