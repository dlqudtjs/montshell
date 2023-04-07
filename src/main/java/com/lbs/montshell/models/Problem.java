package com.lbs.montshell.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Problem {
    // 문제의 id (key)
    private Long id;
    
    // 문제의 제목
    private String title;
    
    // 문제 설명
    private String description;
    
    // 문제 입력 설명 (요구하는 입력)
    private String input_description;
    
    // 문제 출력 설명 (요구하는 출력)
    private String output_description;

    private int difficulty;
    
    // 테스트 케이스 (여러개의 입력과 출력 쌍)
//    private List<TestCase> test_case;
    
    // 문제 생성 날짜
    private String created_at;
    
    // 문제 업데이트 날짜
    private String updated_at;
}
