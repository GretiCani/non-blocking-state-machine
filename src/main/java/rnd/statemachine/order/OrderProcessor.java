package rnd.statemachine.order;

import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rnd.statemachine.ProcessData;
import rnd.statemachine.Processor;

@Service
@RequiredArgsConstructor
public class OrderProcessor implements Processor {
	
	private final OrderProcessorHelper helper;
        
    @Override
    public void process(ProcessData data, Consumer<ProcessData> consumerFn) {   
        helper.process(data, consumerFn); 
    }
}
