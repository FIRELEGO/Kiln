import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JOptionPane;

public class Manager {
    private static final int MAX_TEMP = 1900;
    private static final int TICK_TIME = 500;
    private static FirePlan plan;
    private static Thermocouple therm = new Thermocouple();
    private static Element element = new Element();
    private static long startStageTime;
    private static double stageLength;
    private static double startStageTemp = 0;
    private static double endStageTemp = 0;
    private static double stageRate = 0;
    private static double targetTemp = 0;
    // DEBUG
    private static boolean on = false;

    public static void main(String[] args) {
        //DEBUG
        plan = new FirePlan("40,.5!550,.02!600,.02!800,.02!900,.02!");

        for(int i = 0; i < plan.getNumOfStages(); i++) {
            endStageTemp = plan.getStage(i).getEndTemp();
            // Converts stage time from minutes to milliseconds
            stageLength = plan.getStage(i).getTime() * 60000;
            startStageTemp = getTemp();

            // stageRate = (difference in temperature) / (# of TICK_TIME intervals in stageLength)
            stageRate = (endStageTemp - startStageTemp) / (stageLength / TICK_TIME);
            startStageTime = getCurTime();

            while(getTemp() < endStageTemp - 1 || getTemp() > endStageTemp + 1) {
                if(targetTemp < endStageTemp - stageRate || targetTemp > endStageTemp + stageRate) {
                    targetTemp = stageRate * ((getCurTime() - startStageTime) / TICK_TIME) + startStageTemp;
                } else {
                    targetTemp = endStageTemp;
                }


                if (getTemp() < targetTemp && targetTemp != -1) {
                    element.turnOn();
                    on = true;
                } else {
                    element.turnOff();
                    on = false;
                }

                // DEBUG
                System.out.println("Target: " + (int)targetTemp + "\tCurrent: " + (int)getTemp() + "\tElement: " + (on ? "on" : "  off" ) + "\tStage: " + space(i) + i);

                try {
                    Thread.sleep(TICK_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static double getTemp() {
        double testTemp = therm.getTemp();
        
        if (testTemp >= MAX_TEMP) {
            element.turnOff();
            on = false;
            System.err.println("Temp reached 1900 degrees. Firing stopped.");
        }
        
        return testTemp;
        //return Double.parseDouble(JOptionPane.showInputDialog(null,"Current terget temp: " + endStageTemp + "\nTemp: "));
    }

    public static long getCurTime() {
        return System.currentTimeMillis();
    }

    //DEBUG 
    public static String space(int num) {
        String ret = "";
        
        for(int i = 0; i < num; i++) {
            ret += " ";
        }
        
        return ret;
    }
}
