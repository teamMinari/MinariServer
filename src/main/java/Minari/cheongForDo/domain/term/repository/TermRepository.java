package Minari.cheongForDo.domain.term.repository;

import Minari.cheongForDo.domain.term.entity.Term;
import Minari.cheongForDo.domain.term.model.enums.TermDifficulty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TermRepository extends JpaRepository<Term, Long> {

    List<Term> findAllByTermDifficulty(TermDifficulty level);


}
