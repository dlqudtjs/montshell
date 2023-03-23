package com.lbs.montshell.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Submission {
    private String problem_id;
    private String user_id;
    private String language;
    private String code;
    private String submitted_at;
    private boolean is_correct;
    private String error_message;
}
