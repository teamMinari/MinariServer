//package Minari.cheongForDo.domain.member.service;
//
//import Minari.cheongForDo.domain.member.repository.MemberRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import Minari.cheongForDo.domain.member.entity.MemberEntity;
//
//@Service
//public class ProfileService {
//    private final MemberRepository MEMBER_REPOSITORY;
//
//    @Autowired
//    public ProfileService(MemberRepository memberRepository) {
//        this.MEMBER_REPOSITORY = memberRepository;
//    }
//
//    public MemberEntity getUserProfile(Long userId) {
//        return MEMBER_REPOSITORY.findById(userId).orElse(null);
//    }
//
//    public MemberEntity updateUserProfile(Long userId, MemberEntity updatedUser) {
//        MemberEntity existingUser = MEMBER_REPOSITORY.findById(userId).orElse(null);
//        if (existingUser != null) {
//            // 이름 업데이트
//            existingUser.setUserName(updatedUser.getUserName());
//            // 프로필 사진 업데이트
//            existingUser.setProfileImage(updatedUser.getProfileImage());
//            // 포인트 업데이트
//            existingUser.setPoints(updatedUser.getPoints());
//            // 변경사항 저장
//            return MemberRepository.save(existingUser);
//        }
//        return null;
//    }
//}
