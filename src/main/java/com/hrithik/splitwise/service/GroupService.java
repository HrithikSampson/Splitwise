package com.hrithik.splitwise.service;

import com.hrithik.splitwise.dto.GroupDTO;
import com.hrithik.splitwise.entities.Group;
import com.hrithik.splitwise.entities.User;
import com.hrithik.splitwise.repository.GroupRepository;
import com.hrithik.splitwise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    public GroupDTO createGroup(GroupDTO groupDTO, List<Long> memberIds) {
        Group group = new Group();

        if (memberIds != null && !memberIds.isEmpty()) {
            List<User> members = userRepository.findAllById(memberIds);
            if (members.size() != memberIds.size()) {
                throw new IllegalArgumentException("Some users not found");
            }
            group.getUsers().addAll(members);
        }

        group = groupRepository.save(group);
        return group.toDTO();
    }

    public GroupDTO getGroupById(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Group not found with id: " + id));
        return group.toDTO();
    }

    public List<GroupDTO> getUserGroups(Long userId) {
        return groupRepository.findByUsersId(userId).stream()
                .map(Group::toDTO)
                .collect(Collectors.toList());
    }

    public List<GroupDTO> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(Group::toDTO)
                .collect(Collectors.toList());
    }

    public GroupDTO addUserToGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found with id: " + groupId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        if (group.getUsers().contains(user)) {
            throw new IllegalArgumentException("User already in group");
        }

        group.getUsers().add(user);
        group = groupRepository.save(group);
        return group.toDTO();
    }

    public GroupDTO removeUserFromGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found with id: " + groupId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        if (!group.getUsers().contains(user)) {
            throw new IllegalArgumentException("User not in group");
        }

        group.getUsers().remove(user);
        group = groupRepository.save(group);
        return group.toDTO();
    }

    public void deleteGroup(Long id) {
        if (!groupRepository.existsById(id)) {
            throw new IllegalArgumentException("Group not found with id: " + id);
        }
        groupRepository.deleteById(id);
    }
}
