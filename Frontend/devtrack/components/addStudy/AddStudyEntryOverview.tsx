"use client";

import React, { FormEvent, useEffect, useState } from "react";
import { addStudyEntry, getCourses } from "@/services/StudyService";
import type { Course } from "@/types";

const AddStudyEntryOverview: React.FC = () => {
  const [courses, setCourses] = useState<Course[]>([]);
  const [selectedCourseId, setSelectedCourseId] = useState("");
  const [date, setDate] = useState("");
  const [description, setDescription] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [isLoadingCourses, setIsLoadingCourses] = useState(true);
  const [coursesError, setCoursesError] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [timeSpent, setTimeSpent] = useState<number>(0); 

  useEffect(() => {
    const loadCourses = async () => {
      try {
        setIsLoadingCourses(true);
        setCoursesError("");

        const loadedCourses = await getCourses();
        setCourses(loadedCourses);
        setSelectedCourseId((currentSelectedCourseId) => {
          if (currentSelectedCourseId) {
            return currentSelectedCourseId;
          }

          return loadedCourses.length > 0 ? String(loadedCourses[0].id) : "";
        });
      } catch (error) {
        setCoursesError(
          error instanceof Error ? error.message : "Failed to load courses.",
        );
      } finally {
        setIsLoadingCourses(false);
      }
    };

    loadCourses();
  }, []);

  const validate = (): boolean => {
    return (
      selectedCourseId.trim() !== "" &&
      date.trim() !== "" &&
      timeSpent != 0 &&
      description.trim() !== ""
    );
  };

  const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    setErrorMessage("");
    setSuccessMessage("");

    if (!validate()) {
      setErrorMessage(
        "Please select a course and fill in the description, time spent and date.",
      );
      return;
    }

    const selectedCourse = courses.find(
      (course) => Number(course.id) === Number(selectedCourseId),
    );

    if (!selectedCourse) {
      setErrorMessage("Please select a valid course.");
      return;
    }

    try {
      setIsSubmitting(true);

      await addStudyEntry({
        course: {
          id: selectedCourse.id,
          name: selectedCourse.name,
        },
        description: description.trim(),
        timeSpent: timeSpent,
        date,
      });

      setSuccessMessage("Study entry added successfully.");
      setDate("");
      setTimeSpent(0);
      setDescription("");
    } catch (error) {
      setErrorMessage(
        error instanceof Error ? error.message : "Failed to add study entry.",
      );
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label htmlFor="courseInput">Course name:</label>
        <select
          id="courseInput"
          name="course"
          value={selectedCourseId}
          onChange={(event) => setSelectedCourseId(event.target.value)}
          disabled={isLoadingCourses}
        >
          <option value="">
            {isLoadingCourses ? "Loading courses..." : "--Select a course--"}
          </option>
          {courses.map((course) => (
            <option key={course.id} value={course.id}>
              {course.name}
            </option>
          ))}
        </select>
      </div>
      <div>
        <label htmlFor="descriptionInput">Description:</label>
        <input
          type="text"
          id="descriptionInput"
          name="description"
          value={description}
          onChange={(event) => setDescription(event.target.value)}
          placeholder="Enter description"
        />
      </div>
      <div>
        <label htmlFor="timeSpentInput">Time spent:</label>
        <input
          type="number"
          step="0.5"
          id="timeSpentInput"
          name="timeSpent"
          value={timeSpent}
          onChange={(event) => setTimeSpent(Number(event.target.value))}
        />
      </div>
      <div>
        <label htmlFor="dateInput">Date:</label>
        <input
          type="date"
          id="dateInput"
          name="date"
          value={date}
          onChange={(event) => setDate(event.target.value)}
        />
      </div>

      {coursesError ? <p>{coursesError}</p> : null}
      {errorMessage ? <p>{errorMessage}</p> : null}
      {successMessage ? <p>{successMessage}</p> : null}

      <button type="submit" disabled={isSubmitting || isLoadingCourses}>
        {isSubmitting ? "Adding..." : "Add Study Entry"}
      </button>
    </form>
  );
};

export default AddStudyEntryOverview;
