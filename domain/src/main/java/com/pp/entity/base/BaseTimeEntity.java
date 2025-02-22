package com.pp.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    @CreatedDate
    @Column(name = "created_at", updatable = false, columnDefinition = "datetime comment '생성일'")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", columnDefinition = "datetime comment '수정일'")
    private LocalDateTime updatedAt;

}
