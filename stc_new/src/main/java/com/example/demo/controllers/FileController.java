package com.example.demo.controllers;

import com.example.demo.data.dtos.*;
import com.example.demo.service.FileService;
import com.example.demo.service.FolderService;
import com.example.demo.service.SpaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;
    private final SpaceService spaceService;
    private final FolderService folderService;

    public FileController(FileService fileService, SpaceService spaceService, FolderService folderService) {
        this.fileService = fileService;
        this.spaceService = spaceService;
        this.folderService = folderService;
    }

    @PostMapping("/spaces")
    public ResponseEntity<SpaceDto> createSpace(@RequestBody SpaceCreateRequest request) {
        SpaceDto createdSpace = spaceService.createSpace(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSpace);
    }

    @PostMapping("/folders")
    public ResponseEntity<FolderDto> createFolder(@RequestBody FolderCreateRequest request) {
        FolderDto createdFolder = folderService.createFolder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFolder);
    }

    @PostMapping("/files")
    public ResponseEntity<FileDto> createFile(@RequestBody FileCreateRequest request) {
        FileDto createdFile = fileService.createFile(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFile);
    }

}
