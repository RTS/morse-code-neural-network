package com.morsecode.decay;

public class StepDecay implements DecayFunction {
	private double initialLearningRate;
	private double decayFactor;
	private int dropEvery;

	public StepDecay(double initialLearningRate, double decayFactor, int dropEvery) {
		this.initialLearningRate = initialLearningRate;
		this.decayFactor = decayFactor;
		this.dropEvery = dropEvery;
	}

	@Override
	public double getLearningRate(int epoch) {
		return initialLearningRate * Math.pow(decayFactor, epoch / dropEvery);
	}
}

