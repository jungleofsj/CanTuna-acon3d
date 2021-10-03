package CanTuna.CanTunaacon3d.controller;

import CanTuna.CanTunaacon3d.Service.UserService;
import CanTuna.CanTunaacon3d.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/new")
    public String createForm() {
        return "/createUserForm";
    }

    @PostMapping("/users/new")
    public String createUser(UserForm form) {
        User user = new User();

        user.setUserName(form.getName());
        user.setUserType(form.getType());

        userService.joinUser(user);
        System.out.println(user);

        return "redirect:/";
    }

    /*@GetMapping("/users/login")
    public String loginForm() {
        return "users/login";
    }*/
    @GetMapping("/users/login")
    public String validUser(@RequestParam("username") String userName,@RequestParam("usertype") Long userType ) {
        User user = new User();

        user.setUserName(userName);
        user.setUserType(userType);

        //userService.joinUser(user);
        if (userService.loginUser(user)==0L) {
            return "redirect:/items/new";
        } else if (userService.loginUser(user)==1L) {
            return "redirect:/items/update";
        } else if (userService.loginUser(user)==2L) {
            return "redirect:/items/approved";
        }




        return "redirect:/";
    }

    @ResponseBody
    @GetMapping("/users/findall")
    public List<User> list(Model model) {
        List<User> users = userService.findAllUser();
        model.addAttribute("users", users);
        return users;
    }



}
