package com.example.PerfulandiaSPA.Repository;

import com.example.PerfulandiaSPA.Model.Perfume;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PerfumeRepository extends JpaRepository<Perfume, Integer> {
    Optional<Perfume> findByIsbn(String isbn);
}