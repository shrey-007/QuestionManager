package com.smart.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int qid;
    private String name;
    private String description;
    private String fileName;
    private String imageName;
    @ManyToOne
    private User user;


}
