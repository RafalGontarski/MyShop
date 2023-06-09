package com.codecool.shop.web;

import com.codecool.shop.dao.implementation.UserDaoJdbc;
import com.codecool.shop.model.User;
import com.codecool.shop.service.MyUserDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class UserController {


    private MyUserDetailsService userService;

    private UserDaoJdbc userDao;

    public UserController(UserDaoJdbc userDao,MyUserDetailsService userService) {
        this.userService = userService;
        this.userDao = userDao;
    }

    @PostMapping("/register")
    public String processRegistrationForm(RedirectAttributes redirectAttributes, @RequestParam("firstName") String firstname,
                                          @RequestParam("lastName") String lastName,
                                          @RequestParam("email") String email,
                                          @RequestParam("address") String address,
                                          @RequestParam("password") String password){

        if(userService.registration(firstname,lastName,email,password,address)){
            redirectAttributes.addFlashAttribute("registrationSucces", "Registration is Succes!");
        }else {
            redirectAttributes.addFlashAttribute("acountExistError", "this account already exist!");
        }
        return "redirect:/";
    }
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
    @GetMapping("/login")
    public String login(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("wrongLoginDataError", "wrong eamil or password!");
        return "redirect:/";
    }
    @GetMapping("/profile")
    public String getProfile(Model model){
        int userId = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
        User user = userDao.getById(userId).get();
        model.addAttribute("user", user);
        return "profile";
    }
}
