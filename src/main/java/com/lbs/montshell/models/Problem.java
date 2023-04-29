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
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 문제의 id (key)
    private Long id;
    
    // 문제의 제목
    private String title;
    
    // 작성자
    private String author;

    // 문제 설명
    private String description;

    // 문제 입력 설명 (요구하는 입력)
    private String input_description;
    
    // 문제 출력 설명 (요구하는 출력)
    private String output_description;

    private int difficulty;

    // 문제 생성 날짜
    private String created_at;
    
    // 문제 업데이트 날짜
    private String updated_at;
}
