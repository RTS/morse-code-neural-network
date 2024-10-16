package com.morsecode.activation;

public class TanhActivation implements ActivationFunction {

	@Override
	public double activate(double x) {
		return Math.tanh(x);
	}

	@Override
	public double derivative(double x) {
		double tanh = activate(x);
		return 1 - Math.tanh(x) * tanh;
	}
}

