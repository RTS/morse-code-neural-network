package com.morsecode.initialization;

public class LeCunInitialization implements InitializationFunction {

	public double[] init(int inputSize) {
		double[] weights = new double[inputSize];
		double stdDev = Math.sqrt(1.0 / inputSize);
		for(int i = 0; i < inputSize; i++) {
			weights[i] = randGaussian() * stdDev;
		}
		return weights;
	}

	private static double randGaussian() {
		return Math.random() * 2 - 1;
	}

}
