package rnd.statemachine.order;

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
    public ProcessData process(ProcessData data) {
		//TODO: generate order confirmation number and call the email service
		log.info("Sent payment success email");
		((OrderData)data).setEvent(OrderEvent.paymentSuccessEmailSent);
		return data;
	}

	@Override
	public boolean isAsync() {
		return false;
	}
}
