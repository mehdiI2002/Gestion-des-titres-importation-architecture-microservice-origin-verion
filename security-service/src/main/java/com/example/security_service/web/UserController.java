package com.example.security_service.web;

import com.example.security_service.Repository.UserRepository;
import com.example.security_service.dto.UpdateUserDTO;
import com.example.security_service.dto.UpdateUserWithPasswordDTO;
import com.example.security_service.dto.UserDTO;
import com.example.security_service.models.AddUserRequest;
import com.example.security_service.models.DeleteRequest;
import com.example.security_service.service.UserService;
import com.example.security_service.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/user")
public class UserController {
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/authorities")
    public List<User> getAllUsersWithAutorithies() {
        return userRepository.findAll();
    }

    @PostMapping("/deleteUser")
    public void deleteUserByEmail(@RequestBody DeleteRequest request) {
        userService.deleteUserByEmail(request.getEmail());
    }

    @PostMapping("/createUser")
    public void createUser(@RequestBody AddUserRequest request) {
        userService.addUser(request);
    }

    @PostMapping("/updateUser")
    public void updateUser(@RequestParam String email, @RequestBody UpdateUserDTO updateUserDTO) {
        userService.updateUser(email, updateUserDTO);
    }
    @PostMapping("/updateUserPassword")
    public void updateUserPassword(@RequestParam String email, @RequestBody UpdateUserWithPasswordDTO updateUserWithPasswordDTO){
        userService.updateUserPassword(email, updateUserWithPasswordDTO);
    }


}
