package com.pp.common;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import com.pp.config.JpaAuditingConfig;
import com.pp.entity.member.Member;
import com.pp.enums.Gender;
import com.pp.enums.Provider;
import com.pp.enums.Role;
import net.jqwik.api.Arbitraries;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

@Import(JpaAuditingConfig.class)
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public abstract class ServiceTest {

    protected char[] charArray = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    protected Member getMember() {
        return getReflectionMonkey().giveMeBuilder(Member.class)
                .set("id", Arbitraries.longs().greaterOrEqual(4L))
                .set("email", Arbitraries.strings().alpha().ofMinLength(4).ofMaxLength(8).map(param -> param + "@gmail.com"))
                .set("nickname", Arbitraries.strings().alpha().ofMinLength(4).ofMaxLength(8))
                .set("gender", Arbitraries.of(Gender.class).sample())
                .set("role", Role.USER)
                .set("provider", Arbitraries.of(Provider.class))
                .set("providerId", Arbitraries.strings().withCharRange('a', 'z').ofMinLength(1).ofMaxLength(4).map(param -> param + Arbitraries.strings().numeric().ofLength(4)))
                .set("createdAt", Arbitraries.of(LocalDateTime.now()).sample())
                .set("updatedAt", Arbitraries.of(LocalDateTime.now()).sample())
                .sample();
    }

    protected final FixtureMonkey getReflectionMonkey() {
        return FixtureMonkey.builder()
                .plugin(new JakartaValidationPlugin())
                .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
                .build();
    }

    protected final FixtureMonkey getConstructorMonkey() {
        return FixtureMonkey.builder()
                .plugin(new JakartaValidationPlugin())
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .build();
    }

    protected final FixtureMonkey getBuilderMonkey() {
        return FixtureMonkey.builder()
                .plugin(new JakartaValidationPlugin())
                .objectIntrospector(BuilderArbitraryIntrospector.INSTANCE)
                .build();
    }

}
