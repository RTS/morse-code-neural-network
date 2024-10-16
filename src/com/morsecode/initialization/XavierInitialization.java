package com.morsecode.initialization;

public class XavierInitialization implements InitializationFunction {

	public double[] init(int inputSize) {
		double[] weights = new double[inputSize];
		double limit = Math.sqrt(6.0 / (inputSize + 1));
		for(int i = 0; i < inputSize; i++) {
			weights[i] = (Math.random() * 2 * limit) - limit;
		}
		return weights;
	}
}
