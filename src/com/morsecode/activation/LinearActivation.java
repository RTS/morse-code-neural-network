package com.morsecode.activation;

public class LinearActivation implements ActivationFunction {

	@Override
	public double activate(double x) {
		return x;
	}

	@Override
	public double derivative(double x) {
		return 1;
	}
}
