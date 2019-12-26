package rnd.statemachine.order;

import java.util.function.Consumer;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import rnd.statemachine.ProcessData;

@Service
public class PaymentProcessorHelper {
     
    /**
     * A long running process
     * @param data
     * @param consumerFn
     */
    @Async("threadPoolTaskExecutor")
    public void process(ProcessData data, Consumer<ProcessData> consumerFn) {
        try{
            //simulate a long running process
            Thread.sleep(2000);

            if(((OrderData)data).getPayment() > 0) {
                ((OrderData)data).setEvent(OrderEvent.paymentSuccess);
            } else {
                ((OrderData)data).setEvent(OrderEvent.paymentError);
            }
        }catch(InterruptedException e){
            //TODO: Use a new state transition to include system error
        }

        consumerFn.accept(data);
    }
}
