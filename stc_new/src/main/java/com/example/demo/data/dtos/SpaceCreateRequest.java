package com.example.demo.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpaceCreateRequest {
    private String name;
    private Long permissionGroupId;

}

