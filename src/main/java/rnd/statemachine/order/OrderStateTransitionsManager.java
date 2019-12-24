package rnd.statemachine.order;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rnd.statemachine.AbstractStateTransitionsManager;
import rnd.statemachine.ProcessData;

/**
 * This class manages various state transitions based on the event The
 * superclass AbstractStateTransitionsManager calls the two methods
 * initializeState and processStateTransition in that order
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class OrderStateTransitionsManager extends AbstractStateTransitionsManager {

	private final ApplicationContext context;
	private final OrderDbService dbService;

	@Override
	public void processPostEvent(ProcessData data) {

		log.info("Post-event: " + data.getEvent().toString());
		dbService.getStates().put(((OrderData) data).getOrderId(),
				(OrderState) data.getEvent().nextState());
				
        //TODO: if the post-event is either paymentSuccess or paymentError
		//send an email to the customer with a confirmation number
		
		log.info("Final state: " + dbService.getStates().get(((OrderData) data).getOrderId()).name());
		log.info("??*************************************");
	}

	@Override
	protected ProcessData processStateTransition(ProcessData sdata) {

		OrderData data = (OrderData) sdata;
		log.info("Pre-event: " + data.getEvent().toString());
		this.context.getBean(data.getEvent().nextStepProcessor()).process(data, this::processPostEvent);
		return data;
	}

	private OrderData checkStateForReturningCustomers(OrderData data) throws OrderException {
		// returning customers must have a state
		if (data.getOrderId() != null) {
			if (this.dbService.getStates().get(data.getOrderId()) == null) {
				throw new OrderException("No state exists for orderId=" + data.getOrderId());
			} else if (this.dbService.getStates().get(data.getOrderId()) == OrderState.Completed) {
				throw new OrderException("Order is completed for orderId=" + data.getOrderId());
			} else {
				log.info("Initial state: " + dbService.getStates().get(data.getOrderId()).name());
			}
		}
		return data;
	}

	@Override
	protected ProcessData initializeState(ProcessData sdata) throws OrderException {

		OrderData data = (OrderData) sdata;

		if (data.getOrderId() != null) {
			return checkStateForReturningCustomers(data);
		}

		UUID orderId = UUID.randomUUID();
		data.setOrderId(orderId);
		dbService.getStates().put(orderId, (OrderState) OrderState.Default);

		log.info("Initial state: " + dbService.getStates().get(data.getOrderId()).name());
		return data;
	}

	public ConcurrentHashMap<UUID, OrderState> getStates() {
		return dbService.getStates();
	}
}
