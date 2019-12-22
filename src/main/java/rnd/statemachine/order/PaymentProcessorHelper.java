package rnd.statemachine.order;

import java.util.function.Consumer;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import rnd.statemachine.ProcessData;

@Service
public class PaymentProcessorHelper {
    
    @Async("threadPoolTaskExecutor")
    public void process(ProcessData data, Consumer<ProcessData> consumerFn) {
        try{
            Thread.sleep(2000);
        }catch(Exception e){}
        if(((OrderData)data).getPayment() < 1.00) {
        	((OrderData)data).setEvent(OrderEvent.paymentError);
        } else {
            ((OrderData)data).setEvent(OrderEvent.paymentSuccess);
        }
        consumerFn.accept(data);
    }
}
