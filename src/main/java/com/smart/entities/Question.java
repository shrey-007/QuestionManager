package com.smart.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int qid;
    private String name;
    private String description;


    private String fileName; //fileName is name of the containing the code
    private String imageName;
    @ManyToOne
    @JsonIgnore
    //to avoid circular dependency we have used json ignore.
    private User user;

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question() {
    }

    public Question(String name, String description, String fileName, String imageName, User user) {
        this.name = name;
        this.description = description;
        this.fileName = fileName;
        this.imageName = imageName;
        this.user = user;
    }

    public Question(int qid, String name, String description, String fileName, String imageName, User user) {
        this.qid = qid;
        this.name = name;
        this.description = description;
        this.fileName = fileName;
        this.imageName = imageName;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Question{" +
                "qid=" + qid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", fileName='" + fileName + '\'' +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}
