package com.example.demo.service;

import com.example.demo.data.dtos.FileCreateRequest;
import com.example.demo.data.dtos.FileDto;
import com.example.demo.data.entities.File;
import com.example.demo.data.entities.Item;
import com.example.demo.data.entities.Permission;
import com.example.demo.data.entities.Space;
import com.example.demo.data.enums.ItemType;
import com.example.demo.repository.FileRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class FileService {
    private final Path ROOT_PATH = Paths.get("uploads");
    @Autowired
    private  FileRepository fileRepository;
    @Autowired
    private  SpaceRepository spaceRepository;
    @Autowired
    private  PermissionRepository permissionRepository;
    @Autowired
    private  ItemRepository itemRepository;





    public FileDto createFile(FileCreateRequest request) {
        Item result;
        // Check if the user has EDIT access to the parent folder
        Permission permission = permissionRepository.findByUserEmail(request.getUserEmail());
        if(permission.getGroup().getGroupName().equals("admins")){
            Space parentItem = spaceRepository.findSpaceByName(request.getParentName())
                    .orElseThrow(() -> new RuntimeException("can't find this Space"));
            Item item = new Item();
            item.setType(ItemType.FILE);
            item.setPermissionGroup(permission.getGroup());
            item.setParentItem(parentItem);
            result = itemRepository.save(item);
        }else {
            throw new RuntimeException("not authorized to edit in this space");
        }
       return convertToFileDto(save(request.getFile(),result));

    }
    public File save(MultipartFile file, Item item){
        try {
            Files.copy(file.getInputStream(), this.ROOT_PATH.resolve(Objects.requireNonNull(file.getOriginalFilename())));
            File fileForInsert = new File(1L, file.getBytes(), file.getContentType(),item);
           return fileRepository.save(fileForInsert);
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("file already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }
    }

    private FileDto convertToFileDto(File file) {
        FileDto fileDto = new FileDto();
        fileDto.setId(file.getId());
        fileDto.setName(file.getItem().getName());
        return fileDto;
    }
}
