package rnd.statemachine.order;

import rnd.statemachine.ProcessState;
import rnd.statemachine.AbstractProcessor;
import rnd.statemachine.ProcessEvent;
import rnd.statemachine.Processor;

/**  
 * DEFAULT    -  submit -> orderProcessor()   -> orderCreated   -> PMTPENDING
 * PMTPENDING -  pay    -> paymentProcessor() -> paymentError   -> PMTERROREMAILPENDING
 * PMTERROREMAILPENDING- errorEmail  -> paymentErrorEmailProcessor()   -> pmtErrorEmailSent   -> PMTPENDING
 * PMTPENDING -  pay    -> paymentProcessor() -> paymentSuccess -> PMTSUCCESSEMAILPENDING
 * PMTSUCCESSEMAILPENDING - successEmail -> paymentSuccessEmailProcessor() -> pmtSuccessEmailSent -> COMPLETED
 */
public enum OrderEvent implements ProcessEvent {

    submit {
        @Override
        public Class<? extends AbstractProcessor> nextStepProcessor() {
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
        public Class<? extends AbstractProcessor> nextStepProcessor() {
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
        public Class<? extends AbstractProcessor> nextStepProcessor() {
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
        public Class<? extends AbstractProcessor> nextStepProcessor() {
            return null;
        }
        @Override
        public ProcessState nextState() {
                return OrderState.paymentSuccessEmailPending;
        }
		@Override
		public String getMessage() {
			return "Payment success, processing success email";
        }
    },
    paymentError {
    	/**
    	 * This event does not trigger any process
    	 * So return null 
    	 */
        @Override
        public Class<? extends AbstractProcessor> nextStepProcessor() {
            return null;
        }
        
        @Override
        public ProcessState nextState() {
                return OrderState.paymentErrorEmailPending;
        }

		@Override
		public String getMessage() {
			return "Payment processing error, processing error email";
        }
    },
    errorEmail {
    	/**
    	 * This event does not trigger any process
    	 * So return null 
    	 */
        @Override
        public Class<? extends AbstractProcessor> nextStepProcessor() {
            return PaymentErrorEmailProcessor.class;
        }
        @Override
        public ProcessState nextState() {
                return OrderState.paymentErrorEmailPending;
        }
		@Override
		public String getMessage() {
			return "Payment error, processing error email";
        }
    },
    successEmail {
    	/**
    	 * This event does not trigger any process
    	 * So return null 
    	 */
        @Override
        public Class<? extends AbstractProcessor> nextStepProcessor() {
            return PaymentSuccessEmailProcessor.class;
        }
        
        @Override
        public ProcessState nextState() {
                return OrderState.paymentSuccessEmailPending;
        }

		@Override
		public String getMessage() {
			return "Payment processing success, processing success email";
        }
    },
    paymentErrorEmailSent {

        @Override
        public Class<? extends AbstractProcessor> nextStepProcessor() {
            return null;
        }
        
        @Override
        public ProcessState nextState() {
                return OrderState.PaymentPending;
        }

		@Override
		public String getMessage() {
			return "Payment processing error, email sent";
        }
    },
    paymentSuccessEmailSent {
    	/**
    	 * This event does not trigger any process
    	 * So return null 
    	 */
        @Override
        public Class<? extends AbstractProcessor> nextStepProcessor() {
            return null;
        }
        
        @Override
        public ProcessState nextState() {
                return OrderState.Completed;
        }

		@Override
		public String getMessage() {
			return "Payment processing success, order completed";
        }
    };
}
