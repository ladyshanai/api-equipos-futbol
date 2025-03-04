package com.gestion.equiposfutbol.mapper;


import com.gestion.equiposfutbol.dto.response.LoggedUser;
import com.gestion.equiposfutbol.dto.response.LoginUserResponse;
import com.gestion.equiposfutbol.entity.UserEntity;

public class UserMapper {

    public static LoginUserResponse entityToResponse(UserEntity userEntity){
        LoginUserResponse loginUserResponse = new LoginUserResponse();
        LoggedUser username = new LoggedUser();
        username.setUsername(userEntity.getUsername());
        username.setId(userEntity.getId());
        loginUserResponse.setUsername(username);
        return loginUserResponse;
    }
}
