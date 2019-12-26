package rnd.statemachine.order;

import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rnd.statemachine.AbstractProcessor;
import rnd.statemachine.ProcessData;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentSuccessEmailProcessor extends AbstractProcessor {
    
    @Override
    public void processAsync(ProcessData data, Consumer<ProcessData> consumerFn) {
		//TODO: call the email service
		log.info("Sent payment success email");
		((OrderData)data).setEvent(OrderEvent.paymentSuccessEmailSent);
		consumerFn.accept(data);
	}

	@Override
	public boolean isAsync() {
		return true;
	}
}
