package rnd.statemachine.order;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rnd.statemachine.AbstractProcessor;
import rnd.statemachine.ProcessData;

@Service
@RequiredArgsConstructor
public class OrderProcessor extends AbstractProcessor {

    @Override
    public ProcessData process(ProcessData data) {
        // TODO: perform tasks necessary for creating order
        ((OrderData) data).setEvent(OrderEvent.orderCreated);
        return data;
    }

    @Override
    public boolean isAsync() {
        return false;
    }

}
