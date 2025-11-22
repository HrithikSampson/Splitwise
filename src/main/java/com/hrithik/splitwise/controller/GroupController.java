package com.hrithik.splitwise.controller;

import com.hrithik.splitwise.dto.GroupDTO;
import com.hrithik.splitwise.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = "*")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupDTO> createGroup(
            @RequestBody GroupDTO groupDTO,
            @RequestParam(required = false) List<Long> memberIds) {
        GroupDTO createdGroup = groupService.createGroup(groupDTO, memberIds);
        return new ResponseEntity<>(createdGroup, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Long id) {
        GroupDTO group = groupService.getGroupById(id);
        return ResponseEntity.ok(group);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GroupDTO>> getUserGroups(@PathVariable Long userId) {
        List<GroupDTO> groups = groupService.getUserGroups(userId);
        return ResponseEntity.ok(groups);
    }

    @GetMapping
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        List<GroupDTO> groups = groupService.getAllGroups();
        return ResponseEntity.ok(groups);
    }

    @PostMapping("/{groupId}/members/{userId}")
    public ResponseEntity<GroupDTO> addUserToGroup(
            @PathVariable Long groupId,
            @PathVariable Long userId) {
        GroupDTO group = groupService.addUserToGroup(groupId, userId);
        return ResponseEntity.ok(group);
    }

    @DeleteMapping("/{groupId}/members/{userId}")
    public ResponseEntity<GroupDTO> removeUserFromGroup(
            @PathVariable Long groupId,
            @PathVariable Long userId) {
        GroupDTO group = groupService.removeUserFromGroup(groupId, userId);
        return ResponseEntity.ok(group);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }
}
