package Minari.cheongForDo.domain.grapes.repository;

import Minari.cheongForDo.domain.grapes.entity.Grapes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrapesRepository extends JpaRepository<Grapes, Long> {
}
