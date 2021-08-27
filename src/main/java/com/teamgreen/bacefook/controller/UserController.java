package com.teamgreen.bacefook.controller;

import com.teamgreen.bacefook.entity.Post;
import com.teamgreen.bacefook.entity.User;
import com.teamgreen.bacefook.repository.UserRepository;
import com.teamgreen.bacefook.service.PostService;
import com.teamgreen.bacefook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class UserController {


  /**************** ### Spring Security --> ### ****************/
  @Autowired
  private UserRepository userRepo;
  /**************** ### <-- Spring Security ### ****************/

  @Autowired
  private UserService userService;

  @Autowired
  private PostService postService;

  /**************** ### Home ### ****************/

  @GetMapping("/")
  public String welcome(@ModelAttribute("user") User user, Model model,
                        @CookieValue(value = "currentUser", required = false) String currentUser) {
    if (currentUser != null && currentUser != "") {
      model.addAttribute("user", userService.findUserById(Long.parseLong(currentUser)));
      return "index";
    }
    return "index";
  }

  /**************** ### Authenticate, Sign In and Create Cookie ### ****************/

  @GetMapping("/signin")
  public String signIn() {
    return "signin";
  }

  @PostMapping("/authenticate-user")
  public String authUser(@RequestParam("username") String username,
                         @RequestParam("password") String password,
                         HttpServletResponse response) {

    User user = userService.findUserByUsername(username);

    if (user != null && userService.authUser(username, password)) {
      Long id = user.getId();
      Cookie cookie = new Cookie("currentUser", id.toString());
      cookie.setMaxAge(5000);
      response.addCookie(cookie);
      return "redirect:/profile/" + id;
    }
    return "redirect:/authError";
  }

  @GetMapping("/authError")
  public String authError(Model model) {
    model.addAttribute("msg", "The username and password you entered is incorrect. No Account? Sign Up using the link below.");
    return "signin";
  }

  /**************** ### User Profile based on Cookie and Id Values ### ****************/

  @GetMapping("/profile/{id}")
  public String showProfile(@ModelAttribute("post") Post post, Model model,
//                            @CookieValue("currentUser") String currentUser,
                            @PathVariable long id) {
//    userService.findUserById(id);
    model.addAttribute("posts", postService.findPostByAuthorIdCreatedDate(id));
//    model.addAttribute("user", userService.findUserById(Long.parseLong(currentUser)));
    model.addAttribute("user", userService.findUserById(id));
    return "profile";
  }

  /**************** ### Sign Up and Save User to Database ### ****************/

  @GetMapping("/signup")
  public String signUp(@ModelAttribute("user") User user) {
    return "signup";
  }

  @PostMapping("/save-user")
  public String saveUser(User user,
                         @RequestParam("password") String password,
                         @RequestParam("password_confirm") String password_confirm) {

    if (password.equals(password_confirm)) {
      user.setImg("https://via.placeholder.com/150");
      userService.saveUser(user);
      return "redirect:/success";
    }
    return "redirect:/failed";
  }

  @GetMapping("/success")
  public String success(@ModelAttribute("user") User user) {
    return "redirect:/signin";
  }

  @GetMapping("/failed")
  public String failed(@ModelAttribute("user") User user,
                       Model model) {
    model.addAttribute("msg", "Sign up failed.");
    return "signup";
  }


  /**************** ### Sign Out and Empty Cookie Value ### ****************/

  @GetMapping("/signout")
  public String signOut(HttpServletResponse response) {
    Cookie cookie = new Cookie("currentUser", "");
    cookie.setMaxAge(0);
    response.addCookie(cookie);
    return "redirect:/";
  }

  /**************** ### View All Profiles ### ****************/

    @GetMapping("/profiles")
    public String showProfiles(Model model, User user) {
      List<User> users = userService.findAllUsers();
      model.addAttribute("users", users);
    return "profiles";
  }

  /******************** Admin/Edit ********************/

//  @GetMapping("/admin")
//  public String adminDashboard(Model model, User user) {
//    List<User> users = userService.findAllUsers();
//    model.addAttribute("users", users);
//    return "admin";
//  }

  @GetMapping("/edit/{id}")
  public String editUser(Model model,
                         @PathVariable long id,
                         @CookieValue("currentUser") String currentUser) {
    userService.findUserById(id);
    User thisUser = userService.findUserById(Long.parseLong(currentUser));
    model.addAttribute("currentUser", currentUser);
    model.addAttribute(thisUser);
    return "edit";
  }

  @PostMapping("/update-user")
  public String updateUser(@ModelAttribute User user) {
    userService.updateUser(user);
    Long id = user.getId();
    return "redirect:/profile/" + id;
  }

  @GetMapping("/delete/{id}")
  public String deleteUser(@PathVariable long id) {
    postService.deletePostsByAuthorId(id);
    userService.deleteUser(id);
    return "redirect:/signout";
  }

  /**************** ### Spring Security --> ### ****************/

//  In order to view pages restricted to "currentUser"-Cookies we have to comment the Cookies out
//  from the methods above atm. Same goes for PostController.

//  @GetMapping("/")
//  public String viewHomePage() {
//    return "index";
//  }

  @GetMapping("/register")
  public String showRegistrationForm(Model model) {
    model.addAttribute("user", new User());

    return "signup_form";
  }

  @PostMapping("/process_register")
  public String processRegister(User user) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
    user.setImg("https://via.placeholder.com/150");

    userRepo.save(user);

    return "register_success";
  }

  @GetMapping("/users")
  public String listUsers(Model model) {
    List<User> listUsers = userRepo.findAll();
    model.addAttribute("listUsers", listUsers);

    return "users";
  }

  /**************** <-- ### Spring Security ### ****************/

}
