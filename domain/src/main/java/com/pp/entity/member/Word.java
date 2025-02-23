package com.pp.entity.member;

import com.pp.enums.WordType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "word")
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED comment '단어 고유 번호'")
    private Long id;

    @Column(name = "word", nullable = false, columnDefinition = "varchar(10) comment '단어'")
    private String word;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, columnDefinition = "varchar(10) comment '단어 구분'")
    private WordType type;

}

