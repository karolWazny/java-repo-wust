package pl.edu.pwr.java.lab8.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.edu.pwr.java.lab8.model.mapper.soap.PaymentMapper;
import pl.edu.pwr.java.lab8.service.PaymentService;
import pl.pwr.java.lab8.soap.payments.*;

import java.util.stream.Collectors;

@Endpoint
@RequiredArgsConstructor
public class PaymentsEndpoint {
    public final static String NAMESPACE_URI = "pl/pwr/java/lab8/soap/payments";

    private final PaymentService paymentService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "fetchPaymentsForPersonRequest")
    @ResponsePayload
    public FetchPaymentsResponse
    fetchPaymentsForPerson(@RequestPayload FetchPaymentsForPersonRequest request){
        FetchPaymentsResponse response = new FetchPaymentsResponse();
        response.getPayments().addAll(paymentService
                .fetchPaymentsForPerson(request.getPersonId(), request.getPage())
                .stream()
                .map(PaymentMapper::map)
                .collect(Collectors.toList()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "fetchPaymentsForEventRequest")
    @ResponsePayload
    public FetchPaymentsResponse
    fetchPaymentsForEvent(@RequestPayload FetchPaymentsForEventRequest request){
        FetchPaymentsResponse response = new FetchPaymentsResponse();
        response.getPayments().addAll(paymentService
                .fetchPaymentsForEvent(request.getEventId(), request.getPage())
                .stream()
                .map(PaymentMapper::map)
                .collect(Collectors.toList()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createPaymentRequest")
    @ResponsePayload
    public CreatePaymentResponse
    createPayment(@RequestPayload CreatePaymentRequest request){
        CreatePaymentResponse response = new CreatePaymentResponse();
        response.setPayment(PaymentMapper
                .map(paymentService
                        .createPayment(PaymentMapper
                                .map(request
                                        .getPayment()))));
        return response;
    }
}
