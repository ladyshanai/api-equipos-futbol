package com.gestion.equiposfutbol.service.user;


import com.gestion.equiposfutbol.dto.request.LoginUserDto;
import com.gestion.equiposfutbol.dto.response.LoginUserResponse;
import com.gestion.equiposfutbol.entity.UserEntity;
import com.gestion.equiposfutbol.jwt.TokenService;
import com.gestion.equiposfutbol.mapper.UserMapper;
import com.gestion.equiposfutbol.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;

@Service
public class UsersServiceImpl implements UsersService {

    private static final Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);
    private final UsersRepository usersRepository;

    private final TokenService tokenService;

    public UsersServiceImpl(UsersRepository usersRepository, TokenService tokenService) {
        this.usersRepository = usersRepository;
        this.tokenService = tokenService;
    }

    public LoginUserResponse loginUser(LoginUserDto loginUserDto) throws ResponseStatusException {
        try {
            UserEntity userEntity = usersRepository.findByUsername(loginUserDto.getUsername());
            validateUserAndPassword(loginUserDto.getPassword(), userEntity);
            LoginUserResponse loginUserResponse = UserMapper.entityToResponse(userEntity);
            loginUserResponse.setToken(tokenService.getJwtToken(loginUserResponse.getUsername()));
            return loginUserResponse;
        } catch (Exception e) {
            log.error("loginUser error: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al loguear", e);
        }
    }

    private void validateUserAndPassword(String pass, UserEntity userEntity) throws Exception {
        String errorMessage = "Invalid User/Password";
        if(userEntity == null)
            throw new Exception(errorMessage);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12, new SecureRandom());
        if(userEntity.getPassword() != null && !bCryptPasswordEncoder.matches(pass, userEntity.getPassword()))
            throw new Exception(errorMessage);
    }

    public void validateToken(String authToken) throws Exception {
        try {
            tokenService.getUserFromToken(authToken);
        } catch (Exception e) {
            log.error("validateToken error: {}", e.getMessage());
            throw new Exception("Token invalido");
        }
    }

}
