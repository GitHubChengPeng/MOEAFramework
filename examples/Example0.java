/* Copyright 2009-2022 David Hadka
 *
 * This file is part of the MOEA Framework.
 *
 * The MOEA Framework is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * The MOEA Framework is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the MOEA Framework.  If not, see <http://www.gnu.org/licenses/>.
 */
import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.core.Problem;
import org.moeaframework.problem.DTLZ.DTLZ2;

/**
 * Demonstrates the new way of creating and running algorithms, starting with version 3.2.
 * The algorithm is automatically populated with default settings.
 */
public class Example0 {

	public static void main(String[] args) {
		// create the problem
		Problem problem = new DTLZ2(2);
		
		// create and run the algorithm
		NSGAII algorithm = new NSGAII(problem);
		algorithm.run(10000);
		
		// display the results
		algorithm.getResult().display();
	}

}
