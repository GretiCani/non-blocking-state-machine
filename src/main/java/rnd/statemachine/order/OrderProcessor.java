package rnd.statemachine.order;

import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import rnd.statemachine.ProcessData;
import rnd.statemachine.Processor;

@Service
public class OrderProcessor implements Processor {
	
	private final OrderProcessorHelper helper;
	
	public OrderProcessor(OrderProcessorHelper helper) {
		this.helper = helper;
	}
        
    @Override
    public void process(ProcessData data, Consumer<ProcessData> consumerFn) {   
        helper.process(data, consumerFn); 
    }
}
