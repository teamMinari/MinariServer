package Minari.cheongForDo.domain.term.repository;

import Minari.cheongForDo.domain.term.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term, String> {
}
