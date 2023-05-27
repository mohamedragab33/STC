package com.example.demo.service;

import com.example.demo.data.dtos.*;
import com.example.demo.data.entities.*;
import com.example.demo.data.enums.ItemType;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
@Service
public class FilesService {
    private final Path ROOT_PATH = Paths.get("uploads");


    private final SpaceRepository spaceRepository;

    private final PermissionRepository permissionRepository;

    private final ItemRepository itemRepository;


    private final PermissionGroupRepository permissionGroupRepository;

    private final FolderRepository folderRepository;
    private final FileRepository fileRepository;



    public FilesService(SpaceRepository spaceRepository, PermissionRepository permissionRepository,
                        ItemRepository itemRepository, PermissionGroupRepository permissionGroupRepository,
                        FolderRepository folderRepository, FileRepository fileRepository) {
        this.spaceRepository = spaceRepository;
        this.permissionRepository = permissionRepository;
        this.itemRepository = itemRepository;
        this.permissionGroupRepository = permissionGroupRepository;
        this.folderRepository = folderRepository;
        this.fileRepository = fileRepository;
    }

    public FileDto createFile(FileCreateRequest request) {
        Item result;
        // Check if the user has EDIT access to the parent folder
        Permission permission = permissionRepository.findByUserEmail(request.getUserEmail());
        if(permission.getGroup().getGroup_name().equals("admins")){
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


    public FolderDto createFolder(FolderCreateRequest request) {
        Permission permission = permissionRepository.findByUserEmail(request.getUserEmail());
        if( permission != null && permission.getGroup().getGroup_name().equals("admins")){
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

    public SpaceDto createSpace(SpaceCreateRequest request) {

        PermissionGroup permissionGroup = permissionGroupRepository.findById(request.getPermissionGroupId()).
                orElseThrow(() -> new RuntimeException("Can't find permission Group"));
        // Create the space entity
        Space space = new Space();
        space.setName(request.getName());
        space.setType(ItemType.SPACE);
        space.setPermissionGroup(permissionGroup);
        // Save the space to the database
        spaceRepository.save(space);
        return convertToSpaceDto(space);
    }
    private SpaceDto convertToSpaceDto(Space space) {
        SpaceDto spaceDto = new SpaceDto();
        spaceDto.setId(space.getId());
        spaceDto.setName(space.getName());
        spaceDto.setType(space.getType());
        return spaceDto;
    }

}
