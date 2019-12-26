package rnd.statemachine;

public interface ProcessEvent {
    public abstract Class<? extends AbstractProcessor> nextStepProcessor();   
    public abstract ProcessState nextState();
    public abstract String getMessage();
}
