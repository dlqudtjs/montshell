package com.lbs.montshell.controllers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitForm {
    // 제출한 문제의 id 값 (key)
    private Long problem_id;

    // 답안을 제출한 사용자 id(key)
    private String user_id;

    // 사용자가 제출한 코드의 언어
    private String language;

    // 사용자가 제출한 코드
    private String code;
}
