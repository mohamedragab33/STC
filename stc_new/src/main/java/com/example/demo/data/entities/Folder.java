package com.example.demo.data.entities;

import com.example.demo.data.enums.ItemType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "folders")
public class Folder{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private ItemType type;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "permission_group_id", nullable = false)
    private PermissionGroup permissionGroup;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "ID")
    private Item parentItem;

}
