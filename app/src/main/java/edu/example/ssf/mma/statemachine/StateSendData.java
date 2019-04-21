package edu.example.ssf.mma.statemachine;

import android.util.Log;

import org.json.JSONException;

import edu.example.ssf.mma.D3SHttpClient;
import edu.example.ssf.mma.data.LocalDataStorage;
import edu.example.ssf.mma.data.MeasurementEntry;

/**
 * This State is used to represent the phone sending data to the backend.
 * In this state the collected data is sent to the backend
 */
public class StateSendData extends AbstractState {

    /**
     * Instantiates a new abstract state.
     *
     * @param parentStateMachine callback-reference to the IParentStateMachine
     */
    public StateSendData(IParentStateMachine parentStateMachine) {
        super("SEND", parentStateMachine);
    }

    @Override
    public synchronized void doit() {
        Log.d("STATEMACHINE", "entry State: SEND - Sending measure data to backend");
        //Send data to backend.
        MeasurementEntry entry = fromLocalDataStorage();
        try {
            D3SHttpClient.getInstance().addMeasureEntries(LocalDataStorage.getUuid(), entry);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private MeasurementEntry fromLocalDataStorage() {
        MeasurementEntry entry = new MeasurementEntry();
        entry.setTime(System.currentTimeMillis());
        entry.setValuesX(LocalDataStorage.getxValues());
        entry.setValuesY(LocalDataStorage.getyValues());
        entry.setValuesZ(LocalDataStorage.getzValues());

        return entry;
    }

    @Override
    public void entry() {
        D3SHttpClient.getInstance().sendPing(LocalDataStorage.getUuid());
    }

    @Override
    public void exit() {

    }
}
