package rnd.statemachine.order;

import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rnd.statemachine.ProcessData;
import rnd.statemachine.Processor;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentSuccessEmailProcessor implements Processor {
    
    @Override
    public void process(ProcessData data, Consumer<ProcessData> consumerFn) {
		//TODO: call the email service
		log.info("Sent payment success email");
		((OrderData)data).setEvent(OrderEvent.paymentSuccessEmailSent);
		consumerFn.accept(data);
	}
}
