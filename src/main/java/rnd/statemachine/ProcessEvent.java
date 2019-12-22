package rnd.statemachine;

public interface ProcessEvent {
    public abstract Class<? extends Processor> nextStepProcessor();   
    public abstract ProcessState nextState();
    public abstract String getMessage();
}
