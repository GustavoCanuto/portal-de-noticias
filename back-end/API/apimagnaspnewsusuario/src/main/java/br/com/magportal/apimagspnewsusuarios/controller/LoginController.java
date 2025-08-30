package br.com.magportal.apimagspnewsusuarios.controller;



import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.magportal.apimagspnewsusuarios.dto.UserDto;
import br.com.magportal.apimagspnewsusuarios.service.LoginService;
import br.com.magportal.apimagspnewsusuarios.service.authGoogle.GoogleAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping("/login")
@RestController
public class LoginController {
	
	 @Autowired
	 private LoginService loginService;
	 
	@PostMapping
	public ResponseEntity<String> token(@RequestBody UserDto user) {
		return loginService.logar(user);	
	}
	
    @Autowired
    private GoogleAuthService authService;

    @GetMapping("/google")
    public void googleAuthRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
       
    	authService.googleAuthRedirect(response);
    }

    @GetMapping("/callback")
    public ResponseEntity<String> googleAuthCallback(@RequestParam("code") String code, @RequestParam("state") String state) {
    	String accessToken = authService.exchangeCodeForAccessToken(code);
        return authService.tokenGoogle(accessToken);
    }
	
}
