package com.pp.service.member;

import com.pp.common.ServiceTest;
import com.pp.dto.request.SignUpRequest;
import com.pp.dto.response.SignUpResponse;
import com.pp.entity.member.Member;
import com.pp.entity.member.Word;
import com.pp.enums.Gender;
import com.pp.enums.Provider;
import com.pp.enums.Role;
import com.pp.enums.WordType;
import com.pp.provider.JwtProvider;
import com.pp.repository.MemberRepository;
import com.pp.repository.WordRepository;
import com.pp.vo.Token;
import net.jqwik.api.Arbitraries;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@DisplayName("회원 인증 인가 비즈니스 로직 테스트")
class AuthServiceTest extends ServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private WordRepository wordRepository;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("이메일 회원가입에 성공한다.")
    void 회원가입_성공() {
        // given
        final List<Word> words = IntStream.range(0, 20)
                .mapToObj(i -> getBuilderMonkey().giveMeBuilder(Word.class)
                        .set("word", Arbitraries.strings().alpha().ofMinLength(4).ofMaxLength(8))
                        .set("type", i < 10 ? WordType.ADJECTIVE : WordType.NOUN)
                        .sample())
                .toList();

        final Provider provider = Provider.EMAIL;
        final String email = Arbitraries.strings().alpha().ofMinLength(4).ofMaxLength(8).map(param -> param + "@gmail.com").sample();
        final String password = Arbitraries.strings()
                .withChars(charArray)
                .ofMinLength(7)
                .ofMaxLength(15)
                .map(p -> p + "0")
                .filter(p -> p.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?!.*\\s).+$"))
                .sample();
        final String encodedPassword = passwordEncoder.encode(password);

        final SignUpRequest request = getConstructorMonkey().giveMeBuilder(SignUpRequest.class)
                .set("email", email)
                .set("password", password)
                .set("confirmPassword", password)
                .sample();

        final Member member = getConstructorMonkey().giveMeBuilder(Member.class)
                .set("id", Arbitraries.longs().greaterOrEqual(12345))
                .set("email", email)
                .set("password", encodedPassword)
                .set("gender", Arbitraries.of(Gender.class).sample())
                .set("role", Role.USER)
                .set("provider", Provider.EMAIL)
                .sample();

        final Token token = getConstructorMonkey().giveMeBuilder(Token.class)
                .setNotNull("accessToken")
                .setNotNull("accessExpiredAt")
                .sample();

        // when
        doReturn(false).when(memberRepository).existsByEmailAndProvider(email, provider);
        doReturn(encodedPassword).when(passwordEncoder).encode(request.password());
        doReturn(words).when(wordRepository).getAll();
        doReturn(false).when(memberRepository).existsByNickname(anyString());
        doReturn(member).when(memberRepository).save(argThat(m -> m.getEmail().equals(email)));
        doReturn(token).when(jwtProvider).createToken(email, provider);
        final SignUpResponse response = authService.signUp(request);

        // then
        ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository).save(memberCaptor.capture());
        assertEquals(response.memberId(), member.getId());
    }

}
