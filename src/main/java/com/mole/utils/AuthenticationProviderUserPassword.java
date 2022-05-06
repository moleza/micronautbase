package com.mole.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.mole.entity.User;
import com.mole.repository.UserRepository;

import org.reactivestreams.Publisher;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.annotations.Nullable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class AuthenticationProviderUserPassword implements AuthenticationProvider { 

    @Inject
    private final UserRepository userRepo;
    @Value("${micronaut.application.secretKey}")
    private static String key;
    @Value("${micronaut.application.secretSalt}")
    private static String salt;

    // private static final Logger log = LoggerFactory.getLogger(AuthenticationProviderUserPassword.class);

    @Override
    public Publisher<AuthenticationResponse> authenticate(@Nullable HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Flowable.create(emitter -> {
            Optional<User> user = userRepo.findByEmailAndPasswordAndEnabled(authenticationRequest.getIdentity().toString(), Aes256.encrypt(authenticationRequest.getSecret().toString(),key,salt), true);
            if (user.isPresent()) {
                User user_ = user.get();
                List<String> roles = new ArrayList<String>();
                if (user_.getLevel() == 100) {
                    roles = Arrays.asList("ADMIN");
                } else if (user_.getLevel() == 11) {
                    roles = Arrays.asList("BUSINESS");
                } else if (user_.getLevel() == 1) {
                    roles = Arrays.asList("USER");
                }
                if (roles.size() == 0) {
                    emitter.onError(new AuthenticationException(new AuthenticationFailed()));
                } else {
                    emitter.onNext(AuthenticationResponse.success(authenticationRequest.getIdentity().toString(), roles));
                    emitter.onComplete();
                }
            } else {
                emitter.onError(new AuthenticationException(new AuthenticationFailed()));
            }
        }, BackpressureStrategy.ERROR);
    }
}