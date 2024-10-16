package com.morsecode.activation;

public class SwishActivation implements ActivationFunction {

	@Override
	public double activate(double x) {
		return x * (1.0 / (1.0 + Math.exp(-x)));
	}

	@Override
	public double derivative(double x) {
		double sigmoid = 1.0 / (1.0 + Math.exp(-x));
		return sigmoid + x * sigmoid * (1 - sigmoid);
	}
}
