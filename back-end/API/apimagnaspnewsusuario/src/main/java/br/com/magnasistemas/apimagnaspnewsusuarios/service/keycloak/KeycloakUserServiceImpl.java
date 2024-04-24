package br.com.magnasistemas.apimagnaspnewsusuarios.service.keycloak;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.apimagnaspnewsusuarios.dto.UserRegistrationRecord;
import br.com.magnasistemas.apimagnaspnewsusuarios.enuns.Role;
import br.com.magnasistemas.apimagnaspnewsusuarios.validacoes.cadastroUsuario.ValidarAcessoRole;
import br.com.magnasistemas.apimagnaspnewsusuarios.validacoes.cadastroUsuario.ValidarCadastroUsuario;
import jakarta.ws.rs.core.Response;

@Service
public class KeycloakUserServiceImpl implements KeycloakUserService{

    @Value("${keycloak.realm}")
    private String realm;
    private Keycloak keycloak;

	@Autowired
	private List<ValidarCadastroUsuario> validadoresCadastro;
	
    public KeycloakUserServiceImpl(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @Override
    public UserRegistrationRecord createUser(UserRegistrationRecord userRegistrationRecord) {

        UserRepresentation user=new UserRepresentation();
        
        List<Role> roles = userRegistrationRecord.roles();
        
        validadoresCadastro.forEach(v -> v.validar(roles));
        
        user.setEnabled(true);
        user.setUsername(userRegistrationRecord.username());
        user.setEmail(userRegistrationRecord.email());
        user.setFirstName(userRegistrationRecord.firstName());
        user.setLastName(userRegistrationRecord.lastName());
        user.setEmailVerified(false);

        CredentialRepresentation credentialRepresentation=new CredentialRepresentation();
        credentialRepresentation.setValue(userRegistrationRecord.password());
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        List<CredentialRepresentation> list = new ArrayList<>();
        list.add(credentialRepresentation);
        user.setCredentials(list);
        
        UsersResource usersResource = getUsersResource();

        Response response = usersResource.create(user);

        if(Objects.equals(201,response.getStatus())){

        	String userId = getCreatedId(response);
        	
        	UserResource userResource = getUserResource(userId);
            RolesResource rolesResource = getRolesResource();
            
            
            if (roles != null && !roles.isEmpty()) {
            for (Role roleName : roles) {
            	 RoleRepresentation representation = rolesResource.get(roleName.name()).toRepresentation();
                 userResource.roles().realmLevel().add(Collections.singletonList(representation));
            }
            }  

            return  userRegistrationRecord;
        }

//        response.readEntity()

        return null;
    }
    
    private String getCreatedId(Response response) {
        String locationHeader = response.getHeaderString("Location");
        if (locationHeader != null) {
            // Extract the user ID from the Location header
            return locationHeader.substring(locationHeader.lastIndexOf('/') + 1);
        }
        throw new RuntimeException("Failed to extract user ID from response");
    }

    
    private UsersResource getUsersResource() {
        RealmResource realm1 = keycloak.realm(realm);
        return realm1.users();
    }

    @Override
    public UserRepresentation getUserById(String userId) {


        return  getUsersResource().get(userId).toRepresentation();
    }

    @Override
    public void deleteUserById(String userId) {

        getUsersResource().delete(userId);
    }


    @Override
    public void emailVerification(String userId){

        UsersResource usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();
    }

    public UserResource getUserResource(String userId){
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId);
    }

    @Override
    public void updatePassword(String userId) {

        UserResource userResource = getUserResource(userId);
        List<String> actions= new ArrayList<>();
        actions.add("UPDATE_PASSWORD");
        userResource.executeActionsEmail(actions);

    }
    
    private RolesResource getRolesResource(){
        return  keycloak.realm(realm).roles();
    }

}
