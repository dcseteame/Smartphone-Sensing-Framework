package edu.example.ssf.mma.statemachine;

import android.util.Log;

import edu.example.ssf.mma.D3SHttpClient;
import edu.example.ssf.mma.data.LocalDataStorage;

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
    public void doit() {

    }

    @Override
    public void entry() {
        Log.d("STATEMACHINE", "entry State: SEND - Sending measure data to backend");
        //Send data to backend.
        D3SHttpClient.getInstance().addMeasureEntries(LocalDataStorage.getUuid());
    }

    @Override
    public void exit() {

    }
}
