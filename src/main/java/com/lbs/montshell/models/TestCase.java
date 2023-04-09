package com.lbs.montshell.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class TestCase {

    @Id
    // 문제의 id (key)
    private Long id;

    //test_case 입력
    @Column(name = "input")
    private String input;

    // test_case 입력 답
    @Column(name = "output")
    private String output;
}
