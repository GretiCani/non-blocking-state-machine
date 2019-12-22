package rnd.statemachine;

import java.util.function.Consumer;

public interface Processor {
    public void process(ProcessData data, Consumer<ProcessData> consumerFn);
}
