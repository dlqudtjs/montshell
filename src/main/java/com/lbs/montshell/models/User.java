package com.lbs.montshell.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 사용자 id (Key)
    private Long id;

    // 사용자 아이디 (로그인 할 때 사용)
    private String username;

    // 사용자 패스워드
    private String password;
}
