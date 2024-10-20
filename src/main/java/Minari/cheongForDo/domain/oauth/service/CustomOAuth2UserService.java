package Minari.cheongForDo.domain.oauth.service;

import Minari.cheongForDo.domain.member.authority.MemberAccountType;
import Minari.cheongForDo.domain.member.entity.MemberEntity;
import Minari.cheongForDo.domain.member.repository.MemberRepository;
import Minari.cheongForDo.domain.oauth.OAuthAttributes;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import Minari.cheongForDo.domain.oauth.dto.SessionUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        MemberEntity member = saveOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionUserDTO(member));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getAuthority().name())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private MemberEntity saveOrUpdate(OAuthAttributes attributes) {
        return memberRepository.findByEmail(attributes.getEmail())
                .orElseGet(() -> createNewMember(attributes));
    }

    private MemberEntity createNewMember(OAuthAttributes attributes) {
        MemberAccountType authority = MemberAccountType.ROLE_USER;

        return memberRepository.save(MemberEntity.builder()
                .id(generateUniqueId(attributes.getEmail())) // 사용자 ID를 따로 입력받는 과정은 이후 진행
                .password(generateTemporaryPassword())
                .email(attributes.getEmail())
                .point(0L)
                .exp(0L)
                .authority(authority)
                .title("New Member")
                .level(1L)
                .checkLevel(1L)
                .gpsList(Collections.emptyList())
                .build());
    }

    private String generateUniqueId(String email) {
        return email.split("@")[0] + "_" + System.currentTimeMillis();
    }

    private String generateTemporaryPassword() {
        return "tempPassword123!";
    }
}