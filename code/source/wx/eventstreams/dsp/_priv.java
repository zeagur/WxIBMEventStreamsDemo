package wx.eventstreams.dsp;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.Random;
// --- <<IS-END-IMPORTS>> ---

public final class _priv

{
	// ---( internal utility methods )---

	final static _priv _instance = new _priv();

	static _priv _newInstance() { return new _priv(); }

	static _priv _cast(Object o) { return (_priv)o; }

	// ---( server methods )---




	public static final void formatName (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(formatName)>> ---
		// @sigtype java 3.5
		// [i] field:0:required prefix
		// [i] field:0:required number
		// [o] field:0:required name
		// pipeline in
		IDataCursor pipelineCursor = pipeline.getCursor();
		String prefix = IDataUtil.getString(pipelineCursor, "prefix");
		String number = IDataUtil.getString(pipelineCursor, "number");
		
		// process
		
		int length = 4 - number.length();
		String name = String.format("%1s-%2$" + length + "s", prefix, number).replace(' ', '0');
		
		// pipeline
		IDataUtil.put(pipelineCursor, "name", name);
		pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void generateTemperatureRange (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(generateTemperatureRange)>> ---
		// @sigtype java 3.5
		// [i] field:0:required count
		// [i] field:0:required minTemp
		// [i] field:0:required maxTemp
		// [o] object:1:required temperatures
		// pipeline in
		
		IDataCursor pipelineCursor = pipeline.getCursor();
		String count = IDataUtil.getString( pipelineCursor, "count");
		String minTemp = IDataUtil.getString( pipelineCursor, "minTemp");
		String maxTemp = IDataUtil.getString( pipelineCursor, "maxTemp");
		pipelineCursor.destroy();
		
		// process
		
		Double rangeMin = Double.parseDouble(minTemp);
		Double rangeMax = Double.parseDouble(maxTemp);
		
		int countInt = Integer.parseInt(count);
		Double[] temperatures = new Double[countInt];
		
		for (int i=0; i < countInt; i++) {
			Random r = new Random();
			
			temperatures[i] = round(rangeMin + (rangeMax - rangeMin) * r.nextDouble(), 2);
		}
		
		// pipeline out
		
		IDataUtil.put(pipelineCursor, "temperatures", temperatures);
		pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void getProducerState (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getProducerState)>> ---
		// @sigtype java 3.5
		// [o] field:0:required nextSequence
		// [o] field:0:required minTemp
		// [o] field:0:required maxTemp
		// [o] field:0:required numDevices
		// [o] field:0:required producerCount
		// [o] field:0:required runState
		// [o] record:1:required produced
		// [o] object:0:required requestToStop
		IDataCursor pipelineCursor = pipeline.getCursor();
		IDataUtil.put(pipelineCursor, "nextSequence", "" + _nextSequence);
		IDataUtil.put(pipelineCursor, "requestToStop", _requestToStop);
		
		IDataUtil.put(pipelineCursor, "minTemp", "" + _minTemp);
		IDataUtil.put(pipelineCursor, "maxTemp", "" + _maxTemp);
		IDataUtil.put(pipelineCursor, "numDevices", "" + _numDevices);
		IDataUtil.put(pipelineCursor, "producerCount", "" + _producerCount);
		IDataUtil.put(pipelineCursor, "runState", _runState != null ? _runState : "stopped");
		
		IDataUtil.put(pipelineCursor, "produced", _produced);
		
		pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void saveProducerState (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(saveProducerState)>> ---
		// @sigtype java 3.5
		// [i] field:0:required minTemp
		// [i] field:0:required maxTemp
		// [i] field:0:required numDevices
		// [i] field:0:required nextSequence
		// [i] field:0:required runState
		// [i] field:0:required producerCount
		// [i] record:1:required produced
		// [o] record:1:required produced
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	minTemp = IDataUtil.getString(pipelineCursor, "minTemp");
		String	maxTemp = IDataUtil.getString(pipelineCursor, "maxTemp" );
		String	numDevices = IDataUtil.getString(pipelineCursor, "numDevices");
		String	nextSequence = IDataUtil.getString(pipelineCursor, "nextSequence");
		String	count = IDataUtil.getString(pipelineCursor, "producerCount");
		String	runState = IDataUtil.getString(pipelineCursor, "runState");
		
		IData[] produced = IDataUtil.getIDataArray(pipelineCursor, "produced");
		
		// process 
		
		_minTemp = Double.parseDouble(minTemp);
		_maxTemp = Double.parseDouble(maxTemp);
		_numDevices = Integer.parseInt(numDevices);
		_nextSequence = Long.parseLong(nextSequence);
		_producerCount = Integer.parseInt(count);
		_runState = runState;
		
		long offset = _nextSequence - produced.length;
		
		if (_produced == null) {
			_produced = produced;
		} else {
			// keep max of a 100 elements;
			
			IData[] old = _produced;
			int reqCapacity = old.length + produced.length;
			
			if (reqCapacity > 50)
				reqCapacity = 50;
			
			_produced = new IData[reqCapacity];
			int newI = produced.length-1;
			int oldI = old.length-1;
			
			for (int z=reqCapacity-1; z >=0; z--) {
		
				IData e = null;
				
				if (newI >= 0) {
					e = produced[newI--];
				} else {
					e = old[oldI--];
				}
				
				_produced[z] = e;
			}
		}
		
		// pipeline out
		
		IDataUtil.put(pipelineCursor, "produced", _produced);
		pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void stop (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(stop)>> ---
		// @sigtype java 3.5
		_requestToStop = false;
		_runState = "stopped";
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	
	private static double _minTemp = 0.0;
	private static double _maxTemp = 0.0;
	private static Integer _numDevices = 10;
	private static long _nextSequence = 0;
	
	private static IData[] _produced = null;
	private static boolean _requestToStop = false;
	private static int _producerCount = 0;
	private static String _runState = null;
	
	private static double round(Double value, int decimalPoints) {
		
	    int multiplier = (int) Math.pow(10, decimalPoints);
	    int b = (int) (value * multiplier);
	    return b / (double) multiplier;
	}
	// --- <<IS-END-SHARED>> ---
}

