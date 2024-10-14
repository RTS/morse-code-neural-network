package com.morsecode.network;

public class Neuron {
	public double[] weights;
	public double bias;
	public double output;
	public double delta;
	ActivationFunction activationFunction;

	public Neuron(int inputSize, ActivationFunction activationFunction) {
		this.activationFunction = activationFunction;
		weights = new double[inputSize];
		// Xavier initialization
		double limit = Math.sqrt(6.0 / (inputSize + 1));
		for (int i = 0; i < inputSize; i++) {
			weights[i] = (Math.random() * 2 * limit) - limit;
		}
		bias = 0.0;
	}

	public double activate(double[] inputs) {
		double sum = bias;
		for (int i = 0; i < weights.length; i++) {
			sum += weights[i] * inputs[i];
		}
		output = activationFunction.activate(sum);
		return output;
	}
}
