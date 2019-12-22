package rnd.statemachine.order;

import rnd.statemachine.ProcessState;
import rnd.statemachine.ProcessEvent;
import rnd.statemachine.Processor;

/**  
 * DEFAULT    -  submit -> orderProcessor()   -> orderCreated   -> PMTPENDING
 * PMTPENDING -  pay    -> paymentProcessor() -> paymentError   -> PMTPENDING
 * PMTPENDING -  pay    -> paymentProcessor() -> paymentSuccess -> COMPLETED 
 */
public enum OrderEvent implements ProcessEvent {

    submit {
        @Override
        public Class<? extends Processor> nextStepProcessor() {
                return OrderProcessor.class;
        }
        
        /**
         * This event has no effect on state so return current state
         */
        @Override
        public ProcessState nextState() {
                return OrderState.Default;
        }

		@Override
		public String getMessage() {
			return "Order submitted";
		}

    },
    orderCreated {
    	/**
    	 * This event does not trigger any process
    	 * So return null 
    	 */
        @Override
        public Class<? extends Processor> nextStepProcessor() {
            return null;
        }
        
        @Override
        public ProcessState nextState() {
                return OrderState.PaymentPending;
        }

		@Override
		public String getMessage() {
			return "Order create, payment pending";
		}

    },
    pay {
        @Override
        public Class<? extends Processor> nextStepProcessor() {
                return PaymentProcessor.class;
        }
        
        /**
         * This event has no effect on state so return current state
         */
        @Override
        public ProcessState nextState() {
                return OrderState.PaymentPending;
        }

		@Override
		public String getMessage() {
			return "We are processing your payment, please check your email for the order confirmation number";
		}
    },
    paymentSuccess {
    	/**
    	 * This event does not trigger any process
    	 * So return null 
    	 */
        @Override
        public Class<? extends Processor> nextStepProcessor() {
            return null;
        }
        @Override
        public ProcessState nextState() {
                return OrderState.Completed;
        }
		@Override
		public String getMessage() {
			return "Payment success, order completed";
		}
    },
    paymentError {
    	/**
    	 * This event does not trigger any process
    	 * So return null 
    	 */
        @Override
        public Class<? extends Processor> nextStepProcessor() {
            return null;
        }
        
        @Override
        public ProcessState nextState() {
                return OrderState.PaymentPending;
        }

		@Override
		public String getMessage() {
			return "Payment processing error";
		}
    };
}
