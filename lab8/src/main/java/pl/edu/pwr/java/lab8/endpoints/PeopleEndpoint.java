package pl.edu.pwr.java.lab8.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.edu.pwr.java.lab8.model.mapper.soap.PersonMapper;
import pl.edu.pwr.java.lab8.service.PersonService;
import pl.pwr.java.lab8.soap.*;

import java.util.stream.Collectors;

@Endpoint
@RequiredArgsConstructor
public class PeopleEndpoint {
    public final static String NAMESPACE_URI = "pl/pwr/java/lab8/soap";

    private final PersonService personService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "fetchPeopleRequest")
    @ResponsePayload
    public FetchPeopleResponse
    fetchPeople(@RequestPayload FetchPeopleRequest request){
        FetchPeopleResponse response = new FetchPeopleResponse();
        response.getPeople().addAll(personService
                .fetchPeople(request.getPage())
                .stream()
                .map(PersonMapper::map)
                .collect(Collectors.toList()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "fetchAllPeopleRequest")
    @ResponsePayload
    public FetchPeopleResponse
    fetchAllPeople(@RequestPayload FetchAllPeopleRequest request){
        FetchPeopleResponse response = new FetchPeopleResponse();
        response.getPeople().addAll(personService
                .fetchAllPeople()
                .stream()
                .map(PersonMapper::map)
                .collect(Collectors.toList()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createPersonRequest")
    @ResponsePayload
    public CreatePersonResponse
    fetchAllPeople(@RequestPayload CreatePersonRequest request){
        CreatePersonResponse response = new CreatePersonResponse();
        response.setPerson(
                PersonMapper.map(
                        personService.createPerson(
                                PersonMapper.map(
                                        request.getPerson()))));
        return response;
    }
}
