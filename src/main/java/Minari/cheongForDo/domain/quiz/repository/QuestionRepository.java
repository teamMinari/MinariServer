package Minari.cheongForDo.domain.quiz.repository;

import Minari.cheongForDo.domain.quiz.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
