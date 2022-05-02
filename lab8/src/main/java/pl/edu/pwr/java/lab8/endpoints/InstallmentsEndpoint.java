package pl.edu.pwr.java.lab8.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.edu.pwr.java.lab8.model.mapper.soap.InstallmentMapper;
import pl.edu.pwr.java.lab8.service.InstallmentService;
import pl.pwr.java.lab8.soap.installments.*;

import java.util.stream.Collectors;

@Endpoint
@RequiredArgsConstructor
public class InstallmentsEndpoint {
    public final static String NAMESPACE_URI = "pl/pwr/java/lab8/soap/installments";

    private final InstallmentService installmentService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "fetchUnpaidInstallmentsForPersonRequest")
    @ResponsePayload
    public FetchInstallmentsResponse
    fetchUnpaidInstallmentsForPerson(@RequestPayload FetchUnpaidInstallmentsForPersonRequest request){
        FetchInstallmentsResponse response = new FetchInstallmentsResponse();
        response.getInstallments().addAll(installmentService
                .fetchUnpaidInstallmentsForPerson(request.getPersonId())
                .stream()
                .map(InstallmentMapper::map)
                .collect(Collectors.toList()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "fetchPendingInstallmentsForPersonRequest")
    @ResponsePayload
    public FetchInstallmentsResponse
    fetchPendingInstallmentsForPerson(@RequestPayload FetchPendingInstallmentsForPersonRequest request){
        FetchInstallmentsResponse response = new FetchInstallmentsResponse();
        response.getInstallments().addAll(installmentService
                .fetchPendingInstallmentsForPerson(request.getPersonId())
                .stream()
                .map(InstallmentMapper::map)
                .collect(Collectors.toList()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "fetchOverdueInstallmentsForPersonRequest")
    @ResponsePayload
    public FetchInstallmentsResponse
    fetchOverdueInstallmentsForPerson(@RequestPayload FetchOverdueInstallmentsForPersonRequest request){
        FetchInstallmentsResponse response = new FetchInstallmentsResponse();
        response.getInstallments().addAll(installmentService
                .fetchOverdueInstallmentsForPerson(request.getPersonId())
                .stream()
                .map(InstallmentMapper::map)
                .collect(Collectors.toList()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "fetchInstallmentsForEventRequest")
    @ResponsePayload
    public FetchInstallmentsResponse
    fetchInstallmentsForEvent(@RequestPayload FetchInstallmentsForEventRequest request){
        FetchInstallmentsResponse response = new FetchInstallmentsResponse();
        response.getInstallments().addAll(installmentService
                .fetchInstallmentsForEvent(request.getEventId(), request.getPage())
                .stream()
                .map(InstallmentMapper::map)
                .collect(Collectors.toList()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createInstallmentRequest")
    @ResponsePayload
    public CreateInstallmentResponse
    createInstallment(@RequestPayload CreateInstallmentRequest request){
        CreateInstallmentResponse response = new CreateInstallmentResponse();
        response.setInstallment(InstallmentMapper
                .map(installmentService
                        .createInstallment(InstallmentMapper
                                .map(request
                                        .getInstallment()))));
        return response;
    }
}
