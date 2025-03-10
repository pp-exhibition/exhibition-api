package com.pp.repository;

import com.pp.entity.member.Member;
import com.pp.enums.Provider;
import com.pp.exception.CustomException;
import com.pp.exception.ResponseCode;
import com.pp.repository.jpa.MemberJpaRepository;
import com.pp.repository.query.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberQueryRepository memberQueryRepository;

    public Member getEmailAndProvider(String email, Provider provider) {
        return memberJpaRepository.findByEmailAndProvider(email, provider)
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_MEMBER));
    }

    public boolean existsByNickname(String nickname) {
        return memberQueryRepository.existsByNickname(nickname);
    }

    public boolean existsByEmailAndProvider(String email, Provider provider) {
        return memberQueryRepository.existsByEmailAndProvider(email, provider);
    }

    public Member save(Member member) {
        return memberJpaRepository.save(member);
    }

}
