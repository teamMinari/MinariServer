package Minari.cheongForDo.domain.grapes.repository;

import Minari.cheongForDo.domain.grapes.entity.Grapes;
import Minari.cheongForDo.domain.grapes.enums.GrapesAgeGroup;
import Minari.cheongForDo.domain.grapes.enums.GrapesWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrapesRepository extends JpaRepository<Grapes, Long> {

    List<Grapes> findAll();

    List<Grapes> findByGpsAgeGroup(GrapesAgeGroup ageGroup);

    List<Grapes> findByGpsWork(GrapesWork work);

    List<Grapes> findByGpsAgeGroupAndGpsWork(GrapesAgeGroup ageGroup, GrapesWork work);
}
