package com.eurekamps.user;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/{id}")
    public String getUser(@PathVariable String id) {
        return "User " + id;
    }

    @GetMapping("/edad/{e}")
    public String getEdad(@PathVariable int e) {
        return "Edad " + (e*10);
    }
}