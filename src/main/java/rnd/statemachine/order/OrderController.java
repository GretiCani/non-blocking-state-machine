package rnd.statemachine.order;

import java.util.UUID;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import rnd.statemachine.ProcessException;

@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderStateTransitionsManager stateTrasitionsManager;

    /**
     * Quick API to test the payment event
     * @param amount
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("/orders/{id}/payment/{amount}")
    public String handleOrderPayment( 
            @PathVariable double amount,
            @PathVariable UUID id) throws Exception {

        OrderData data = new OrderData();
    	data.setPayment(amount);
    	data.setOrderId(id);
    	data.setEvent(OrderEvent.pay);
    	data = (OrderData)stateTrasitionsManager.processPreEvent(data);
    	
        return ((OrderEvent)data.getEvent()).getMessage();
    }
    
    @ExceptionHandler(value=OrderException.class)
    public String handleOrderException(OrderException e) {
        return e.getMessage();
    }
    
    /**
     * API to test the order submit event
     * @return
     * @throws ProcessException
     */
    @PostMapping("/order/items")
    public String handleOrderSubmit() throws ProcessException {

        OrderData data = new OrderData();
        data.setEvent(OrderEvent.submit);
        data = (OrderData)stateTrasitionsManager.processPreEvent(data);
        
        return ((OrderEvent)data.getEvent()).getMessage() + ", orderId = " + data.getOrderId();
    }
}

