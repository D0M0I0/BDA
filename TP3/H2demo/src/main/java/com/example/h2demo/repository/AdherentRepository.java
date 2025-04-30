package com.example.h2demo.repository;

import com.example.h2demo.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdherentRepository extends JpaRepository<Adherent, Long>
{
}
