package com.lbs.montshell.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@Entity
public class SubmitInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 제출정보의 id 값 (key)
    private Long id;

    @Column(name = "problem_id")
    // 사용자가 제출한 문제의 id 값
    private Long problemId;

    @Column(name = "user_id")
    // 제출한 사용자의 id
    private String userId;

    private String language;

    private String correct;

    private String user_code;

    private String memory_usage;

    private String execution_time;

    @Temporal(value = TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date submit_time;
}
