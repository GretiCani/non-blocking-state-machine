package rnd.statemachine.order;

import java.util.Set;
import java.util.function.Consumer;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rnd.statemachine.AbstractProcessor;
import rnd.statemachine.AsyncProcessor;
import rnd.statemachine.ProcessData;
import rnd.statemachine.Processor;

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
