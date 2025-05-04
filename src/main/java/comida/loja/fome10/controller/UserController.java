package comida.loja.fome10.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import comida.loja.fome10.dto.UserPatchDto;
import comida.loja.fome10.dto.UserTokenDto;
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

    @PostMapping
    public UserTokenDto create(@RequestBody User user) {return userService.save(user);}

    @DeleteMapping
    public void delete(@RequestBody Integer id) {userService.delete(id);};

    @PatchMapping("/{id}")
    public User update(@RequestBody UserPatchDto user, @PathVariable Integer id) {return userService.update(id, user);}
}
