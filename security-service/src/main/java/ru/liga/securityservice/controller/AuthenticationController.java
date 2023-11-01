package ru.liga.securityservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.securityservice.dto.NewRoleDTO;
import ru.liga.securityservice.dto.RegDTO;
import ru.liga.securityservice.service.UserService;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody RegDTO request) {
        return userService.createUser(request);
    }

    @PostMapping("/setRole")
    public ResponseEntity<String> addNewRole(@RequestBody NewRoleDTO request) {
        return userService.setUserRole(request);
    }
}
