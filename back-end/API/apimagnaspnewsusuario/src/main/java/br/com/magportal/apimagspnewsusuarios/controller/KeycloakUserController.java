package br.com.magportal.apimagspnewsusuarios.controller;

import java.security.Principal;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.magportal.apimagspnewsusuarios.dto.UserRegistrationRecord;
import br.com.magportal.apimagspnewsusuarios.service.keycloak.KeycloakUserService;

@RestController
@RequestMapping("/users")
public class KeycloakUserController {
	
	private final KeycloakUserService keycloakUserService;

    public KeycloakUserController(KeycloakUserService keycloakUserService) {
		this.keycloakUserService = keycloakUserService;
	}
    
	@PostMapping
    public UserRegistrationRecord createUser(@RequestBody UserRegistrationRecord userRegistrationRecord) {
        return keycloakUserService.createUser(userRegistrationRecord);
    }

    @GetMapping
    public UserRepresentation getUser(Principal principal) {

        return keycloakUserService.getUserById(principal.getName());
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable String userId) {
        keycloakUserService.deleteUserById(userId);
    }

    @PutMapping("/{userId}/send-verify-email")
    public void sendVerificationEmail(@PathVariable String userId) {
        keycloakUserService.emailVerification(userId);
    }
    @PutMapping("/update-password")
    public void updatePassword(Principal principal) {
        keycloakUserService.updatePassword(principal.getName());
    }


}
