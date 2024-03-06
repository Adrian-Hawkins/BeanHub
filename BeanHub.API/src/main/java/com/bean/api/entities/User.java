package com.bean.api.classes;

import jakarta.persistence.*;

@Entity
@Table("User")
public class User {
    @OneToMany()
    private String name;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long User_ID;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
