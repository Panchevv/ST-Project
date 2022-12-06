package com.todo.project.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.todo.project.exceptions.UserAuthenticationException;
import com.todo.project.payload.response.UserResponse;
import com.todo.project.persistence.model.User;
import com.todo.project.persistence.repository.TicketRepository;
import com.todo.project.persistence.repository.UserRepository;
import com.todo.project.service.HelperService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepo;
    private final TicketRepository ticketRepo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserController(UserRepository userRepo, TicketRepository ticketRepo){
        this.userRepo = userRepo;
        this.ticketRepo = ticketRepo;
    }

    @GetMapping("/fetch")
    public List<UserResponse> getAllUsers(){
        List<User> users = userRepo.findAll();
        List<UserResponse> result = new ArrayList<>();

        for(User user : users){
            UserResponse userResponse = new UserResponse();
            userResponse.setFirstName(user.getFirstName());
            userResponse.setLastName(user.getLastName());
            userResponse.setPassword(user.getPassword());
            userResponse.setEmail(user.getEmail());

            result.add(userResponse);
        }
        return result;
    }

    @GetMapping("/find/id")
    public ResponseEntity findUserById(Long id){
        User user = userRepo.findUserById(id);
        if(user == null){
            return new ResponseEntity<>("Incorrect id.",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/find/name")
    public ResponseEntity<?> findUserByFullName(String fName, String lName){
        List<User> result = userRepo.findUserByFirstNameAndLastName(fName, lName);
        return ResponseEntity.ok(result.isEmpty()? "Not Found!" : result);
    }

    @GetMapping("/find/token")
    public ResponseEntity getUserBySessionToken(@RequestHeader("session-token") String sessionToken){
        User user = userRepo.findUserBySessionToken(sessionToken);
        if(user == null){
            return new ResponseEntity<>("Incorrect sessionToken",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User newUser){
        if(!checkUser(newUser).equals("")){
            return new ResponseEntity<>(checkUser(newUser), HttpStatus.BAD_REQUEST);
        }
        if(userRepo.findUserByEmail(newUser.getEmail()) != null){
            return new ResponseEntity<>("Email already in use",HttpStatus.BAD_REQUEST);
        }
        try{
            newUser.setPassword(encoder.encode(newUser.getPassword()));
            userRepo.save(newUser);
            return new ResponseEntity<>("Register successful",HttpStatus.OK);
        }
        catch (DataIntegrityViolationException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private String checkUser(User user){
        String message = "";
        if(user.getFirstName() == null || user.getFirstName().equals("")){
            message = "First Name must not be empty.";
        }else if(user.getLastName() == null || user.getLastName().equals("")){
            message = "Last Name must not be empty.";
        }else if(user.getEmail() == null || user.getEmail().equals("")){
            message = "Email must not be empty.";
        }else if(user.getPassword() == null || user.getPassword().equals("")){
            message = "Password must not be empty.";
        }
        return message;
    }

    @PutMapping("/login")
    public ResponseEntity login(@RequestBody ObjectNode emailAndPasswordInJson){
        String email = emailAndPasswordInJson.get("email").asText();
        String password = emailAndPasswordInJson.get("password").asText();
        User user;
        try{
            user = authenticateAndReturnUser(email, password);
            user.setSessionToken(HelperService.generateNewToken());
            userRepo.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (UserAuthenticationException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/logout")
    public ResponseEntity logout(@RequestHeader("session-token") String sessionToken){
        User user = userRepo.findUserBySessionToken(sessionToken);
        if(user == null){
            return new ResponseEntity<>("Incorrect sessionToken", HttpStatus.BAD_REQUEST);
        }
        user.setSessionToken(null);
        userRepo.save(user);
        return new ResponseEntity<>("Logout successful", HttpStatus.OK);
    }

    private User authenticateAndReturnUser(String email, String password){
        User user = userRepo.findUserByEmail(email);
        if(user == null){
            throw new UserAuthenticationException("Wrong email or password.");
        }
        if(encoder.matches(password, user.getPassword())){
            return user;
        }else{
            throw new UserAuthenticationException("Wrong email or password.");
        }
    }
}
