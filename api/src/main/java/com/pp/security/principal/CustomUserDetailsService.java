package com.pp.security.principal;

import com.pp.constants.SecurityConstants;
import com.pp.entity.member.Member;
import com.pp.enums.Provider;
import com.pp.exception.CustomException;
import com.pp.exception.ResponseCode;
import com.pp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String subject) throws UsernameNotFoundException {
        String[] subjects = subject.split(SecurityConstants.JWT_SUBJECT_SEPARATOR);
        String email = subjects[0];
        Provider provider = Provider.valueOf(subjects[1].toUpperCase());
        Member member = memberRepository.getEmailAndProvider(email, provider);

        CustomUserDetails userDetails = CustomUserDetails.of(member);
        validateAuthenticate(userDetails);
        return userDetails;
    }

    private void validateAuthenticate(CustomUserDetails member) {
        if (Objects.isNull(member)) {
            throw new CustomException(ResponseCode.INTERNAL_AUTHENTICATION_SERVICE);
        }
        validateEnabled(member);
        validateAccountExpired(member);
        validateAccountNonLocked(member);
        validateCredentialNonExpired(member);
    }

    private static void validateEnabled(CustomUserDetails member) {
        if(!member.isEnabled()){
            throw new CustomException(ResponseCode.DISABLE_ACCOUNT);
        }
    }

    private static void validateCredentialNonExpired(CustomUserDetails member) {
        if (!member.isCredentialsNonExpired()) {
            throw new CustomException(ResponseCode.NON_EXPIRED_ACCOUNT);
        }
    }

    private static void validateAccountNonLocked(CustomUserDetails member) {
        if (!member.isAccountNonLocked()) {
            throw new CustomException(ResponseCode.NON_EXPIRED_ACCOUNT);
        }
    }

    private static void validateAccountExpired(CustomUserDetails member) {
        if (!member.isAccountNonExpired()) {
            throw new CustomException(ResponseCode.NON_EXPIRED_ACCOUNT);
        }
    }

}
