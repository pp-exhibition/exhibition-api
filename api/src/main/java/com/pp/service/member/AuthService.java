package com.pp.service.member;

import com.pp.dto.request.SignUpRequest;
import com.pp.dto.response.SignUpResponse;
import com.pp.entity.member.Member;
import com.pp.entity.member.Word;
import com.pp.enums.Gender;
import com.pp.enums.Provider;
import com.pp.enums.WordType;
import com.pp.exception.CustomException;
import com.pp.exception.ResponseCode;
import com.pp.provider.JwtProvider;
import com.pp.repository.MemberRepository;
import com.pp.repository.WordRepository;
import com.pp.utils.RandomUtil;
import com.pp.vo.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final int NICKNAME_SUFFIX_LENGTH = 3;

    private final WordRepository wordRepository;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        checkEmailDuplication(request.email());
        final Token token = jwtProvider.createToken(request.email(), Provider.EMAIL);
        final Member member = memberRepository.save(Member.createEmailMember(request.email(), passwordEncoder.encode(request.password()), generateRandomNickname(), Gender.UNKNOWN));
        return SignUpResponse.from(member, token);
    }

    private void checkEmailDuplication(final String email) {
        if (memberRepository.existsByEmailAndProvider(email, Provider.EMAIL)) {
            throw new CustomException(ResponseCode.ALREADY_IN_USE_EMAIL);
        }
    }

    private void checkNicknameDuplication(final String nickname) {
        if (memberRepository.existsByEmailAndProvider(nickname, Provider.EMAIL)) {
            throw new CustomException(ResponseCode.ALREADY_IN_USE_NICKNAME);
        }
    }

    private String generateRandomNickname() {
        Map<WordType, List<Word>> wordMap = wordRepository.getAll().stream().collect(Collectors.groupingBy(Word::getType));
        List<Word> adjectiveWords = wordMap.get(WordType.ADJECTIVE);
        List<Word> nounWords = wordMap.get(WordType.NOUN);

        String nickname;
        boolean exists;

        do {
            String randomAdjectiveWord = getRandomWord(adjectiveWords);
            String randomNounWord = getRandomWord(nounWords);
            String suffix = RandomUtil.generateRandomCode(NICKNAME_SUFFIX_LENGTH);

            nickname = randomAdjectiveWord + randomNounWord + suffix;
            exists = memberRepository.existsByNickname(nickname);
        } while (exists);

        return nickname;
    }

    private String getRandomWord(List<Word> words) {
        Word word = RandomUtil.getRandomElement(words);
        return word.getWord();
    }

}
