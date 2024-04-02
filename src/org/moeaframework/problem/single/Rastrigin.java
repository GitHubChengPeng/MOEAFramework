/* Copyright 2009-2024 David Hadka
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
package org.moeaframework.problem.single;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

/**
 * The single-objective Rastrigin problem with an optimum at {@code x = (0, ..., 0)} with {@code f(x) = 0}.
 * <p>
 * References:
 * <ol>
 *   <li>Rastrigin, L. A. "Systems of Extremal Control." Mir, Moscow (1974).
 * </ol>
 */
public class Rastrigin extends AbstractProblem {
	
	/**
	 * Constructs a new instance of the Rastrigin problem with two decision variables.
	 */
	public Rastrigin() {
		this(2);
	}
	
	/**
	 * Constructs a new instance of the Rastrigin problem.
	 * 
	 * @param numberOfVariables the number of decision variables
	 */
	public Rastrigin(int numberOfVariables) {
		super(numberOfVariables, 1);
	}

	@Override
	public void evaluate(Solution solution) {
		double[] x = EncodingUtils.getReal(solution);
		final double A = 10.0;
		double sum = 0.0;
		
		for (int i = 0; i < numberOfVariables; i++) {
			sum += Math.pow(x[i], 2.0) - A * Math.cos(2.0 * Math.PI * x[i]);
		}
		
		solution.setObjective(0, A * numberOfVariables + sum);
	}

	@Override
	public Solution newSolution() {
		Solution solution = new Solution(numberOfVariables, 1);
		
		for (int i = 0; i < numberOfVariables; i++) {
			solution.setVariable(i, EncodingUtils.newReal(-5.12, 5.12));
		}

		return solution;
	}

}