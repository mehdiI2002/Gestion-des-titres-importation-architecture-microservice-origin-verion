package com.example.security_service.service;

import com.example.security_service.dto.UpdateUserDTO;
import com.example.security_service.dto.UpdateUserWithPasswordDTO;
import com.example.security_service.dto.UserDTO;
import com.example.security_service.models.AddUserRequest;
import com.example.security_service.user.User;

import java.util.List;

public interface UserService {
    public List<String> getAllAdmins();
    public  List<UserDTO> getAllUsers();
    public void deleteUserByEmail(String email);
    public void addUser(AddUserRequest request);
public void updateUser(String email,UpdateUserDTO updateUserDTO);
public void updateUserPassword(String email, UpdateUserWithPasswordDTO updateUserWithPasswordDTO);
}
