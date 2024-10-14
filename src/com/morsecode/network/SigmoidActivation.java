package com.morsecode.network;

public class SigmoidActivation implements ActivationFunction {
	@Override
	public double activate(double x) {
		return 1 / (1 + Math.exp(-x));
	}

	@Override
	public double derivative(double x) {
		return x * (1 - x); // Note: x should be the output of the sigmoid function
	}
}
