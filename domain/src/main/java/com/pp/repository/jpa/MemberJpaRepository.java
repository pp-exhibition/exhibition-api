package com.pp.repository.jpa;

import com.pp.entity.member.Member;
import com.pp.enums.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmailAndProvider(String email, Provider provider);

}
