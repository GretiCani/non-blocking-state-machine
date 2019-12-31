# non-blocking-state-machine

This Spring Boot Java project extends the [simple-state-machine](https://github.com/mapteb/simple-state-machine) project to make the processors asynchronous and non-blocking.

## Usage

The first step in using the framework is to configure the state transitions. 
An online order processing system is considered as an example for the demo. The order processing system is assumed to have the following state transitions:

|Initial State |Pre-event |   Processor    |        Post-event  |  Final State  |
| --- | --- | --- | --- | --- |  
|DEFAULT     |  submit  | OrderProcessor  | orderCreated     |PMTPENDING |
|PMTPENDING   | pay     | PaymentProcessor  | paymentError     |PMTERROREMAILPENDING |
|PMTERROREMAILPENDING   | errorEmail     | PaymentErrorEmailProcessor  | pmtErrorEmailSent     |PMTPENDING |
|PMTPENDING  |  pay     | PaymentProcessor  | paymentSuccess  | PMTSUCCESSEMAILPENDING |
|PMTSUCCESSEMAILPENDING   | successEmail     | PaymentSuccessEmailProcessor  | pmtSuccessEmailSent     |COMPLETED |

where the [PaymentProcessor](https://github.com/mapteb/non-blocking-state-machine/blob/master/src/main/java/rnd/statemachine/order/PaymentProcessor.java) is considered a long running process and made asynchronous and non-blocking. All other processors are assumed synchronous. The above state transitions are configured in Java enums, [OrderState](https://github.com/mapteb/non-blocking-state-machine/blob/master/src/main/java/rnd/statemachine/order/OrderState.java) and [OrderEvent](https://github.com/mapteb/non-blocking-state-machine/blob/master/src/main/java/rnd/statemachine/order/OrderEvent.java).

The demo includes an [OrderController](https://github.com/mapteb/non-blocking-state-machine/blob/master/src/main/java/rnd/statemachine/order/OrderController.java) with two APIs to test the following scenarios:

## Testing

Build using:\
$ ./gradlew build

Run using:\
$ \gradlew bootRun

### Submit an order

User submits an order\
System responds with an order ID.\
Log messages confirm the state transition.

$ curl -X POST http://localhost:8080/order/items

### Submit an invalid payment (amount = 0.00)

User submits an invalid payment for the order\
System responds with a message "We are processing your payment. Please check your email for the order confirmation number."\
System also sends an email with an error message.\
Log messages confirm the state transition.

$ curl -X POST http://localhost:8080/orders/1e6092da-3bef-4377-8a02-5a4cb93f4a96/payment/0

### Submit a valid payment (amount > 0.00)

User submits a valid payment for the order\
System responds with a message "We are processing your payment. Please check your email for the order confirmation number."\
System also sends an email with a success message.\
Log messages confirm the state transition.

$ curl -X POST http://localhost:8080/orders/1e6092da-3bef-4377-8a02-5a4cb93f4a96/payment/1

### Submit a valid payment for an order which is already completed

User submits a valid payment for the order\
System responds with a message "Order is completed for orderId=1e6092da-3bef-4377-8a02-5a4cb93f4a96."\
No state transition occurs in this case.

$ curl -X POST http://localhost:8080/orders/1e6092da-3bef-4377-8a02-5a4cb93f4a96/payment/2

Interested readers can run the included Apache JMeter [test plan](https://github.com/mapteb/non-blocking-state-machine/tree/master/src/test/jmeter) to test for multiple simultaneous users placing orders and paying for the orders.