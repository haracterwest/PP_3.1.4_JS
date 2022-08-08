package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.Exception.UserException;

import java.util.List;

@RequestMapping("/api")
public class RestController {

    private UserService userService;

    public RestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> showAllUsers() {
        List<User> allUsers = userService.findAll();

        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            throw new UserException("There is no user with id = "
                    + id + " in database");
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/users")
    public User addNewUser(@RequestBody User user, @RequestBody String[] setOfRoles) {
        userService.saveUser(user, setOfRoles);
        return user;
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable("id") Long id, @RequestBody User user, @RequestBody String[] setOfRoles) {
        User oldUser = userService.findById(id);
        userService.updateUser(oldUser, user, setOfRoles);
        return user;
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            throw new UserException("There is no user with id = "
                    + id + " in database");
        }

        userService.deleteById(id);
        return "User with id= " + id + " was deleted";
    }
}
