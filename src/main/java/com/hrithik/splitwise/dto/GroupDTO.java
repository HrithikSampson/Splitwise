package com.hrithik.splitwise.dto;

import com.hrithik.splitwise.entities.Group;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupDTO {
    private Long id;
    private String groupName;
    private String groupDescription;

    public GroupDTO(Group group) {
        this.id = group.getId();
        this.groupName = group.getGroup_name();
        this.groupDescription = group.getGroup_description();
    }
}
