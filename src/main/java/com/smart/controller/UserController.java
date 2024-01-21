package com.smart.controller;

import com.smart.entities.Question;
import com.smart.entities.User;
import com.smart.helper.Message;
import com.smart.model.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
    public String saveQuestion(Model model, HttpSession session, @ModelAttribute Question question, BindingResult bindingResult, @RequestParam("fileName")MultipartFile file,@RequestParam("imageName")MultipartFile image){
        try{
            //get user
            User user=(User) session.getAttribute("currentUser");
            model.addAttribute("user",user);

            //uploading file
            if(!file.isEmpty()){
                question.setFileName(file.getOriginalFilename());
                File savedFile=new ClassPathResource("static/files").getFile();
                Path path=Paths.get(savedFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
                Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
            }
            //uploading image
            if(!image.isEmpty()){
                question.setImageName(image.getOriginalFilename());
                File savedFile=new ClassPathResource("static/images").getFile();
                Path path=Paths.get(savedFile.getAbsolutePath()+File.separator+image.getOriginalFilename());
                Files.copy(image.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
            }

            //add question to user's list
            user.getQuestions().add(question);
            //update user in database
            userRepository.save(user);
            //we can do the same by making QuestionRepository
            session.setAttribute("message",new Message("Added Successfully","success"));
            return "addQuestion";
        }
        catch (Exception e){
            model.addAttribute("message",new Message("Error Adding Question","danger"));
            System.out.println(e.getMessage());
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
        return "addQuestion";

    }

}
