package Minari.cheongForDo.domain.grapeSeed.repository;

import Minari.cheongForDo.domain.grapeSeed.entity.GrapeSeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrapeSeedRepository extends JpaRepository<GrapeSeed, Long> {
}
