package com.pp.repository.jpa;

import com.pp.entity.member.Word;
import com.pp.enums.WordType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordJpaRepository extends JpaRepository<Word, Long> {

    List<Word> findAllByType(WordType type);

}
