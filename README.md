# non-blocking-state-machine

This Java/Spring project extends the [simple-state-machine](https://github.com/mapteb/simple-state-machine) project to make the processors to be asynchronous and non-blocking.

## Usage

The usage of this framework is illustrated using an online order processing system. The order processing system is assumed to have the following state transitions:

|Initial State |Pre-event |   Processor    |        Post-event  |  Final State  |
| --- | --- | --- | --- | --- |  
|DEFAULT    ->|  submit ->| orderProcessor() ->| orderCreated   -> |PMTPENDING |
|PMTPENDING -> | pay    ->| paymentProcessor() ->| paymentError   -> |PMTPENDING |
|PMTPENDING ->|  pay    ->| paymentProcessor() ->| paymentSuccess ->| COMPLETED |

The paymentProcessor() is considered a long running process and made asynchronous and non-blocking.
