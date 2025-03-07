package com.pp.repository;

import com.pp.entity.member.Word;
import com.pp.enums.WordType;
import com.pp.repository.jpa.WordJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WordRepository {

    private final WordJpaRepository wordJpaRepository;

    public List<Word> getAllAdjectiveWord() {
        return wordJpaRepository.findAllByType(WordType.ADJECTIVE);
    }

    public List<Word> getAllNounWord() {
        return wordJpaRepository.findAllByType(WordType.NOUN);
    }

    public List<Word> getAll() {
        return wordJpaRepository.findAll();
    }

}
