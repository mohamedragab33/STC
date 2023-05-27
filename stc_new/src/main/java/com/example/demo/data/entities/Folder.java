package com.example.demo.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "folders")
public class Folder extends Item {

}
