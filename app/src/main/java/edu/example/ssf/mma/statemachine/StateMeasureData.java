package edu.example.ssf.mma.statemachine;

import android.util.Log;

import edu.example.ssf.mma.D3SHttpClient;
import edu.example.ssf.mma.data.CurrentTickData;
import edu.example.ssf.mma.data.LocalDataStorage;

/**
 * This State is used to represent the phone collecting data.
 * In this state the accelerometer data is collected and written to a global storage.
 */
public class StateMeasureData extends AbstractState {

    /**
     * Instantiates a new abstract state.
     *
     * @param parentStateMachine callback-reference to the IParentStateMachine
     */
    public StateMeasureData(IParentStateMachine parentStateMachine) {
        super("MEASURE", parentStateMachine);
    }

    /**
     * Add normalized acc Data
     */
    @Override
    public void doit() {
       LocalDataStorage.addDataSet(
               CurrentTickData.accX - LocalDataStorage.getNullify()[0],
               CurrentTickData.accY- LocalDataStorage.getNullify()[1],
               CurrentTickData.accZ- LocalDataStorage.getNullify()[2]);
    }

    /**
     * On entry, register device if needed
     * Also store nullification Vector to later normalize values send to backend
     */
    @Override
    public void entry() {
        Log.d("STATEMACHINE", "entry State: Measure");
        LocalDataStorage.resetStorages();

        if(LocalDataStorage.getUuid().isEmpty())
            registerDevice();

        LocalDataStorage.setNullify(
                CurrentTickData.accX, CurrentTickData.accY, CurrentTickData.accZ);
    }

    @Override
    public void exit() {
        Log.d("STATEMACHINE", "exit State: Measure");
    }


    private void registerDevice() {
        D3SHttpClient.getInstance().register();
    }
}
