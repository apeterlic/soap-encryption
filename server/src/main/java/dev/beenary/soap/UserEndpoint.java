package dev.beenary.soap;


import dev.beenary.repository.UserRepository;
import dev.beenary.adapter.StringXmlAdapter;
import generated.GetUserRequest;
import generated.GetUserResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * User endpoint
 */
@Endpoint
public class UserEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private final UserRepository userRepository;

    public UserEndpoint(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    @XmlJavaTypeAdapter(StringXmlAdapter.class)
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload @XmlJavaTypeAdapter(StringXmlAdapter.class)
                                           GetUserRequest request) {
        System.out.println("request: " + request);

        GetUserResponse response = new GetUserResponse();
        response.setUser(userRepository.findUser(request.getId()));

        return response;
    }
}

