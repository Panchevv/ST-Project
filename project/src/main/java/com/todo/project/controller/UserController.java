package com.todo.project.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.todo.project.exceptions.UserAuthenticationException;
import com.todo.project.payload.response.UserResponse;
import com.todo.project.persistence.model.User;
import com.todo.project.service.HelperService;
import com.todo.project.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/fetch")
    public ResponseEntity<?> getAllUsers(){
        List<User> users = userService.getAllUsers();
        List<UserResponse> result = new ArrayList<>();

        for(User user : users){
            UserResponse userResponse = new UserResponse();
            userResponse.setFirstName(user.getFirstName());
            userResponse.setLastName(user.getLastName());
            userResponse.setPassword(user.getPassword());
            userResponse.setEmail(user.getEmail());

            result.add(userResponse);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/find/id")
    public ResponseEntity<?> findUserById(Long id){
        User user = userService.findUser(id);
        if(user == null){
            return new ResponseEntity<>("Incorrect id.",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/find/name")
    public ResponseEntity<?> findUserByFullName(String fName, String lName){
        List<User> result = userService.findUser(fName, lName);
        if(result.isEmpty()){
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/find/token")
    public ResponseEntity<?> getUserBySessionToken(@RequestHeader("session-token") String sessionToken){
        User user = userService.findUser(sessionToken);
        if(user == null){
            return new ResponseEntity<>("Incorrect sessionToken",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User newUser){
        if(!userService.checkUser(newUser).equals("")){
            return new ResponseEntity<>(userService.checkUser(newUser), HttpStatus.BAD_REQUEST);
        }
        if(userService.findUserByEmail(newUser.getEmail()) != null){
            return new ResponseEntity<>("Email already in use",HttpStatus.BAD_REQUEST);
        }
        try{
            newUser.setPassword(userService.encode(newUser.getPassword()));
            userService.createUser(newUser);
            return new ResponseEntity<>("Register successful",HttpStatus.OK);
        }
        catch (DataIntegrityViolationException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



    @PutMapping("/login")
    public ResponseEntity<?> login(@RequestBody ObjectNode emailAndPasswordInJson){
        String email = emailAndPasswordInJson.get("email").asText();
        String password = emailAndPasswordInJson.get("password").asText();
        User user;
        try{
            user = userService.authenticateAndReturnUser(email, password);
            user.setSessionToken(HelperService.generateNewToken());
            userService.createUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (UserAuthenticationException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("session-token") String sessionToken){
        User user = userService.findUser(sessionToken);
        if(user == null){
            return new ResponseEntity<>("Incorrect sessionToken", HttpStatus.BAD_REQUEST);
        }
        user.setSessionToken(null);
        userService.createUser(user);
        return new ResponseEntity<>("Logout successful", HttpStatus.OK);
    }
}
