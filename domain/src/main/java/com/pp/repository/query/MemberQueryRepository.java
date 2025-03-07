package com.pp.repository.query;

import com.pp.entity.member.QMember;
import com.pp.entity.member.QMemberWithdrawal;
import com.pp.enums.Provider;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    QMember member = QMember.member;
    QMemberWithdrawal memberWithdrawal = QMemberWithdrawal.memberWithdrawal;

    public boolean existsByEmailAndProvider(String email, Provider provider) {
        return jpaQueryFactory
                .selectOne()
                .from(member)
                .where(
                        eqEmail(member, email),
                        eqProvider(member, provider)
                )
                .fetchFirst() != null;
    }

    public boolean existsByNickname(String nickname) {
        return jpaQueryFactory
                .selectOne()
                .from(member)
                .where(
                        eqNickname(member, nickname)
                )
                .fetchFirst() != null;
    }

    private BooleanExpression eqEmail(QMember member, String email) {
        return member.email.eq(email);
    }

    private BooleanExpression eqNickname(QMember member, String nickname) {
        return member.nickname.eq(nickname);
    }

    private BooleanExpression eqProvider(QMember member, Provider provider) {
        return member.provider.eq(provider);
    }

}
