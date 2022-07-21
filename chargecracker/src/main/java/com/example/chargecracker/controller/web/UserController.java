package com.example.chargecracker.controller.web;

import com.example.chargecracker.exception.ModelAlreadyExistsException;
import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.User;
import com.example.chargecracker.service.AutoService;
import com.example.chargecracker.service.RoleService;
import com.example.chargecracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AutoService autoService;

    @GetMapping("/admin/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "admin/user/user-list";
    }

    @GetMapping("/user/profile")
    public String getUserProfile(Model model, @AuthenticationPrincipal User currentUser) {
        model.addAttribute("user", userService.getUserProfile(currentUser.getId()));
        return "user/user-profile";
    }

    @PostMapping("/admin/user-delete")
    public String deleteUser(@RequestParam Long id, Model model) {
        try {
            userService.deleteUser(id);
            return "redirect:/admin/users";
        } catch (ModelNotFoundException e) {
            model.addAttribute("exception", e.getMessage());
            return "exception/exception";
        }
    }

    @GetMapping("/registration")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user/user-registration";
    }

    @PostMapping("/registration")
    public String createUser(@ModelAttribute User user, Model model) {
        try {
            userService.createUser(user);
            return "redirect:/login";
        } catch (ModelAttributeException | ModelAlreadyExistsException | ModelNotFoundException e) {
            model.addAttribute("exception", e.getMessage());
            return "user/user-registration";
        }
    }

    @GetMapping("/login-error")
    public String unsuccessfulLogin(Model model) {
        model.addAttribute("exception", "Почта или пароль не правильны");
        return "user/user-login";
    }


    @GetMapping("/admin/user-create")
    public String createUserFormByAdmin(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getRoles());
        model.addAttribute("autos", autoService.getAutos());
        return "admin/user/user-create";
    }

    @PostMapping("/admin/user-create")
    public String createUserByAdmin(@ModelAttribute User user, Model model) {
        try {
            userService.createUserByAdmin(user);
            return "redirect:/admin/users";
        } catch (ModelAttributeException | ModelAlreadyExistsException | ModelNotFoundException e) {
            model.addAttribute("exception", e.getMessage());
            return "admin/user/user-create";
        }
    }

    @GetMapping("/user-update")
    public String updateUserForm(@AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("user", userService.getUser(currentUser.getId()));
        return "user/user-update";
    }

    @PostMapping("/user-update")
    public String updateUser(@ModelAttribute User user, Model model, @AuthenticationPrincipal User currentUser) {
        try {
            userService.updateUser(currentUser.getId(), user);
            return "redirect:/user/profile";
        } catch (ModelAlreadyExistsException | ModelAttributeException | ModelNotFoundException e) {
            model.addAttribute("exception", e.getMessage());
            return "user/user-update";
        }
    }

    @GetMapping("/user-password-update")
    public String updateUserPasswordForm() {
        return "user/user-password-update";
    }

    @PostMapping("/user-password-update")
    public String updateUserPassword(@AuthenticationPrincipal User currentUser,
                                     @RequestParam String oldPassword,
                                     @RequestParam String newPassword,
                                     Model model) {
        try {
            userService.updateUserPassword(currentUser.getId(), oldPassword, newPassword);
            return "redirect:/user/profile";
        } catch (ModelAttributeException | ModelNotFoundException e) {
            model.addAttribute("exception", e.getMessage());
            return "user/user-password-update";
        }
    }

    @GetMapping("/admin/user-update/{id}")
    public String updateUserFormByAdmin(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("roles", roleService.getRoles());
        model.addAttribute("autos", autoService.getAutos());
        return "admin/user/user-update";
    }

    @PostMapping("/admin/user-update")
    public String updateUserByAdmin(@ModelAttribute User user, Model model) {
        try {
            userService.updateUserByAdmin(user);
            return "redirect:/admin/users";
        } catch (ModelAttributeException | ModelNotFoundException | ModelAlreadyExistsException e) {
            model.addAttribute("exception", e.getMessage());
            model.addAttribute("roles", roleService.getRoles());
            model.addAttribute("autos", autoService.getAutos());
            return "admin/user/user-update";
        }
    }
}
