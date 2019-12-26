package rnd.statemachine;

import java.util.function.Consumer;

public interface Processor {
    public ProcessData process(ProcessData data);
}
