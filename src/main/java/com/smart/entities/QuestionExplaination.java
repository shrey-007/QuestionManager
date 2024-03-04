package com.smart.entities;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QuestionExplaination {

    private int qid;
    private String explaination;

}
