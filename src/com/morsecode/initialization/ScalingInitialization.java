package com.morsecode.initialization;

public class ScalingInitialization implements InitializationFunction {

	public double[] init(int inputSize) {
		double[] weights = new double[inputSize];
		// Scale 0.2
		double stdDev = Math.sqrt(.2 / inputSize);
		for(int i = 0; i < inputSize; i++) {
			weights[i] = randGaussian() * stdDev;
		}
		return weights;
	}

	private static double randGaussian() {
		return Math.random() * 2 - 1;
	}

}
