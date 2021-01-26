package com.mevludin.APITouristBoard.repositories;

import com.mevludin.APITouristBoard.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageDbRepository extends JpaRepository<Image,Long> {
}
