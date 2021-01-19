package com.mevludin.APITouristBoard.repositories;

import com.mevludin.APITouristBoard.models.Sight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SightRepository extends JpaRepository<Sight,Long> {
}
