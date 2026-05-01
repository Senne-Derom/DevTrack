package com.devtrack.repository;

import com.devtrack.model.StudyEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyEntryRepository extends JpaRepository<StudyEntry, Long> {
}
