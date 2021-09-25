package com.example.userservice.mapper;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    final static UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity userDtoToUser(UserDto userDto);

    UserDto requestUserToUserDto(RequestUser user);

    ResponseUser userDtoToResponseUser(UserDto userDto);

    ResponseUser entityToResponseUser(UserEntity userEntity);

    UserDto entityToUserDto(UserEntity userEntity);
}
