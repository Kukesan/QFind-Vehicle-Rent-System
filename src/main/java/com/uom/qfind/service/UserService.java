package com.uom.qfind.service;

import com.uom.qfind.dto.UserDTO;
import com.uom.qfind.model.User;
import com.uom.qfind.repository.UserRepository;
import com.uom.qfind.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserDTO createUser(UserDTO userDTO) {
        userDTO.setId(null);
        User user = userRepository.save(userMapper.memberDtoToMember(userDTO));
        return userMapper.memberToMemberDto(user);
    }

    public UserDTO getUserById(String id) {
        return userRepository.findById(id)
                .map(user -> userMapper.memberToMemberDto(user))
                .orElseThrow(ResourceNotFoundException::new);
    }

    public List<UserDTO> getAllUsers() {
        return userMapper.membersToMemberDtos(userRepository.findAll());
    }

    public UserDTO updateUser(String id, UserDTO userDTO) {
        Optional<User> memberOptional = userRepository.findById(id);
        if (!memberOptional.isPresent()) throw new ResourceNotFoundException();

        User user = userMapper.memberDtoToMember(userDTO);
        user.setId(memberOptional.get().getId());
        return userMapper.memberToMemberDto(userRepository.save(user));
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }
}
