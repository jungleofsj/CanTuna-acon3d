package CanTuna.CanTunaacon3d.controller;

import CanTuna.CanTunaacon3d.Service.UserService;
import CanTuna.CanTunaacon3d.domain.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/users")
    public User createUser() {
        User user = new User();

        user.setUserName("User1");
        user.setUserType(0L);

        userService.joinUser(user);
        System.out.println(user);

        return user;
    }

    @ResponseBody
    @GetMapping("/users/findall")
    public List<User> list(Model model) {
        List<User> members = userService.findAllUser();
        model.addAttribute("members", members);
        return members;
    }

}
