package com.example.demo.data.dtos;

import com.example.demo.data.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpaceDto {
    private Long id;
    private String name;
    private ItemType type;
}
