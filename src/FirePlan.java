import java.util.ArrayList;

public class FirePlan {
	private ArrayList<Stage> plan = new ArrayList<Stage>();
	
	public FirePlan(String planCSV) {
		String[] stages = planCSV.split("!");
		
		for(String temp : stages) {
			String[] info = temp.split(",");
			plan.add(new Stage(Double.parseDouble(info[0]), Double.parseDouble(info[1])));
		}
	}

	public int getNumOfStages() {
		return plan.size();
	}
	
	public Stage getStage(int index) {
		return plan.get(index);
	}
	
	@Override
	public String toString() {
		String ret = "";
		for(Stage temp : plan) {
			ret += temp + "!";
		}
		
		return ret;
	}
}
