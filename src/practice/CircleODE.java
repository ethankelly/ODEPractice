package practice;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.DormandPrince853Integrator;

import java.util.Arrays;

public record CircleODE(double[] c, double omega) implements FirstOrderDifferentialEquations {

	public static void main(String[] args) {
		FirstOrderIntegrator integrator = new DormandPrince853Integrator(1.0e-8, 100.0,
				1.0e-10, 1.0e-10);
		FirstOrderDifferentialEquations ode = new CircleODE(new double[] {1.0, 1.0}, 0.1);

		double[] y = new double[] {0.0, 1.0}; // The initial state
		System.out.println("Initial state: " + Arrays.toString(y));
		integrator.integrate(ode, 0.0, y, 16.0, y); // Now y contains the final state at time t=16
		System.out.println("Final state at t = 16:" + Arrays.toString(y));
	}

	@Override
	public int getDimension() {
		return 2;
	}

	@Override
	public void computeDerivatives(double t, double[] y, double[] yDot) throws MaxCountExceededException, DimensionMismatchException {
		yDot[0] = omega * (c[1] - y[1]);
		yDot[1] = omega * (y[0] - c[0]);
	}

	@Override
	public double[] c() {
		return c;
	}

	@Override
	public double omega() {
		return omega;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CircleODE circleODE = (CircleODE) o;

		if (Double.compare(circleODE.omega, omega) != 0) return false;
		return Arrays.equals(c, circleODE.c);
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = Arrays.hashCode(c);
		temp = Double.doubleToLongBits(omega);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public String toString() {
		return "CircleODE{" +
		       "c=" + Arrays.toString(c) +
		       ", omega=" + omega +
		       '}';
	}
}