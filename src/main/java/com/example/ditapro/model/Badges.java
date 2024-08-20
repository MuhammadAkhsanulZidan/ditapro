package com.example.ditapro.model;

import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "badges")
public class Badges {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    @UuidGenerator
    private UUID uuid;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String name;

    @OneToOne
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    private Date issueAt;

}
