package com.gestion.equiposfutbol.service.user;

import com.gestion.equiposfutbol.dto.request.LoginUserDto;
import com.gestion.equiposfutbol.dto.response.LoginUserResponse;
import org.springframework.web.server.ResponseStatusException;

public interface UsersService {
  LoginUserResponse loginUser(LoginUserDto loginUserDto) throws ResponseStatusException;

  void validateToken(String authToken) throws Exception;

}
