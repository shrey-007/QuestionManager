package com.smart.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    form ki input fields bhi same name ki banana jo name yaha use kre hai
    private int id;
    @Size(min=3,max=25,message = "Minimum 3 and Maximum 25 characters allowed")
    private String name;
    private  String password;
    @Column(unique = true,nullable = false)
    private String email;

    private String toDo;


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Question> questions=new ArrayList<>();

    private boolean isEnabled=false;
    private boolean emailVerified=false;
    private boolean phoneVerified=false;

    // from where did the user did signup
    // By default sefl hai, means khud se login kra hai
    private Providers provider=Providers.SELF;
    private String providerUserId;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", questions=" + questions +
                '}';
    }
}
