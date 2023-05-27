package com.example.demo.controllers;

import com.example.demo.data.dtos.*;
import com.example.demo.service.FilesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/files")
public class FileController {


    private final FilesService fileService;

    public FileController(FilesService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/spaces")
    public ResponseEntity<SpaceDto> createSpace(@RequestBody SpaceCreateRequest request) {
        SpaceDto createdSpace = fileService.createSpace(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSpace);
    }

    @PostMapping("/folders")
    public ResponseEntity<FolderDto> createFolder(@RequestBody FolderCreateRequest request) {
        FolderDto createdFolder = fileService.createFolder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFolder);
    }

    @PostMapping("/files")
    public ResponseEntity<FileDto> createFile(@RequestBody FileCreateRequest request) {
        FileDto createdFile = fileService.createFile(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFile);
    }

}
