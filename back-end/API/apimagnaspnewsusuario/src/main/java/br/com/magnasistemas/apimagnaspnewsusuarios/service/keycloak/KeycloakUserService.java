package br.com.magnasistemas.apimagnaspnewsusuarios.service.keycloak;


import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;

import br.com.magnasistemas.apimagnaspnewsusuarios.dto.UserRegistrationRecord;

public interface KeycloakUserService {

    UserRegistrationRecord createUser(UserRegistrationRecord userRegistrationRecord);
    UserRepresentation getUserById(String userId);
    void deleteUserById(String userId);
    void emailVerification(String userId);
    UserResource getUserResource(String userId);
    void updatePassword(String userId);
}

