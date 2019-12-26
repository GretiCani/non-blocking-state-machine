package rnd.statemachine.order;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rnd.statemachine.AbstractProcessor;
import rnd.statemachine.AbstractStateTransitionsManager;
import rnd.statemachine.AsyncProcessor;
import rnd.statemachine.ProcessData;
import rnd.statemachine.ProcessException;

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
	protected void processPostEvent(ProcessData data) {

		log.info("Post-event: " + data.getEvent().toString());
		dbService.getStates().put(((OrderData) data).getOrderId(),
				(OrderState) data.getEvent().nextState());
				
        //if the post-event is either paymentSuccess or paymentError
		//then initiate the email process
		log.info("Final state: " + dbService.getStates().get(((OrderData) data).getOrderId()).name());
		log.info("??*************************************");

		if ((OrderEvent) data.getEvent() == OrderEvent.paymentSuccess) {
			((OrderData) data).setEvent(OrderEvent.successEmail);
			processPreEvent(data);
		} else if ((OrderEvent) data.getEvent() == OrderEvent.paymentError) {
			((OrderData) data).setEvent(OrderEvent.errorEmail);
			processPreEvent(data);
		} 
	}

	@Override
	protected ProcessData processStateTransition(ProcessData sdata) throws ProcessException {
 
		OrderData data = (OrderData) sdata;
		log.info("Pre-event: " + data.getEvent().toString());
		AbstractProcessor processor = this.context.getBean(data.getEvent().nextStepProcessor());
		if (processor.isAsync()) {
			processor.processAsync(data, this::processPostEvent);
		} else {
			data = (OrderData)this.context.getBean(data.getEvent().nextStepProcessor()).process(data);
			processPostEvent(data);;
		}
		return data;
	}

	@Override
	protected void initializeState(ProcessData sdata) throws OrderException {

		OrderData data = (OrderData) sdata;

		//validate state
		checkStateForReturningCustomers(data);

		if ((OrderEvent) data.getEvent() == OrderEvent.submit) {
			UUID orderId = UUID.randomUUID();
			data.setOrderId(orderId);
		}

		dbService.getStates().put(data.getOrderId(), (OrderState)((OrderEvent) data.getEvent()).nextState());

		log.info("Initial state: " + dbService.getStates().get(data.getOrderId()).name());
	}

	private void checkStateForReturningCustomers(OrderData data) throws OrderException {
		// returning customers must have a state
		if (data.getOrderId() != null) {
			if (this.dbService.getStates().get(data.getOrderId()) == null) {
				throw new OrderException("No state exists for orderId=" + data.getOrderId());
			} else if (this.dbService.getStates().get(data.getOrderId()) == OrderState.Completed) {
				throw new OrderException("Order is completed for orderId=" + data.getOrderId());
			} 
		}
	}

	public ConcurrentHashMap<UUID, OrderState> getStates() {
		return dbService.getStates();
	}
}
