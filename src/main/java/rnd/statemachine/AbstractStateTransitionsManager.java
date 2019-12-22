package rnd.statemachine;

public abstract class AbstractStateTransitionsManager implements StateTransitionsManager {
    protected abstract ProcessData initializeState(ProcessData data) throws ProcessException;
    protected abstract ProcessData processStateTransition(ProcessData data) throws ProcessException;

    @Override
    public ProcessData processPreEvent(ProcessData data) throws ProcessException {
    	initializeState(data);
        return processStateTransition(data);
    }
    
    protected abstract void processPostEvent(ProcessData data) throws ProcessException;
}
