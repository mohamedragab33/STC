package com.example.demo.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Data
public class FileDto {
    private Long id;
    private String name;
    private Long folderId;

}
