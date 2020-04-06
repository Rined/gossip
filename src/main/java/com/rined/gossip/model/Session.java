package com.rined.gossip.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "session")
@Table(name = "spring_session")
public class Session {

    @Id
    @Column(name = "primary_id", columnDefinition="bpchar")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "principal_name",
            referencedColumnName = "username"
    )
    private User username;

}
