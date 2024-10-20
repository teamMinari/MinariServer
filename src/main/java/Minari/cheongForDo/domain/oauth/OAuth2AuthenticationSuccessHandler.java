package Minari.cheongForDo.domain.oauth;

import Minari.cheongForDo.domain.member.authority.MemberAccountType;
import Minari.cheongForDo.global.auth.JwtInfo;
import Minari.cheongForDo.global.auth.JwtUtils;
import Minari.cheongForDo.domain.member.entity.MemberEntity;
import Minari.cheongForDo.domain.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtils jwtUtils;
    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;
    private final RestTemplate restTemplate; // RestTemplate 추가

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // OAuth2User에서 사용자 정보 가져오기
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        // 사용자 이메일을 사용하여 데이터베이스에서 사용자 찾기
        Optional<MemberEntity> existingUser = memberRepository.findByEmail(email);

        MemberEntity member;
        if (existingUser.isPresent()) {
            // 이미 존재하는 사용자인 경우, 해당 사용자 정보 가져오기
            member = existingUser.get();
        } else {
            // 새 사용자인 경우, 사용자에게 ID 입력 요청
            String id = request.getParameter("id"); // 프론트엔드에서 'id'를 전달받는다고 가정

            if (id == null || id.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("새 사용자 ID를 입력해야 합니다.");
                return;
            }

            // ID 중복 확인
            if (memberRepository.findById(id).isPresent()) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().write("이미 존재하는 ID입니다.");
                return;
            }

            member = createNewUser(id, email);
        }

        // Google 사용자 정보 요청
        Map<String, Object> googleUserInfo = fetchGoogleUserInfo(oAuth2User);
        
        // JWT 생성
        JwtInfo jwtInfo = jwtUtils.generateToken(member);

        // JWT를 응답 바디에 포함하여 반환
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(new JwtResponse(jwtInfo.getAccessToken(), jwtInfo.getRefreshToken())));
    }

    private Map<String, Object> fetchGoogleUserInfo(OAuth2User oAuth2User) {
        String accessToken = oAuth2User.getAttribute("accessToken"); // 여기서 accessToken을 받아오는 부분이 필요해.
        String userInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo"; // Google 사용자 정보 API URL

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken); // Bearer Token 설정

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Google 사용자 정보 요청
        try {
            ResponseEntity<Map> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, Map.class);
            return response.getBody();
        } catch (RestClientException e) {
            // 에러 처리
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    private MemberEntity createNewUser(String id, String email) {
        MemberEntity newUser = MemberEntity.builder()
                .id(id)  // 사용자가 입력한 아이디
                .email(email)
                .password("tempPassword") // 구글 로그인이므로 사용되지 않음
                .authority(MemberAccountType.ROLE_USER)
                .build();

        return memberRepository.save(newUser); // 새 사용자 저장
    }

    // JWT 응답 DTO
    public static class JwtResponse {
        private String accessToken;
        private String refreshToken;

        public JwtResponse(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }
}
