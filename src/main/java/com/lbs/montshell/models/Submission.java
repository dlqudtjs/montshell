package com.lbs.montshell.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Submission {
    // 제출한 문제의 id 값 (key)
    private String problem_id;

    // 답안을 제출한 사용자 id(key)
    private String user_id;
    
    // 사용자가 제출한 코드의 언어
    private String language;
    
    // 사용자가 제출한 코드
    private String code;
    
    // 제출한 날짜, 시간
    private String submitted_at;
    
    // 제출한 코드 정답 여부
    private boolean is_correct;

    // 정답이 아닐 경우 에러 메시지
    private String error_message;
}
