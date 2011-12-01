package org.moeaframework.analysis.sensitivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.analysis.MultivariateRealFunction;
import org.junit.Assert;
import org.junit.Test;
import org.moeaframework.TestThresholds;
import org.moeaframework.TestUtils;
import org.moeaframework.util.sequence.Saltelli;

/**
 * Tests the {@link SobolAnalysis} class.
 */
public class SobolAnalysisTest {
	
	@Test
	public void testNoInteraction1() throws FunctionEvaluationException, 
	IOException {
		File outputFile = test(new MultivariateRealFunction() {

			@Override
			public double value(double[] variables)
					throws FunctionEvaluationException {
				return variables[0];
			}
			
		});
		
		assertEntryEquals(outputFile, "Variable1", 0, 1.0);
		assertEntryEquals(outputFile, "Variable2", 0, 0.0);
		assertEntryEquals(outputFile, "Variable3", 0, 0.0);
		
		assertEntryEquals(outputFile, "Variable1", 1, 1.0);
		assertEntryEquals(outputFile, "Variable2", 1, 0.0);
		assertEntryEquals(outputFile, "Variable3", 1, 0.0);
		
		assertEntryEquals(outputFile, "Variable1 \\* Variable2", 0, 0.0);
		assertEntryEquals(outputFile, "Variable1 \\* Variable3", 0, 0.0);
		assertEntryEquals(outputFile, "Variable2 \\* Variable3", 0, 0.0);
	}
	
	@Test
	public void testNoInteraction12() throws FunctionEvaluationException, 
	IOException {
		File outputFile = test(new MultivariateRealFunction() {

			@Override
			public double value(double[] variables)
					throws FunctionEvaluationException {
				return variables[0] + variables[1];
			}
			
		});
		
		assertEntryEquals(outputFile, "Variable1", 0, 0.5);
		assertEntryEquals(outputFile, "Variable2", 0, 0.5);
		assertEntryEquals(outputFile, "Variable3", 0, 0.0);
		
		assertEntryEquals(outputFile, "Variable1", 1, 0.5);
		assertEntryEquals(outputFile, "Variable2", 1, 0.5);
		assertEntryEquals(outputFile, "Variable3", 1, 0.0);
		
		assertEntryEquals(outputFile, "Variable1 \\* Variable2", 0, 0.0);
		assertEntryEquals(outputFile, "Variable1 \\* Variable3", 0, 0.0);
		assertEntryEquals(outputFile, "Variable2 \\* Variable3", 0, 0.0);
	}
	
	@Test
	public void testNoInteraction123() throws FunctionEvaluationException, 
	IOException {
		File outputFile = test(new MultivariateRealFunction() {

			@Override
			public double value(double[] variables)
					throws FunctionEvaluationException {
				return variables[0] + variables[1] + variables[2];
			}
			
		});
		
		assertEntryEquals(outputFile, "Variable1", 0, 0.333);
		assertEntryEquals(outputFile, "Variable2", 0, 0.333);
		assertEntryEquals(outputFile, "Variable3", 0, 0.333);
		
		assertEntryEquals(outputFile, "Variable1", 1, 0.333);
		assertEntryEquals(outputFile, "Variable2", 1, 0.333);
		assertEntryEquals(outputFile, "Variable3", 1, 0.333);
		
		assertEntryEquals(outputFile, "Variable1 \\* Variable2", 0, 0.0);
		assertEntryEquals(outputFile, "Variable1 \\* Variable3", 0, 0.0);
		assertEntryEquals(outputFile, "Variable2 \\* Variable3", 0, 0.0);
	}
	
	@Test
	public void testInteraction12() throws FunctionEvaluationException, 
	IOException {
		File outputFile = test(new MultivariateRealFunction() {

			@Override
			public double value(double[] variables)
					throws FunctionEvaluationException {
				return variables[0]*variables[1] + variables[2];
			}
			
		});
		
		assertEntryNotEquals(outputFile, "Variable1", 0, 0.0);
		assertEntryEquals(outputFile, "Variable1", 0, 
				getEntryValue(outputFile, "Variable2", 0));
		assertEntryNotEquals(outputFile, "Variable3", 0, 0.0);
		
		assertEntryEquals(outputFile, "Variable1", 1, 
				getEntryValue(outputFile, "Variable1", 0) + 
				getEntryValue(outputFile, "Variable1 \\* Variable2", 0));
		assertEntryEquals(outputFile, "Variable2", 1, 
				getEntryValue(outputFile, "Variable2", 0) + 
				getEntryValue(outputFile, "Variable1 \\* Variable2", 0));
		assertEntryEquals(outputFile, "Variable3", 1, 
				getEntryValue(outputFile, "Variable3", 0));
		
		assertEntryNotEquals(outputFile, "Variable1 \\* Variable2", 0, 0.0);
		assertEntryEquals(outputFile, "Variable1 \\* Variable3", 0, 0.0);
		assertEntryEquals(outputFile, "Variable2 \\* Variable3", 0, 0.0);
	}
	
	@Test
	public void testInteraction123() throws FunctionEvaluationException, 
	IOException {
		File outputFile = test(new MultivariateRealFunction() {

			@Override
			public double value(double[] variables)
					throws FunctionEvaluationException {
				return variables[0]*variables[1]*variables[2];
			}
			
		});
		
		assertEntryNotEquals(outputFile, "Variable1", 0, 0.0);
		assertEntryEquals(outputFile, "Variable1", 0, 
				getEntryValue(outputFile, "Variable2", 0));
		assertEntryEquals(outputFile, "Variable1", 0, 
				getEntryValue(outputFile, "Variable3", 0));
		
		assertEntryEquals(outputFile, "Variable1", 1, 
				getEntryValue(outputFile, "Variable1", 0) + 
				getEntryValue(outputFile, "Variable1 \\* Variable2", 0) + 
				getEntryValue(outputFile, "Variable1 \\* Variable3", 0));
		assertEntryEquals(outputFile, "Variable2", 1, 
				getEntryValue(outputFile, "Variable2", 0) + 
				getEntryValue(outputFile, "Variable1 \\* Variable2", 0) + 
				getEntryValue(outputFile, "Variable2 \\* Variable3", 0));
		assertEntryEquals(outputFile, "Variable3", 1, 
				getEntryValue(outputFile, "Variable3", 0) + 
				getEntryValue(outputFile, "Variable1 \\* Variable3", 0) + 
				getEntryValue(outputFile, "Variable2 \\* Variable3", 0));
		
		assertEntryNotEquals(outputFile, "Variable1 \\* Variable2", 0, 0.0);
		assertEntryEquals(outputFile, "Variable1 \\* Variable2", 0, 
				getEntryValue(outputFile, "Variable1 \\* Variable3", 0));
		assertEntryEquals(outputFile, "Variable1 \\* Variable2", 0, 
				getEntryValue(outputFile, "Variable2 \\* Variable3", 0));
	}
	
	/**
	 * Runs Sobol analysis on the given function.
	 * 
	 * @param function the function to evaluate
	 * @return the file containing the output from Sobol analysis
	 * @throws FunctionEvaluationException if an error occurred when evaluating
	 *         the function
	 * @throws IOException if an I/O error occurred
	 */
	protected File test(MultivariateRealFunction function) throws 
	FunctionEvaluationException, IOException {
		double[][] input = new Saltelli().generate(1000*8, 3);
		double[] output = evaluate(function, input);
		
		File outputFile = TestUtils.createTempFile();
		File parameterFile = TestUtils.createTempFile();
		File inputFile = TestUtils.createTempFile();
		
		createParameterFile(parameterFile, 3);
		save(inputFile, output);
		
		SobolAnalysis.main(new String[] {
			"--parameterFile", parameterFile.getPath(),
			"--input", inputFile.getPath(),
			"--metric", "0",
			"--output", outputFile.getPath()
		});
		
		return outputFile;
	}
	
	/**
	 * Asserts that an entry in the Sobol results is equals an expected value.
	 * 
	 * @param file the file containing the output from Sobol analysis
	 * @param key the regular expression for identifying the desired entry in
	 *        the output file
	 * @param skip set to {@code 0} for first-order or second-order effects,
	 *        {@code 1} for total-order effects
	 * @param expected the expected sensitivity value
	 * @throws IOException if an I/O error occurred
	 */
	protected void assertEntryEquals(File file, String key, int skip,
			double expected) throws IOException {
		Assert.assertEquals(expected, getEntryValue(file, key, skip), 
				TestThresholds.STATISTICS_EPS);
	}
	
	/**
	 * Asserts that an entry in the Sobol results is not equal to an expected
	 * value.
	 * 
	 * @param file the file containing the output from Sobol analysis
	 * @param key the regular expression for identifying the desired entry in
	 *        the output file
	 * @param skip set to {@code 0} for first-order or second-order effects,
	 *        {@code 1} for total-order effects
	 * @param expected the expected sensitivity value
	 * @throws IOException if an I/O error occurred
	 */
	protected void assertEntryNotEquals(File file, String key, int skip,
			double expected) throws IOException {
		Assert.assertTrue(Math.abs(expected - getEntryValue(file, key, skip)) > 
				TestThresholds.STATISTICS_EPS);
	}
	
	/**
	 * Parses the specified Sobol output file, returning the sensitivity for
	 * the given key.
	 * 
	 * @param file the file containing the output from Sobol analysis
	 * @param key the regular expression for identifying the desired entry in 
	 *        the output file
	 * @param skip set to {@code 0} for first-order or second-order effects,
	 *        {@code 1} for total-order effects
	 * @return the sensitivity for the given key
	 * @throws IOException if an I/O error occurred
	 */
	protected double getEntryValue(File file, String key, int skip) throws 
	IOException {
		BufferedReader reader = null;
		String line = null;
		Pattern pattern = Pattern.compile("^\\s*" + key + "\\s+" + 
				TestUtils.getNumericPattern(1) + "\\s+\\[" + 
				TestUtils.getNumericPattern(1) + "\\]\\s*$");
		
		try {
			reader = new BufferedReader(new FileReader(file));
			
			while ((line = reader.readLine()) != null) {
				Matcher matcher = pattern.matcher(line);
				
				if (matcher.matches()) {
					if (skip-- == 0) {
						return Double.parseDouble(matcher.group(1));
					}
				}
			}
			
			return Double.POSITIVE_INFINITY;
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}
	
	/**
	 * Evaluates the given function on each input entry, returning the array of
	 * outputs.
	 * 
	 * @param function the function
	 * @param input the array of inputs, where the outer index references each
	 *        set of input to the function
	 * @return the array of outputs
	 * @throws FunctionEvaluationException if an error occurred when evaluating
	 *         the function
	 */
	protected double[] evaluate(MultivariateRealFunction function, 
			double[][] input) throws FunctionEvaluationException { 
		double[] output = new double[input.length];
		
		for (int i=0; i<input.length; i++) {
			output[i] = function.value(input[i]);
		}
		
		return output;
	}
	
	/**
	 * Saves the test data to the input file compatible with the input to the
	 * Sobol analysis utility.
	 * 
	 * @param file the file to create
	 * @param data the array of model outputs
	 * @throws IOException if an I/O error occurred
	 */
	protected void save(File file, double[] data) throws IOException {
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter(file);
			
			for (int i=0; i<data.length; i++) {
				writer.print(data[i]);
				
				//pad with 0's to look like metric file
				for (int j=1; j<MetricFileWriter.NUMBER_OF_METRICS; j++) {
					writer.print(" 0.0");
				}
				
				writer.println();
			}
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
	
	/**
	 * Creates the parameter file with entries of the form
	 * <pre>
	 *   VariableN 0.0 1.0
	 * </pre>
	 * 
	 * @param file the parameter file to create
	 * @param dimension the number of variables
	 * @throws IOException if an I/O error occurred
	 */
	protected void createParameterFile(File file, int dimension) throws 
	IOException {
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter(file);
			
			for (int i=0; i<dimension; i++) {
				writer.print("Variable");
				writer.print(i+1);
				writer.print(' ');
				writer.print(0.0);
				writer.print(' ');
				writer.println(1.0);
			}
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

}
