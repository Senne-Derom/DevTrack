package com.devtrack.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private int study_points;
    @OneToMany(mappedBy = "course")
    @JsonManagedReference
    private List<StudyEntry> studyEntries = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;

    public Course(String name, int study_points) {
        this.name = name;
        this.study_points = study_points;
    }

    public Course() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStudy_points() {
        return study_points;
    }

    public void setStudy_points(int study_points) {
        this.study_points = study_points;
    }

    public void addStudyEntry(StudyEntry entry) {
        studyEntries.add(entry);
        entry.setCourse(this);
    }

    public void removeStudyEntry(StudyEntry entry) {
        studyEntries.remove(entry);
        entry.setCourse(null);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
