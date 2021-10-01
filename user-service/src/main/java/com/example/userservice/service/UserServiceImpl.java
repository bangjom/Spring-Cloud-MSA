package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        UserEntity userEntity = UserMapper.INSTANCE.userDtoToUser(userDto);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));
        UserDto saveUser = UserMapper.INSTANCE.entityToUserDto(userRepository.save(userEntity));
        return saveUser;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        Optional<UserEntity> findUser = userRepository.findByUserId(userId);

        if (findUser.isEmpty())
            throw new UsernameNotFoundException("User not found");

        UserDto userDto = UserMapper.INSTANCE.entityToUserDto(findUser.get());
        userDto.setOrders(new ArrayList<>());
        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDto getUserDetailsByEmail(String username) {
        UserEntity userEntity = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("없어잉~"));
        return UserMapper.INSTANCE.entityToUserDto(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("없어!!!!!"));

        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(), true, true, true, true, new ArrayList<>());
    }
}
