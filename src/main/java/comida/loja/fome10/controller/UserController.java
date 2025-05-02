package comida.loja.fome10.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import comida.loja.fome10.model.User;
import comida.loja.fome10.service.UserService;

@RestController
@RequestMapping("/usuario")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll() {return userService.getAll();}

    @PostMapping("/cadastrar")
    public User create(@RequestBody User user) {return userService.save(user);}
}
