package edu.cmu.sv.ws.ssnoc.common.timer;

import java.util.Timer;
import java.util.TimerTask;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.MeasurementUtils;
import edu.cmu.sv.ws.ssnoc.common.utils.PropertyUtils;


/**
 * This is a utility class to perform timing for memory and performance measurements 
 * 
 */
public class MeasurementTimer  extends TimerTask {

	Timer timer;

	public MeasurementTimer() {
	}

	public MeasurementTimer(Timer timer) {
		this.timer = timer;
	}

	public boolean shouldMeasure() {
		return PropertyUtils.MEASURE_MEMORY;
	}
		
	@Override
	public void run() {
		Log.enter();
		if(shouldMeasure())
		{
			MeasurementUtils.measure();
		}
	}
}
