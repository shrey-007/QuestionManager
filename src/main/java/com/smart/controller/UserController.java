package com.smart.controller;

import com.smart.entities.Question;
import com.smart.entities.User;
import com.smart.helper.Message;
import com.smart.model.QuestionRepository;
import com.smart.model.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private QuestionRepository questionRepository;
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
            question.setUser(user);

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


    //{page} is a dynamic variable which denotes ki pagination mai currently konse page pr ho
    @GetMapping("/showQuestions/{page}")
    public String showQuestions(@PathVariable("page") Integer page, Model model,HttpSession session){
        User user=(User) session.getAttribute("currentUser");
        model.addAttribute("user",user);

        //this pageable contains information of current page and no. of questions per page
        Pageable pageable =PageRequest.of(page,3);
        Page<Question> questionList=questionRepository.findQuestionsByUser(user.getId(),pageable);
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(questionList);
        model.addAttribute("questions",questionList);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",questionList.getTotalPages());
        return "showQuestions";
    }

}
