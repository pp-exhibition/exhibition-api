package com.pp.entity.member;

import com.pp.entity.base.BaseCreatedEntity;
import com.pp.enums.Gender;
import com.pp.enums.MemberStatus;
import com.pp.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "member_withdrawal")
public class MemberWithdrawal extends BaseCreatedEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "BIGINT UNSIGNED comment 'id'")
	private Long id;

	@Column(name = "email", nullable = false, columnDefinition = "varchar(255) comment '이메일'")
	private String email;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "gender", nullable = false, columnDefinition = "varchar(7) comment '성별'")
	private Gender gender;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, columnDefinition = "varchar(20) comment '회원 상태'")
	private MemberStatus status;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false, columnDefinition = "varchar(20) comment '권한'")
	private Role role;

	@Column(name = "deleted_at", columnDefinition = "datetime comment '탈퇴 일시'")
	private LocalDateTime deletedAt;

	public static MemberWithdrawal of(String email,
									  Gender gender,
									  Role role) {
		return MemberWithdrawal.builder()
				.email(email)
				.gender(gender)
				.status(MemberStatus.WITHDRAWAL)
				.role(role)
				.deletedAt(LocalDateTime.now())
				.build();
	}

}
