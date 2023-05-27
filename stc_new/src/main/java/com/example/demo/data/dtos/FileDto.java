package com.example.demo.data.dtos;

public class FileDto {
    private Long id;
    private String name;
    private Long folderId;

    public FileDto() {
    }

    public FileDto(Long id, String name, Long folderId) {
        this.id = id;
        this.name = name;
        this.folderId = folderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }
}
