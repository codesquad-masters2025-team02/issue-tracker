package elbin_bank.issue_tracker.auth.presentation.command;

import elbin_bank.issue_tracker.auth.application.command.AuthCommandService;
import elbin_bank.issue_tracker.auth.application.command.OAuthLoginService;
import elbin_bank.issue_tracker.auth.application.command.dto.TokenDto;
import elbin_bank.issue_tracker.auth.presentation.command.dto.LoginRequestDto;
import elbin_bank.issue_tracker.auth.presentation.command.dto.SignUpRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthCommandService authCommandService;
    private final OAuthLoginService oAuthLoginService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        authCommandService.register(signUpRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequestDto dto) {
        TokenDto tokenDto = authCommandService.login(dto);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.AUTHORIZATION, tokenDto.tokenType() + " " + tokenDto.accessToken())
                .build();
    }

    @GetMapping("/oauth/github")
    public ResponseEntity<Void> redirectToGithub(HttpServletRequest request) {
        // CSRF 방지를 위해 임의 state 생성 후 세션에 저장
        System.out.println("Redirecting to GitHub OAuth...");
        String state = UUID.randomUUID().toString();
        HttpSession session = request.getSession(true);
        session.setAttribute("OAUTH_STATE", state);

        String authorizeUrl = oAuthLoginService.getAuthorizationUrl(state);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", authorizeUrl)
                .build();
    }

    @GetMapping("/oauth/github/callback")
    public ResponseEntity<Void> githubCallback(
            @RequestParam("code") String code,
            @RequestParam("state") String state,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("OAUTH_STATE") == null) {
            return ResponseEntity.badRequest().build();
        }
        String savedState = (String) session.getAttribute("OAUTH_STATE");
        session.removeAttribute("OAUTH_STATE");
        if (!savedState.equals(state)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        TokenDto tokenDto = oAuthLoginService.handleGithubLogin(code);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.AUTHORIZATION, tokenDto.tokenType() + " " + tokenDto.accessToken())
                .build();
    }

}
