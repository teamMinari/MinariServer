package Minari.cheongForDo.domain.grape.repository;

import Minari.cheongForDo.domain.grape.entity.Grape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrapeRepository extends JpaRepository<Grape, Long> {
}
