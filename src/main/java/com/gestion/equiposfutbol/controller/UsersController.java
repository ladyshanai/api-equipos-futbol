package com.gestion.equiposfutbol.controller;

import com.gestion.equiposfutbol.dto.request.LoginUserDto;
import com.gestion.equiposfutbol.dto.response.LoginUserResponse;
import com.gestion.equiposfutbol.service.user.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/auth")
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<LoginUserResponse> login(@RequestBody LoginUserDto loginUserDto) throws Exception {
        LoginUserResponse response = usersService.loginUser(loginUserDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
