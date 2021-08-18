package com.teamgreen.bacefook.controller;


import com.teamgreen.bacefook.entity.Post;
import com.teamgreen.bacefook.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Controller
public class PostController {

    @Autowired
    private PostService postService;



    @GetMapping("/posts")
    public String allPosts(@ModelAttribute("post") Post post, Model model,
                           @CookieValue(value = "currentUser", required = false) String currentUser) {
        if (currentUser != null) {
            model.addAttribute("posts", postService.findPostsByCreatedDate());

            return "posts";
        }
        model.addAttribute("messege", " Sign In to view");
        return "signin";
    }



//    @PostMapping("/addpost")
//    public String savePost(@ModelAttribute Post post, Model model,
//                           @CookieValue("currentUser") String currentUser) {
//        model.addAttribute("currentUser", currentUser);
//        post.setUserPosted(curUser);
//        LocalDateTime now = LocalDateTime.now();
//        Timestamp timestamp = Timestamp.valueOf(now);
//        post.setTimePosted(timestamp);
//        postService.savePost(post);
//        return "redirect:/posts";
//    }



    @GetMapping("/delete-post/{id}")
    public String deletePost(@PathVariable long id) {
        postService.deletePost(id);
        return "redirect:/posts";
    }

    @GetMapping("/delete-by-author/{id}")
    public String deleteByAuthor(@PathVariable long id) {
        postService.deletePostsByAuthorId(id);
        return "redirect:/posts";
    }  }

