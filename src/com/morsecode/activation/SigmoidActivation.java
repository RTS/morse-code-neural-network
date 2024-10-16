package com.morsecode.activation;

public class SigmoidActivation implements ActivationFunction {

	@Override
	public double activate(double x) {
		return 1 / (1 + Math.exp(-x));
	}

	@Override
	public double derivative(double x) {
		return x * (1 - x);
	}
}
