package edu.example.ssf.mma.statemachine;


import java.math.BigDecimal;

import edu.example.ssf.mma.config.ConfigApp;
import edu.example.ssf.mma.data.CurrentTickData;
import edu.example.ssf.mma.data.LocalDataStorage;
import edu.example.ssf.mma.data.MathCalculations;

public class StateMachine implements IStateMachine, IParentStateMachine {

    /*
            List of states
     */
    private AbstractState idle = null;
    private AbstractState measure = null;
    private AbstractState send = null;

    private AbstractState current = null;
    private AbstractState next = null;

    private String stateLabel = "Not Set";

    private int consecutiveMeasures = 0;

    private final double THRESHOLD = 1.5;



    //Check if a state change happens with the last tick.
    @Override
    public void transisionCheck() {
        //NPE otherwise
        if(current == null) initStateMachine();

        if(current.equals(idle)){
           checkIdleTransition();
        }
        if(current.equals(measure)){
            checkMeasureTransition();
        }
        if(current.equals(send)){
            checkSendTransition();
        }
    }

    @Override
    public void stateCheck() {
        current.doit();
        consecutiveMeasures++;
        if(current != next){
            current.exit();
            next.entry();
        }
        current = next;
        stateLabel = current.getStateName();
    }

    @Override
    public String getStateLabel() {
        return stateLabel;
    }

    @Override
    public void initStateMachine() {
        idle = new StateIdle(this);
        measure = new StateMeasureData(this);
        send = new StateSendData(this);

        current = idle;
        next = idle;

        stateLabel = idle.getStateName();
        LocalDataStorage.setStateLabel(stateLabel);

    }

    @Override
    public void setStateLabel(String label) {
        this.stateLabel = label;
    }

    private void checkSendTransition() {
        if(phoneMoves()){
            changeState(idle);
        } else {
            changeState(measure);
        }
    }

    private void checkMeasureTransition() {
        //Time configured is reached / passed
        if(secondsPassed(ConfigApp.measureBulkInSeconds)){
            changeState(send);
        }
        if(phoneMoves()){
            changeState(idle);
        }
    }

    private void checkIdleTransition() {
        if(phoneNotMovedInLastNSeconds(ConfigApp.idleTimeInSeconds)
                && secondsPassed(ConfigApp.idleTimeInSeconds)){
            changeState(measure);
        }
    }

    private void changeState(AbstractState next) {
        this.next = next;
        consecutiveMeasures = 0;
        setStateLabel(next.getStateName());
        LocalDataStorage.setStateLabel(next.getStateName());
    }

    private boolean phoneNotMovedInLastNSeconds(int n) {
        if (phoneMoves()) {
            consecutiveMeasures = 0;
        }
        // n Seconds * 60 measures in a second. if all of them are not moving, state idle transition
        int checkSeconds = n * 60;
        if(consecutiveMeasures >= checkSeconds){
            return true;
        }
        return false;
    }

    /**
     * This Method checks, if the phone is currently moving.
     * This is done by checking the Acc-Meter
     * @return true, if phone moves currently
     */
    private boolean phoneMoves() {
        final float rotX = CurrentTickData.rotationX;
        final float rotY = CurrentTickData.rotationY;
        final float rotZ = CurrentTickData.rotationZ;

        double vecRot = MathCalculations.calculatePythagoras(rotX,rotY,rotZ);

        return MathCalculations.isValueGreaterThen0(vecRot);
    }

    private boolean secondsPassed(int seconds) {
        return ((consecutiveMeasures * ConfigApp.delayStateMachineTimerTaskTimeMs) / 1000)
                >= seconds;
    }
}
