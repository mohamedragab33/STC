package com.example.demo.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolderDto {
    private Long id;
    private String name;
    private Long parentId;
}
