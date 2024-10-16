package com.morsecode.network;

import com.morsecode.activation.ActivationFunction;

public class Neuron {

	private double[] weights;
	private double bias;
	private double output;
	private double delta;
	private ActivationFunction activationFunction;

	public double[] getWeights() {
		return weights;
	}

	public void setWeights(double[] weights) {
		this.weights = weights;
	}

	public double getBias() {
		return bias;
	}

	public void setBias(double bias) {
		this.bias = bias;
	}

	public double getOutput() {
		return output;
	}

	public void setOutput(double output) {
		this.output = output;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	public ActivationFunction getActivationFunction() {
		return activationFunction;
	}

	public void setActivationFunction(ActivationFunction activationFunction) {
		this.activationFunction = activationFunction;
	}

	public Neuron(double[] weights, double bias, ActivationFunction activationFunction) {
		this.weights = weights;
		this.bias = bias;
		this.activationFunction = activationFunction;
	}

	public double activate(double[] inputs) {
		double sum = bias;
		for(int i = 0; i < weights.length; i++) {
			sum += weights[i] * inputs[i];
		}
		output = activationFunction.activate(sum);
		return output;
	}

}
