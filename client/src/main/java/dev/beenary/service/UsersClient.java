package dev.beenary.service;

import generated.GetUserRequest;
import generated.GetUserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;


public class UsersClient extends WebServiceGatewaySupport {
    private static final Logger log = LoggerFactory.getLogger(UsersClient.class);

    public GetUserResponse getUser(String user) {

        // create user request
        GetUserRequest request = new GetUserRequest();
        request.setId(user);

        log.info("Asking for " + user);

        GetUserResponse response = (GetUserResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:7000/ws/users",
                        request,
                        new SoapActionCallback(
                                "http://spring.io/guides/gs-producing-web-service/GetUserRequest"));

        return response;
    }
}
