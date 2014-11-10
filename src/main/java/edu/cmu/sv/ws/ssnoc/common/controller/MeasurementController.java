package edu.cmu.sv.ws.ssnoc.common.controller;

import java.util.Timer;

import edu.cmu.sv.ws.ssnoc.common.timer.MeasurementTimer;
import edu.cmu.sv.ws.ssnoc.common.utils.PropertyUtils;

/**
 * This is a utility class to perform timing for memory and performance
 * measurements
 * 
 */
public class MeasurementController {

    public static Timer myTimer;
    public static MeasurementTimer myTimerTask;
    public static boolean isRunning = false;

    public static void startMeasuring() {
        // Start off timer for measuring memory
        if (!isRunning) {
            myTimer = new Timer();
            myTimerTask = new MeasurementTimer(myTimer);
            int firstSart = 1000; // it means after 1 second.
            int period = PropertyUtils.MEMORY_SAMPLING_FREQUENCY * 1000 * 60;
            myTimer.schedule(myTimerTask, firstSart, period);
            isRunning = true;
        }
    }

    public static void stopMeasuring() {
        // Stop timer and timer task
        if (isRunning) {
            myTimerTask.cancel();
            myTimer.cancel();
            isRunning = false;
        }
    }

    public static boolean isRunning() {
        return isRunning;
    }

}
