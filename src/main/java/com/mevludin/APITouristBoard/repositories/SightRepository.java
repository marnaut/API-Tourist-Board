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
    List<Sight> findByMunicipalityIdAndActivity(Long municipalityId, Boolean activity);

    Optional<Sight> findByIdAndActivity(Long id, Boolean activity);

    List<Sight> findByMunicipalityIdAndImportanceAndActivity(Long municipalityId, Optional<Importance> importance, Boolean activity);

    List<Sight> findByMunicipalityIdAndSightNameContainingAndActivity(Long municipalityId, Optional<String> sightName, Boolean activity);

    List<Sight> findByMunicipalityIdAndSightNameContainingAndImportanceAndActivity(Long municipalityId, Optional<String> sightName, Optional<Importance> importance, Boolean activity);

}
