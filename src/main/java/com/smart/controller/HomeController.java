package com.smart.controller;

import com.smart.entities.User;
import com.smart.helper.Message;
import com.smart.model.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String home(Model model){
        model.addAttribute("title","Question Manager-home");
        return "home";
    }

    @RequestMapping("/about")
    public String about(Model model){
        model.addAttribute("title","Question Manager-About");
        return "about";
    }

    @RequestMapping("/signup")
    public String signup(Model model){
        model.addAttribute("title","Question Manager-Signup");
        //yaha se blank fields form mai jaaegi and jab apan form mai kuch daalege toh voh fields idhar aa jaaegi.
        //ye map kr rha hai form ko controller se.
        //isse hoga ye ki agar koi error aayi validation mai user ko vaapis se saare input nhi dene padega
        //vaha har input par th:value likha hai.
        model.addAttribute("user",new User());
        return "signup";
    }

    @PostMapping("/do_register")
    //since form mai apan ne th:object="user" liya hai isliye yaha ModelAttribute("user") liya
    //form mai jitni fields hai vo saari User class se map ho jaaegi by ModelAttribute coz name same hai dono jagah
    //checkbox is not property of user toh usko alag se request param mai liya hai.
    //and yaha se data vaapis form mai bhejne ke liye model object liya hai
    public String register(@Valid @ModelAttribute("user") User user,BindingResult bindingResult, @RequestParam(value = "agreement",defaultValue = "false") boolean agreement, Model model, HttpSession session){
       try{
           if(!agreement){
               throw new Exception("You have not agreed terms and conditions");
           }
           if(bindingResult.hasErrors()){
               model.addAttribute("user",user);
               return "signup";
           }
           User result=this.userRepository.save(user);
           //jo user aaye usi ko vaapis bhej diya
           model.addAttribute("user",new User());
           session.setAttribute("message",new Message("Successfully Registered","alert-success"));
       }
       catch (Exception e){
          e.printStackTrace();
          model.addAttribute("user",user);
          session.setAttribute("message",new Message("something went wrong"+e.getMessage(),"alert-danger"));
       }
        return "signup";
    }

}
