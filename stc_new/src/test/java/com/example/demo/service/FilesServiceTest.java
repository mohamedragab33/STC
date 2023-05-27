package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.data.dtos.FileCreateRequest;
import com.example.demo.data.dtos.FolderCreateRequest;
import com.example.demo.data.dtos.SpaceCreateRequest;
import com.example.demo.data.dtos.SpaceDto;
import com.example.demo.data.entities.Folder;
import com.example.demo.data.entities.Item;
import com.example.demo.data.entities.Permission;
import com.example.demo.data.entities.PermissionGroup;
import com.example.demo.data.entities.Space;
import com.example.demo.data.enums.ItemType;
import com.example.demo.repository.FolderRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.PermissionGroupRepository;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.SpaceRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {FilesService.class})
@ExtendWith(SpringExtension.class)
class FilesServiceTest {
    @Autowired
    private FilesService filesService;

    @MockBean
    private FolderRepository folderRepository;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private PermissionGroupRepository permissionGroupRepository;

    @MockBean
    private PermissionRepository permissionRepository;

    @MockBean
    private SpaceRepository spaceRepository;




    /**
     * Method under test: {@link FilesService#createFile(FileCreateRequest)}
     */
    @Test
    void testCreateFile1() {
        Space space = new Space();
        space.setId(1L);
        space.setName("Name");
        space.setParentItem(new Item());
        space.setPermissionGroup(new PermissionGroup());
        space.setType(ItemType.SPACE);
        Optional<Space> ofResult = Optional.of(space);
        when(spaceRepository.findSpaceByName(Mockito.<String>any())).thenReturn(ofResult);

        Permission permission = new Permission();
        permission.setGroup(new PermissionGroup(1L, "admins"));
        when(permissionRepository.findByUserEmail(Mockito.<String>any())).thenReturn(permission);
        when(itemRepository.save(Mockito.<Item>any())).thenReturn(new Item());
        assertThrows(RuntimeException.class, () -> filesService.createFile(new FileCreateRequest()));
        verify(spaceRepository).findSpaceByName(Mockito.<String>any());
        verify(permissionRepository).findByUserEmail(Mockito.<String>any());
        verify(itemRepository).save(Mockito.<Item>any());
    }

    /**
     * Method under test: {@link FilesService#createFile(FileCreateRequest)}
     */
    @Test
    void testCreateFile2() {
        Space space = new Space();
        space.setId(1L);
        space.setName("Name");
        space.setParentItem(new Item());
        space.setPermissionGroup(new PermissionGroup());
        space.setType(ItemType.SPACE);
        Optional<Space> ofResult = Optional.of(space);
        when(spaceRepository.findSpaceByName(Mockito.<String>any())).thenReturn(ofResult);

        Permission permission = new Permission();
        permission.setGroup(new PermissionGroup(1L, "admins"));
        when(permissionRepository.findByUserEmail(Mockito.<String>any())).thenReturn(permission);
        when(itemRepository.save(Mockito.<Item>any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> filesService.createFile(new FileCreateRequest()));
        verify(spaceRepository).findSpaceByName(Mockito.<String>any());
        verify(permissionRepository).findByUserEmail(Mockito.<String>any());
        verify(itemRepository).save(Mockito.<Item>any());
    }

    /**
     * Method under test: {@link FilesService#createFile(FileCreateRequest)}
     */
    @Test
    void testCreateFile3() {
        when(spaceRepository.findSpaceByName(Mockito.<String>any())).thenReturn(Optional.empty());

        Permission permission = new Permission();
        permission.setGroup(new PermissionGroup(1L, "admins"));
        when(permissionRepository.findByUserEmail(Mockito.<String>any())).thenReturn(permission);
        when(itemRepository.save(Mockito.<Item>any())).thenReturn(new Item());
        assertThrows(RuntimeException.class, () -> filesService.createFile(new FileCreateRequest()));
        verify(spaceRepository).findSpaceByName(Mockito.<String>any());
        verify(permissionRepository).findByUserEmail(Mockito.<String>any());
    }

    /**
     * Method under test: {@link FilesService#createFile(FileCreateRequest)}
     */
    @Test
    void testCreateFile4() {
        Space space = new Space();
        space.setId(1L);
        space.setName("Name");
        space.setParentItem(new Item());
        space.setPermissionGroup(new PermissionGroup());
        space.setType(ItemType.SPACE);
        Optional<Space> ofResult = Optional.of(space);
        when(spaceRepository.findSpaceByName(Mockito.<String>any())).thenReturn(ofResult);
        PermissionGroup group = mock(PermissionGroup.class);
        when(group.getGroup_name()).thenReturn("Group name");

        Permission permission = new Permission();
        permission.setGroup(group);
        when(permissionRepository.findByUserEmail(Mockito.<String>any())).thenReturn(permission);
        when(itemRepository.save(Mockito.<Item>any())).thenReturn(new Item());
        assertThrows(RuntimeException.class, () -> filesService.createFile(new FileCreateRequest()));
        verify(permissionRepository).findByUserEmail(Mockito.<String>any());
        verify(group).getGroup_name();
    }


    /**
     * Method under test: {@link FilesService#save(MultipartFile, Item)}
     */
    @Test
    void testSave1() {
        assertThrows(RuntimeException.class, () -> filesService.save(null, new Item()));
    }

    /**
     * Method under test: {@link FilesService#save(MultipartFile, Item)}
     */
    @Test
    void testSave2() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenThrow(new FileAlreadyExistsException("File"));
        when(file.getOriginalFilename()).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> filesService.save(file, new Item()));
        verify(file).getInputStream();
    }



    /**
     * Method under test: {@link FilesService#createFolder(FolderCreateRequest)}
     */
    @Test
    void testCreateFolder1() {
        Space space = new Space();
        space.setId(1L);
        space.setName("Name");
        space.setParentItem(new Item());
        space.setPermissionGroup(new PermissionGroup());
        space.setType(ItemType.SPACE);
        Optional<Space> ofResult = Optional.of(space);
        when(spaceRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Permission permission = new Permission();
        permission.setGroup(new PermissionGroup(1L, "admins"));
        when(permissionRepository.findByUserEmail(Mockito.<String>any())).thenReturn(permission);
        when(folderRepository.save(Mockito.<Folder>any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> filesService.createFolder(new FolderCreateRequest()));
        verify(spaceRepository).findById(Mockito.<Long>any());
        verify(permissionRepository).findByUserEmail(Mockito.<String>any());
        verify(folderRepository).save(Mockito.<Folder>any());
    }


    /**
     * Method under test: {@link FilesService#createFolder(FolderCreateRequest)}
     */
    @Test
    void testCreateFolder2() {
        when(spaceRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());

        Permission permission = new Permission();
        permission.setGroup(new PermissionGroup(1L, "admins"));
        when(permissionRepository.findByUserEmail(Mockito.<String>any())).thenReturn(permission);
        when(folderRepository.save(Mockito.<Folder>any())).thenReturn(new Folder());
        assertThrows(RuntimeException.class, () -> filesService.createFolder(new FolderCreateRequest()));
        verify(spaceRepository).findById(Mockito.<Long>any());
        verify(permissionRepository).findByUserEmail(Mockito.<String>any());
    }

    /**
     * Method under test: {@link FilesService#createFolder(FolderCreateRequest)}
     */
    @Test
    void testCreateFolder3() {
        Space space = new Space();
        space.setId(1L);
        space.setName("Name");
        space.setParentItem(new Item());
        space.setPermissionGroup(new PermissionGroup());
        space.setType(ItemType.SPACE);
        Optional<Space> ofResult = Optional.of(space);
        when(spaceRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        PermissionGroup group = mock(PermissionGroup.class);
        when(group.getGroup_name()).thenReturn("Group name");

        Permission permission = new Permission();
        permission.setGroup(group);
        when(permissionRepository.findByUserEmail(Mockito.<String>any())).thenReturn(permission);
        when(folderRepository.save(Mockito.<Folder>any())).thenReturn(new Folder());
        assertThrows(RuntimeException.class, () -> filesService.createFolder(new FolderCreateRequest()));
        verify(permissionRepository).findByUserEmail(Mockito.<String>any());
        verify(group).getGroup_name();
    }


    /**
     * Method under test: {@link FilesService#createSpace(SpaceCreateRequest)}
     */
    @Test
    void testCreateSpace1() {
        Space space = new Space();
        space.setId(1L);
        space.setName("Name");
        space.setParentItem(new Item());
        space.setPermissionGroup(new PermissionGroup());
        space.setType(ItemType.SPACE);
        when(spaceRepository.save(Mockito.<Space>any())).thenReturn(space);
        when(permissionGroupRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new PermissionGroup()));
        SpaceDto actualCreateSpaceResult = filesService.createSpace(new SpaceCreateRequest());
        assertNull(actualCreateSpaceResult.getId());
        assertEquals(ItemType.SPACE, actualCreateSpaceResult.getType());
        assertNull(actualCreateSpaceResult.getName());
        verify(spaceRepository).save(Mockito.<Space>any());
        verify(permissionGroupRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link FilesService#createSpace(SpaceCreateRequest)}
     */
    @Test
    void testCreateSpace2() {
        when(spaceRepository.save(Mockito.<Space>any())).thenThrow(new RuntimeException());
        when(permissionGroupRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new PermissionGroup()));
        assertThrows(RuntimeException.class, () -> filesService.createSpace(new SpaceCreateRequest()));
        verify(spaceRepository).save(Mockito.<Space>any());
        verify(permissionGroupRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link FilesService#createSpace(SpaceCreateRequest)}
     */
    @Test
    void testCreateSpace3() {
        Space space = new Space();
        space.setId(1L);
        space.setName("Name");
        space.setParentItem(new Item());
        space.setPermissionGroup(new PermissionGroup());
        space.setType(ItemType.SPACE);
        when(spaceRepository.save(Mockito.<Space>any())).thenReturn(space);
        when(permissionGroupRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> filesService.createSpace(new SpaceCreateRequest()));
        verify(permissionGroupRepository).findById(Mockito.<Long>any());
    }

}

