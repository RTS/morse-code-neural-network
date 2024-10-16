package com.morsecode.decay;

public class ExponentialDecay implements DecayFunction {
	private double initialLearningRate;
	private double decayRate;

	public ExponentialDecay(double initialLearningRate, double decayRate) {
		this.initialLearningRate = initialLearningRate;
		this.decayRate = decayRate;
	}

	@Override
	public double getLearningRate(int epoch) {
		return initialLearningRate * Math.exp(-decayRate * epoch);
	}
}

