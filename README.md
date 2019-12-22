# non-blocking-state-machine

This Spring Boot Java project extends the [simple-state-machine](https://github.com/mapteb/simple-state-machine) project to make the processors asynchronous and non-blocking.

## Usage

The usage of this framework is illustrated using an online order processing system. The order processing system is assumed to have the following state transitions:

|Initial State |Pre-event |   Processor    |        Post-event  |  Final State  |
| --- | --- | --- | --- | --- |  
|DEFAULT    ->|  submit ->| orderProcessor() ->| orderCreated   -> |PMTPENDING |
|PMTPENDING -> | pay    ->| paymentProcessor() ->| paymentError   -> |PMTPENDING |
|PMTPENDING ->|  pay    ->| paymentProcessor() ->| paymentSuccess ->| COMPLETED |

The paymentProcessor() is considered a long running process and made asynchronous and non-blocking.

The framework is illustrated with an order processing demo. The demo includes the following scenarios:

### Happy path

User submits an order
System responds with an order ID.
User makes a valid payment for the order
System responds with a message "We are processing your payment. Please check your email for the order confirmation number."

### Error path

User submits an order
System responds with an order ID.
User makes an invalid payment for the order
System responds with a message "We are processing your payment. Please check your email for the order confirmation number."

Two APIs are included to test the above steps. Here are the curl commands to call the APIs:

### Happy path testing

$ curl -X POST http://localhost:8080/order/items

Order submitted, orderId = 1e6092da-3bef-4377-8a02-5a4cb93f4a96

The stdout log displays the state transitions like:

Initial state: Default
Pre-event: submit
Post-event: orderCreated
Final state: PaymentPending

$ curl -X POST http://localhost:8080/orders/1e6092da-3bef-4377-8a02-5a4cb93f4a96/payment/1
We are processing your payment, please check your email for the order confirmation number

The stdout logs display the state transformations for the above cases like:

Initial state: PaymentPending
Pre-event: pay
Post-event: paymentSuccess
Final state: Completed

(For brevity, sending email is not included in the demo.)

### Error path testing

$ curl -X POST http://localhost:8080/order/items

Order submitted, orderId = 97b0c82a-3dfd-4c94-a9f6-e8aa08e25e88

The stdout log displays the state transitions like:

Initial state: Default
Pre-event: submit
Post-event: orderCreated
Final state: PaymentPending

$ curl -X POST http://localhost:8080/orders/97b0c82a-3dfd-4c94-a9f6-e8aa08e25e88/payment/0
We are processing your payment, please check your email for the order confirmation number

The stdout logs display the state transformations for the above cases like:

Initial state: PaymentPending
Pre-event: pay
Post-event: paymentError
Final state: PaymentPending

(Invalid payment is simulated using 0 value for the payment)

