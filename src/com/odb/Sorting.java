package com.odb;

/**
 * Ranking of Web Pages is done using merge sort.
 * Collections.sort by default uses merge sort.
*/

import java.util.*;

public class Sorting {

	public static void pageSort(Hashtable<Result, Integer> t, int occur) {
		// Transfer as List and sort it
		ArrayList<Map.Entry<Result, Integer>> l = new ArrayList<>(t.entrySet());
		Collections.sort(l, new Comparator<Map.Entry<?, Integer>>() {

			public int compare(Map.Entry<?, Integer> o1, Map.Entry<?, Integer> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});

		Collections.reverse(l);

		if (occur != 0) {
			System.out.println("\n-----------------Web Page Ranking----------------\n");

			int j = 0, val;
			Result key;
			System.out.println("-------------------------------------------------");
			while (l.size() > j) {
				key = l.get(j).getKey();
				val = l.get(j).getValue();

				if (val > 0) {
					System.out.println(j + 1);
					System.out.println(key);
					System.out.println("Keyword occurence: " + val);
					System.out.println();
				}
				j++;
			}
			System.out.println("\n-------------------------------------------------\n");
		}
	}

}
