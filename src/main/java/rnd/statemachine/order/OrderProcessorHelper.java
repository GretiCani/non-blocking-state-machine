package rnd.statemachine.order;

import java.util.function.Consumer;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import rnd.statemachine.ProcessData;

@Service
public class OrderProcessorHelper {
    
    @Async("threadPoolTaskExecutor")
    public void process(ProcessData data, Consumer<ProcessData> consumerFn) {
        ((OrderData)data).setEvent(OrderEvent.orderCreated); 
        consumerFn.accept(data);
    }
}
