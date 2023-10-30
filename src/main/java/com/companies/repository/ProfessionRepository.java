package com.companies.repository;

import com.companies.entity.Profession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfessionRepository extends JpaRepository<Profession, Long> {

    List<Profession> getAllByNameIn(List<String> names);
}
