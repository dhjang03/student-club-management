package com.studentclub.studentclubbackend.models.embeddables;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubMembershipId implements Serializable {

    private Long clubId;
    private Long userId;
}
