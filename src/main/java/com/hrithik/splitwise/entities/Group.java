package com.hrithik.splitwise.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "splitwise_main")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String group_name;

    @Column(nullable = true)
    private String group_description;

    @ManyToMany(mappedBy = "groups")
    private List<User> users = new ArrayList<>();

    public com.hrithik.splitwise.dto.GroupDTO toDTO() {
        return new com.hrithik.splitwise.dto.GroupDTO(this);
    }
}
