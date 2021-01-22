package com.mevludin.APITouristBoard.repositories;

import com.mevludin.APITouristBoard.models.Importance;
import com.mevludin.APITouristBoard.models.Municipality;
import com.mevludin.APITouristBoard.models.Sight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SightRepository extends JpaRepository<Sight,Long> {
    List<Sight> findByMunicipalityIdAndImportance(Long municipalityId, Optional<Importance> importance);
}
