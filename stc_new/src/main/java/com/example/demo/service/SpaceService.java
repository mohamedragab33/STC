package com.example.demo.service;

import com.example.demo.data.dtos.SpaceCreateRequest;
import com.example.demo.data.dtos.SpaceDto;
import com.example.demo.data.entities.PermissionGroup;
import com.example.demo.data.entities.Space;
import com.example.demo.data.enums.ItemType;
import com.example.demo.repository.PermissionGroupRepository;
import com.example.demo.repository.SpaceRepository;
import org.springframework.stereotype.Service;

@Service
public class SpaceService {
    private final SpaceRepository spaceRepository;
    private final PermissionGroupRepository permissionGroupRepository;

    public SpaceService(SpaceRepository spaceRepository, PermissionGroupRepository permissionGroupRepository) {
        this.spaceRepository = spaceRepository;
        this.permissionGroupRepository = permissionGroupRepository;
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
