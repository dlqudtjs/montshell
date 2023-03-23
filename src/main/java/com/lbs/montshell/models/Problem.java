package com.lbs.montshell.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Problem {
    private Long id;
    private String title;
    private String description;
    private String input_description;
    private String output_description;
    private String test_case;
    private String created_at;
    private String updated_at;
}
