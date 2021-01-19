package com.mevludin.APITouristBoard.repositories;

import com.mevludin.APITouristBoard.models.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality,Long> {
}
