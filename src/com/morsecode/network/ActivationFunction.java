package com.morsecode.network;

public class ActivationFunction {
	public static double sigmoid(double x) {
		return 1 / (1 + Math.exp(-x));
	}

	public static double sigmoidDerivative(double x) {
		return x * (1 - x);
	}
}
