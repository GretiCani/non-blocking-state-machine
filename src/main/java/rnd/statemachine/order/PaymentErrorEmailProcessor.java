package rnd.statemachine.order;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rnd.statemachine.AbstractProcessor;
import rnd.statemachine.ProcessData;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentErrorEmailProcessor extends AbstractProcessor {
    
    @Override
    public ProcessData process(ProcessData data) {
		//TODO: call the email service
		log.info("Sent payment error email");
		((OrderData)data).setEvent(OrderEvent.paymentErrorEmailSent);
		return data;
	}

	@Override
	public boolean isAsync() {
		return false;
	}
}
