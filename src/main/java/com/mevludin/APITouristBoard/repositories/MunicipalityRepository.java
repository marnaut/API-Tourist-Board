package com.mevludin.APITouristBoard.repositories;

import com.mevludin.APITouristBoard.models.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality,Long> {

    List<Municipality> findByCountryIdAndActivity(Long countryId, Boolean active);

    Municipality findByIdAndActivity(Long id, Boolean activity);
}
