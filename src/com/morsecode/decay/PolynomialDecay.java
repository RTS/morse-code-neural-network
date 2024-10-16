package com.morsecode.decay;

public class PolynomialDecay implements DecayFunction {
	private double initialLearningRate;
	private double endLearningRate;
	private int maxEpochs;
	private double power;

	public PolynomialDecay(double initialLearningRate, double endLearningRate, int maxEpochs, double power) {
		this.initialLearningRate = initialLearningRate;
		this.endLearningRate = endLearningRate;
		this.maxEpochs = maxEpochs;
		this.power = power;
	}

	@Override
	public double getLearningRate(int epoch) {
		return (initialLearningRate - endLearningRate) * Math.pow(1 - (double) epoch / maxEpochs, power) + endLearningRate;
	}
}

