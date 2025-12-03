package com.example;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final List<User> users = new ArrayList<>(Arrays.asList(
            new User(1, "Alice", "alice@example.com"),
            new User(2, "Bob", "bob@example.com")
    ));

    @GetMapping("/users")
    public List<User> getUsers() {
        return users;
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        users.add(user);
        return user;
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        users.removeIf(u -> u.getId() == id);
        updatedUser.setId(id);
        users.add(updatedUser);
        return updatedUser;
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable int id) {
        users.removeIf(u -> u.getId() == id);
        return "User deleted";
    }

    public static class User {
        private int id;
        private String name;
        private String email;

        public User() {}
        public User(int id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        // геттеры и сеттеры
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
}