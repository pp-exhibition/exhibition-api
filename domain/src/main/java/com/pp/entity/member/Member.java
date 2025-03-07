package com.pp.entity.member;

import com.pp.entity.base.BaseTimeEntity;
import com.pp.enums.Gender;
import com.pp.enums.Role;
import com.pp.enums.MemberStatus;
import com.pp.enums.Provider;
import com.pp.exception.CustomException;
import com.pp.exception.ResponseCode;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "member")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED comment 'id'")
    private Long id;

    @Column(name = "email", nullable = false, columnDefinition = "varchar(255) comment '이메일'")
    private String email;

    @Column(name = "nickname", nullable = false, columnDefinition = "varchar(20) comment '닉네임'")
    private String nickname;

    @Column(name = "password", columnDefinition = "varchar(255) comment '비밀번호'")
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "gender", nullable = false, columnDefinition = "varchar(7) comment '성별'")
    private Gender gender;

    @Column(name = "phone", columnDefinition = "varchar(20) comment '전화번호'")
    private String phone;

    @Column(name = "profile_image", columnDefinition = "varchar(255) comment '프로필 이미지'")
    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false, columnDefinition = "varchar(20) comment '가입 경로'")
    private Provider provider;

    @Column(name = "provider_id", columnDefinition = "varchar(255) comment 'provider id'")
    private String providerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "varchar(20) comment '회원 상태'")
    private MemberStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, columnDefinition = "varchar(20) comment '권한'")
    private Role role;

    public static Member createEmailMember(String email,
                                           String password,
                                           String nickname,
                                           Gender gender) {
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .provider(Provider.EMAIL)
                .gender(gender)
                .providerId(null)
                .role(Role.USER)
                .status(MemberStatus.ACTIVE)
                .build();
    }

    public static Member createSocialMember(String email,
                                            String password,
                                            String nickname,
                                            Gender gender,
                                            Provider provider,
                                            String providerId) {
        if (provider == Provider.EMAIL || Objects.isNull(providerId) || !Objects.isNull(password)) {
            throw new CustomException(ResponseCode.INVALID_PROVIDER);
        }
        return Member.builder()
                .email(email)
                .password(null)
                .nickname(nickname)
                .gender(gender)
                .provider(provider)
                .providerId(providerId)
                .role(Role.USER)
                .status(MemberStatus.ACTIVE)
                .build();
    }

    public void updateNickname(final String nickname) {
        if (Objects.isNull(nickname) || this.nickname.equals(nickname)) {
            return;
        }
        this.nickname = nickname;
    }

    public void updateProfileImage(final String profileImage) {
        if (Objects.isNull(profileImage)) {
            return;
        }
        this.profileImage = profileImage;
    }

}
