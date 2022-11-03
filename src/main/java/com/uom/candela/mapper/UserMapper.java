package com.uom.candela.mapper;

import com.uom.candela.dto.UserDTO;
import com.uom.candela.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDTO memberToMemberDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setCity(user.getCity());

        return userDTO;
    }

    public User memberDtoToMember(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setCity(userDTO.getCity());

        return user;
    }

    public List<UserDTO> membersToMemberDtos(List<User> users) {
        return users.stream().map(this::memberToMemberDto).collect(Collectors.toList());
    }
}
