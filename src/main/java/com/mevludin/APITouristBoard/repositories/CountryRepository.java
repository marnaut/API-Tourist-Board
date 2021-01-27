package com.mevludin.APITouristBoard.repositories;

import com.mevludin.APITouristBoard.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country,Long> {
    List<Country> findByActivity(Boolean activity);

    Optional<Country> findByIdAndActivity(Long id, Boolean activity);
}
