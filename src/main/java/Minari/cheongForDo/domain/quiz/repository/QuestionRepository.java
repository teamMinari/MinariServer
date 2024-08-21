package Minari.cheongForDo.domain.quiz.repository;

import Minari.cheongForDo.domain.quiz.entity.Question;
import Minari.cheongForDo.domain.quiz.model.enums.QuestionDifficulty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByQtDifficulty(QuestionDifficulty level);

}
