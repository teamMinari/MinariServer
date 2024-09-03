package Minari.cheongForDo.domain.learn.service;

import Minari.cheongForDo.domain.grape.entity.Grape;
import Minari.cheongForDo.domain.grape.repository.GrapeRepository;
import Minari.cheongForDo.domain.grapeSeed.entity.GrapeSeed;
import Minari.cheongForDo.domain.grapeSeed.repository.GrapeSeedRepository;
import Minari.cheongForDo.domain.grapes.entity.Grapes;
import Minari.cheongForDo.domain.grapes.repository.GrapesRepository;
import Minari.cheongForDo.domain.learn.entity.Learn;
import Minari.cheongForDo.domain.learn.enums.LearnCategory;
import Minari.cheongForDo.domain.learn.repository.LearnRepository;
import Minari.cheongForDo.domain.member.entity.MemberEntity;
import Minari.cheongForDo.global.auth.UserSessionHolder;
import Minari.cheongForDo.global.exception.CustomException;
import Minari.cheongForDo.global.response.Response;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static Minari.cheongForDo.global.exception.CustomErrorCode.GRAPESEED_NOT_EXIST;
import static Minari.cheongForDo.global.exception.CustomErrorCode.GRAPES_NOT_EXIST;
import static Minari.cheongForDo.global.exception.CustomErrorCode.GRAPE_NOT_EXIST;
import static Minari.cheongForDo.global.exception.CustomErrorCode.LEARN_ALREADY_EXIST;

@Service
@RequiredArgsConstructor
@Transactional
public class LearnService {
    private final LearnRepository learnRepository;
    private final UserSessionHolder userSessionHolder;
    private final GrapeSeedRepository grapeSeedRepository;
    private final GrapeRepository grapeRepository;
    private final GrapesRepository grapesRepository;

    public Response toggle(LearnCategory category, Long id) {
        MemberEntity curMember = userSessionHolder.current();

        if (category == LearnCategory.GRAPESEED) {
            likeGrapeSeed(curMember, id);
        } else if (category == LearnCategory.GRAPE) {
            likeGrape(curMember, id);
        } else {
            likeGrapes(curMember, id);
        }

        return Response.of(HttpStatus.OK, "학습 여부 등록 성공!");
    }


    private void likeGrapeSeed(MemberEntity curMember, Long gpseId) {
        GrapeSeed getGrapeSeed = getGrapeSeed(gpseId);

        Optional<Learn> learn = learnRepository.findByMemberAndGrapeSeed(curMember, getGrapeSeed);

        if (learn.isEmpty()) {
            addGrapeSeedLearn(curMember, getGrapeSeed);
            // curMember.increaseExp(getGrapeSeed.getGpseExp());
        } else {
            throw new CustomException(LEARN_ALREADY_EXIST);
        }
    }

    private void likeGrape(MemberEntity curMember, Long gpId) {
        Grape getGrape = getGrape(gpId);

        Optional<Learn> learn = learnRepository.findByMemberAndGrape(curMember, getGrape);

        if (learn.isEmpty()) {
            addGrapeLearn(curMember, getGrape);
        } else {
            throw new CustomException(LEARN_ALREADY_EXIST);
        }
    }

    private void likeGrapes(MemberEntity curMember, Long gpsId) {
        Grapes getGrapes = getGrapes(gpsId);

        Optional<Learn> learn = learnRepository.findByMemberAndGrapes(curMember, getGrapes);

        if (learn.isEmpty()) {
            addGrapesLearn(curMember, getGrapes);
        } else {
            throw new CustomException(LEARN_ALREADY_EXIST);
        }
    }


    private void addGrapeSeedLearn(MemberEntity member, GrapeSeed grapeSeed) {
        learnRepository.save(
                Learn.builder()
                        .member(member)
                        .grapeSeed(grapeSeed)
                        .build()
        );
    }

    private void addGrapeLearn(MemberEntity member, Grape grape) {
        learnRepository.save(
                Learn.builder()
                        .member(member)
                        .grape(grape)
                        .build()
        );
    }

    private void addGrapesLearn(MemberEntity member, Grapes grapes) {
        learnRepository.save(
                Learn.builder()
                        .member(member)
                        .grapes(grapes)
                        .build()
        );
    }

    private GrapeSeed getGrapeSeed(Long gpseId) {
        return grapeSeedRepository.findById(gpseId).orElseThrow(
                () -> new CustomException(GRAPESEED_NOT_EXIST));
    }

    private Grape getGrape(Long gpId) {
        return grapeRepository.findById(gpId).orElseThrow(
                () -> new CustomException(GRAPE_NOT_EXIST));
    }

    private Grapes getGrapes(Long gpsId) {
        return grapesRepository.findById(gpsId).orElseThrow(
                () -> new CustomException(GRAPES_NOT_EXIST)
        );
    }
}
