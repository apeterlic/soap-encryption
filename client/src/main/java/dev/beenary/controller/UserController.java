package dev.beenary.controller;

import dev.beenary.service.UserService;
import generated.GetUserResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/users")
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String openForm(Model model) {
        return "index";
    }

    @PostMapping
    public String generateSoapRequest(@RequestParam("id") String id, Model model) {
        GetUserResponse response = userService.send(id);
        model.addAttribute("user", response.getUser());
        return "index";
    }
}
