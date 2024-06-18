package Minari.cheongForDo.domain.term.repository;

import Minari.cheongForDo.domain.term.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TermRepository extends JpaRepository<Term, String> {

    List<Term> findAllByTermNm(String termNm);

    @Query("select p from Term p where p.termLike = true")
    List<Term> findAllByTermLike();

}
