package com.church.app.ministry;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ministries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ministry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @Column(nullable = false)
    private String name;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
