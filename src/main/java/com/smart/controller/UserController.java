package com.smart.controller;

import com.smart.entities.Question;
import com.smart.entities.User;
import com.smart.model.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @RequestMapping("/addQuestion")
    public String addQuestion(Model model, HttpSession session){
        //get current user
        User user=(User) session.getAttribute("currentUser");

        model.addAttribute("title","Add Question");
        //send user to view
        model.addAttribute("user",user);
        //add a blank object
        model.addAttribute("question",new Question());
        return "addQuestion";
    }

    @PostMapping("/saveQuestion")
    public String saveQuestion(Model model,HttpSession session, @ModelAttribute Question question, BindingResult bindingResult){
        //get user
        User user=(User) session.getAttribute("currentUser");
        model.addAttribute("user",user);

        //add question to user's list
        user.getQuestions().add(question);
        //update user in database
        userRepository.save(user);
        //we can do the same by making QuestionRepository
        System.out.println(question+"==================================================================================================================================");
        return "dashboard";
    }

}
