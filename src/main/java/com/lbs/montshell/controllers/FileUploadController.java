package com.lbs.montshell.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class FileUploadController {
    @PostMapping("/fileUpload")
    public String uploadFile(@RequestPart("file")MultipartFile file) {
        if (file.isEmpty()) {
            return "파일이 없습니다.";
        }

        try {
            // 파일 저장 경로 설정
            String uploadDir = "C:/Users/dlqud/OneDrive/바탕 화면/학교 공유 파일/3-1/Start-Up Project/montshell/temp/";
            File destFile = new File(uploadDir + file.getOriginalFilename());

            // 파일 저장
            file.transferTo(destFile);

            // 요청 처리 결과 반환
            return "File uploaded successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "File upload failed";
        }
    }
}
