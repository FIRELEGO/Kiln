public class Stage {
	private double endTemp;
	private double endTime;
	
	public Stage(double endTemp, double time) {
		this.endTemp = endTemp >= 1900 ? -1 : endTemp;
		this.endTime = time;
	}
	
	@Override
	public String toString() {
		return endTemp + "," + endTime;
	}
	
	public double getEndTemp() {
		return this.endTemp;
	}
	
	public double getTime() {
		return this.endTime;
	}
}
