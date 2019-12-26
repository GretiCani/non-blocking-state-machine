package rnd.statemachine.order;

import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rnd.statemachine.AbstractProcessor;
import rnd.statemachine.AsyncProcessor;
import rnd.statemachine.ProcessData;
import rnd.statemachine.Processor;

@Service
@RequiredArgsConstructor
public class PaymentProcessor extends AbstractProcessor {
	
	private final PaymentProcessorHelper helper;
    
    @Override
    public void processAsync(ProcessData data, Consumer<ProcessData> consumerFn) {
		helper.process(data, consumerFn);
	}

	@Override
	public boolean isAsync() {
		return true;
	}
}
