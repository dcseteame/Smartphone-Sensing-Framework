package edu.example.ssf.mma.statemachine;

import android.util.Log;

import edu.example.ssf.mma.D3SHttpClient;
import edu.example.ssf.mma.data.LocalDataStorage;


/**
 * This State is used to represent the phone moving.
 * In this state no data is collected and no ping is sent
 */
public class StateIdle extends AbstractState {

    /**
     * Instantiates a new abstract state.
     *
     * @param parentStateMachine callback-reference to the IParentStateMachine
     */
    public StateIdle(IParentStateMachine parentStateMachine) {
        super("IDLE", parentStateMachine);
    }

    @Override
    public void doit() {
        Log.d("STATEMACHINE", "State: IDLE - Sending ping to backend");
        D3SHttpClient.getInstance().sendPing(LocalDataStorage.getUuid());
    }

    @Override
    public void entry() {
        Log.d("STATEMACHINE", "entry Sate: Idle");
        // Unregister from Backend
        D3SHttpClient.getInstance().unregister(LocalDataStorage.getUuid());
    }

    @Override
    public void exit() {
        Log.d("STATEMACHINE", "exit State: Idle");
    }
}
