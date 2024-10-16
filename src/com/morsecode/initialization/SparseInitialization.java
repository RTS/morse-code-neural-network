package com.morsecode.initialization;

public class SparseInitialization implements InitializationFunction {

	public double[] init(int inputSize) {
		double[] weights = new double[inputSize];
		for(int i = 0; i < inputSize; i++) {

			// sparsityLevel 0.5
			if (Math.random() < .5) {
				weights[i] = Math.random() * 2 - 1; // Initialize non-zero weight
			} else {
				weights[i] = 0.0; // Initialize to zero
			}
		}
		return weights;
	}

}
