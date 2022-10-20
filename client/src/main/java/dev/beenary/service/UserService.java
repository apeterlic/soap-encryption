package dev.beenary.service;

import generated.GetUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static dev.beenary.common.Preconditions.checkNotNull;

@Slf4j
@Service
public class UserService {

    private final UsersClient userClient;

    public UserService(UsersClient userClient) {
        this.userClient = checkNotNull(userClient, "User client is null");
    }

    public GetUserResponse send(final String id) {
        GetUserResponse response = userClient.getUser(id);
        checkNotNull(response, "Response is null");
        log.info("Name: {}, Id: {}, Allowed: {}", response.getUser().getNombre(), response.getUser().getId(), response.getUser().getPermitido());
        return response;
    }
}
