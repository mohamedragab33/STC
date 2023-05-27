package com.example.demo.service;

import com.example.demo.data.dtos.FolderCreateRequest;
import com.example.demo.data.dtos.FolderDto;
import com.example.demo.data.entities.Folder;
import com.example.demo.data.entities.Permission;
import com.example.demo.data.entities.Space;
import com.example.demo.data.enums.ItemType;
import com.example.demo.repository.FolderRepository;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.SpaceRepository;
import org.springframework.stereotype.Service;

@Service
public class FolderService {

    private final FolderRepository folderRepository;
    private final SpaceRepository spaceRepository;

    private final PermissionRepository permissionRepository ;

    public FolderService(FolderRepository folderRepository, SpaceRepository spaceRepository, PermissionRepository permissionRepository) {
        this.folderRepository = folderRepository;
        this.spaceRepository = spaceRepository;
        this.permissionRepository = permissionRepository;
    }

    public FolderDto createFolder(FolderCreateRequest request) {
        Permission permission = permissionRepository.findByUserEmail(request.getUserEmail());
        if( permission != null && permission.getGroup().getGroupName().equals("admins")){
            Space parentItem = spaceRepository.findById(request.getParentId()).orElseThrow(() -> new RuntimeException("Space not found"));
            Folder folder = new Folder();
            folder.setName(request.getName());
            folder.setType(ItemType.FOLDER);
            folder.setPermissionGroup(permission.getGroup());
            folder.setParentItem(parentItem);
            Folder result =  folderRepository.save(folder);
            return convertToFolderDto(result);
        }else {
            throw new RuntimeException("You can't make changes in this space");
        }
    }
    private FolderDto convertToFolderDto(Folder folder) {
        FolderDto folderDto = new FolderDto();
        folderDto.setId(folder.getId());
        folderDto.setName(folder.getName());
        folderDto.setParentId(folder.getParentItem().getId());
        return folderDto;
    }

}
