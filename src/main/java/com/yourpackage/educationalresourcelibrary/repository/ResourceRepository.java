package com.yourpackage.educationalresourcelibrary.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yourpackage.educationalresourcelibrary.model.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
	Optional<Resource> findByTitle(String title);
	List<Resource> findByCategory(String category);
}
