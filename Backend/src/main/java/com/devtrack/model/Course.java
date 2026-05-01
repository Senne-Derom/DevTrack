package com.devtrack.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "course")
    @JsonManagedReference
    private ArrayList<StudyEntry> studyEntries = new ArrayList<>();

    public Course(String name) {
        this.name = name;
    }

    public Course() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addStudyEntry(StudyEntry entry) {
        studyEntries.add(entry);
        entry.setCourse(this);
    }

    public void removeStudyEntry(StudyEntry entry) {
        studyEntries.remove(entry);
        entry.setCourse(null);
    }
}
