package Minari.cheongForDo.domain.term.repository;

import Minari.cheongForDo.domain.term.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TermRepository extends JpaRepository<Term, String> {

    List<Term> findAllByTermNm(String termNm);

}
