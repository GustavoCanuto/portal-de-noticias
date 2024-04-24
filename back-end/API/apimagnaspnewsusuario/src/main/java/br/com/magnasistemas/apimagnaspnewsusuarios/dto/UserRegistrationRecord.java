package br.com.magnasistemas.apimagnaspnewsusuarios.dto;

import java.util.List;

import br.com.magnasistemas.apimagnaspnewsusuarios.enuns.Role;

public record UserRegistrationRecord 
(String username, String email, String firstName, String lastName, String password,  List<Role> roles){

}
