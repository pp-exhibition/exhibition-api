package com.pp.provider;

import com.pp.constants.AuthConstants;
import com.pp.constants.SecurityConstants;
import com.pp.enums.Provider;
import com.pp.exception.CustomException;
import com.pp.exception.ResponseCode;
import com.pp.security.principal.CustomUserDetails;
import com.pp.security.principal.CustomUserDetailsService;
import com.pp.vo.Token;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtProvider {

    private final SecretKey key;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtProvider(
        @Value("${spring.jwt.secret}") String key,
        CustomUserDetailsService customUserDetailsService
    ) {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.customUserDetailsService = customUserDetailsService;
    }

    public Token getTokenByEmail(String email, Provider provider) {
        String accessToken = createAccessToken(email, provider);
        final Date accessExpiredAt = getExpirationByToken(accessToken);
        return Token.of(accessToken, accessExpiredAt);
    }

    public Token createToken(String email, Provider provider) {
        final String accessToken = createAccessToken(email, provider);
        return Token.of(accessToken, getExpirationByToken(accessToken));
    }

    public String createAccessToken(String email, Provider provider) {
        Date now = new Date();
        long accessTokenExpireTime = now.getTime() + SecurityConstants.ACCESS_TOKEN_EXPIRE_TIME;

        return Jwts.builder()
                .subject(email + SecurityConstants.JWT_SUBJECT_SEPARATOR + provider.name())
                .issuedAt(now)
                .expiration(new Date(accessTokenExpireTime))
                .signWith(key)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(getSubjectByAccessToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String resolveToken(String header) {
        return Optional.ofNullable(header)
            .orElseThrow(() -> new CustomException(ResponseCode.INVALID_AUTHENTICATION))
            .replace(AuthConstants.TOKEN_PREFIX, "");
    }

    public boolean isValidateToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }

        return !getExpirationByToken(token).before(new Date());
    }

    public String getSubjectByAccessToken(String accessToken) {
        return parseClaims(accessToken).getSubject();
    }

    public Date getExpirationByToken(String token) {
        return parseClaims(token).getExpiration();
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (MalformedJwtException ex) {
            throw new CustomException(ResponseCode.MALFORMED_JWT_EXCEPTION);
        } catch (ExpiredJwtException ex) {
            throw new CustomException(ResponseCode.EXPIRED_JWT_EXCEPTION);
        } catch (UnsupportedJwtException ex) {
            throw new CustomException(ResponseCode.UNSUPPORTED_JWT_EXCEPTION);
        } catch (IllegalArgumentException ex) {
            throw new CustomException(ResponseCode.ILLEGAL_ARGUMENT_EXCEPTION);
        } catch (Exception e) {
            throw new CustomException(ResponseCode.INVALID_AUTHENTICATION);
        }
    }

}
