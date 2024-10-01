package com.GreenThumb.Mappers;

import com.GreenThumb.DTO.UserDTO;
import com.GreenThumb.Models.heritage.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO userDTO);

    User partialUpdate(UserDTO userDTO, @MappingTarget User user);

    List<User> toEntity(List<UserDTO> list);

    List<UserDTO> toDto(List<User> list);

    UserDTO toDto(User user);
}
