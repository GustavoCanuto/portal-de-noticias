package br.com.magnasistemas.apimagnaspnewsusuarios.dto;

import java.util.List;

public record UserRegistrationRecord 
(String username, String email, String firstName, String lastName, String password,  List<String> roles){

}