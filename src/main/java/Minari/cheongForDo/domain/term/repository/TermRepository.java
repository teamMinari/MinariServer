package Minari.cheongForDo.domain.term.repository;

import Minari.cheongForDo.domain.term.entity.Term;
import Minari.cheongForDo.domain.term.model.enums.TermDifficulty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TermRepository extends JpaRepository<Term, Long> {

    List<Term> findAllByTermDifficulty(TermDifficulty level);
    Page<Term> findAll(Pageable pageable);
    Term findByTermNm(String termNm);

    @Query("SELECT t FROM Term t LEFT JOIN FETCH t.likes l WHERE t.termNm LIKE %:keyword%")
    List<Term> findByTermNmContaining(@Param("keyword") String keyword);


}
