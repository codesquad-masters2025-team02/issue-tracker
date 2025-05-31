package elbin_bank.issue_tracker.auth.application.command;

import com.github.benmanes.caffeine.cache.Cache;
import elbin_bank.issue_tracker.auth.application.command.dto.TokenDto;
import elbin_bank.issue_tracker.auth.exception.UserAlreadyExistsException;
import elbin_bank.issue_tracker.auth.domain.JwtProvider;
import elbin_bank.issue_tracker.auth.presentation.command.dto.LoginRequestDto;
import elbin_bank.issue_tracker.auth.presentation.command.dto.SignUpRequestDto;
import elbin_bank.issue_tracker.auth.util.PasswordEncoderUtil;
import elbin_bank.issue_tracker.user.domain.User;
import elbin_bank.issue_tracker.user.domain.UserCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthCommandService {

    private final UserCommandRepository userCommandRepository;
    private final JwtProvider jwt;
    private final Cache<String, Long> cache;

    @Transactional
    public void register(SignUpRequestDto dto) {
        if (userCommandRepository.findByLogin(dto.login()).isPresent()) {
            throw new UserAlreadyExistsException("이미 존재하는 로그인입니다.");
        }
        String uuid = UUID.randomUUID().toString();
        String salt = PasswordEncoderUtil.generateSalt();
        String hash = PasswordEncoderUtil.hashPassword(dto.password(), salt);

        User user = User.createByLogin(dto.login(), hash, salt, dto.nickname(), dto.profileImageUrl(), uuid);

        userCommandRepository.save(user);
    }

    @Transactional(readOnly = true)
    public TokenDto login(LoginRequestDto dto) {
        User user = userCommandRepository.findByLogin(dto.login())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!PasswordEncoderUtil.verifyPassword(dto.password(), user.getSalt(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        // 캐시에 사용자 UUID와 ID를 저장
        cache.put(user.getUuid(), user.getId());

        // JWT 생성 및 반환
        return jwt.createJwt(user);
    }

}
