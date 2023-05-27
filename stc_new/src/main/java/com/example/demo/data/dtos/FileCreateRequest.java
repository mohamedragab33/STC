package com.example.demo.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileCreateRequest {
    private MultipartFile file ;
    private String name;
    private String userEmail;
    private String parentName;

}
