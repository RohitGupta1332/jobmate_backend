package com.jobmate.jobmate.model.profile;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String fileName;
    @Column(nullable = false)
    private String fileUrl;
    @Column(nullable = false)
    private String publicId;

}
