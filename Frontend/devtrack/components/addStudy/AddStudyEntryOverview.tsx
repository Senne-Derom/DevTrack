"use client";

import React, { FormEvent, useEffect, useState } from "react";
import type { Course } from "@/types";
import { getCourses } from "@/services/CourseService";
import { addStudyEntry } from "@/services/StudyEntryService";
import { revalidateStudyEntries } from "@/app/actions/revalidateStudyEntries";

const AddStudyEntryOverview: React.FC = () => {
  const [courses, setCourses] = useState<Course[]>([]);
  const [selectedCourseId, setSelectedCourseId] = useState("");
  const [date, setDate] = useState("");
  const [description, setDescription] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [isLoadingCourses, setIsLoadingCourses] = useState(true);
  const [coursesError, setCoursesError] = useState("");
  const [courseError, setCourseError] = useState("");
  const [descriptionError, setDescriptionError] = useState("");
  const [dateError, setDateError] = useState("");
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

  const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    setErrorMessage("");
    setSuccessMessage("");
    setCourseError("");
    setDescriptionError("");
    setDateError("");

    let hasValidationErrors = false;

    if (!selectedCourseId.trim()) {
      setCourseError("Course is required.");
      hasValidationErrors = true;
    }

    if (description.trim() === "") {
      setDescriptionError("Description is required.");
      hasValidationErrors = true;
    }

    if (!date) {
      setDateError("Date is required.");
      hasValidationErrors = true;
    }

    if (hasValidationErrors) {
      return;
    }

    const selectedCourse = courses.find(
      (course) => Number(course.id) === Number(selectedCourseId),
    );

    if (!selectedCourse) {
      setCourseError("Please select a valid course.");
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
      setSelectedCourseId("");
      setDate("");
      setTimeSpent(0);
      setDescription("");
      await revalidateStudyEntries();
    } catch (error) {
      setErrorMessage(
        error instanceof Error ? error.message : "Failed to add study entry.",
      );
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <div className="add-course-overview">
          <form className="add-course-form" onSubmit={handleSubmit}>
            <div className="form-field">
              <label className="form-label" htmlFor="courseInput">
                Course name:
              </label>
              <select
                id="courseInput"
                name="course"
                value={selectedCourseId}
                onChange={(event) => {
                  setSelectedCourseId(event.target.value);
                  if (courseError) {
                    setCourseError("");
                  }
                }}
                disabled={isLoadingCourses}
              >
                <option value="">
                  {isLoadingCourses
                    ? "Loading courses..."
                    : "--Select a course--"}
                </option>
                {courses.map((course) => (
                  <option key={course.id} value={course.id}>
                    {course.name}
                  </option>
                ))}
              </select>
              {courseError && (
                <span className="field-error">{courseError}</span>
              )}
              {coursesError && (
                <p className="status-message status-message-error">
                  {coursesError}
                </p>
              )}
            </div>
            <div className="form-field">
              <label className="form-label" htmlFor="descriptionInput">
                Description:
              </label>
              <input
                type="text"
                id="descriptionInput"
                name="description"
                value={description}
                onChange={(event) => {
                  setDescription(event.target.value);
                  if (descriptionError) {
                    setDescriptionError("");
                  }
                }}
                placeholder="Enter description"
              />
              {descriptionError && (
                <span className="field-error">{descriptionError}</span>
              )}
            </div>
            <div className="form-field">
              <label className="form-label" htmlFor="timeSpentInput">
                Time spent:
              </label>
              <input
                type="number"
                step="0.5"
                min="0.5"
                id="timeSpentInput"
                name="timeSpent"
                value={timeSpent}
                onChange={(event) => setTimeSpent(Number(event.target.value))}
              />
            </div>
            <div className="form-field">
              <label className="form-label" htmlFor="dateInput">
                Date:
              </label>
              <input
                type="date"
                id="dateInput"
                name="date"
                value={date}
                onChange={(event) => {
                  setDate(event.target.value);
                  if (dateError) {
                    setDateError("");
                  }
                }}
              />
              {dateError && <span className="field-error">{dateError}</span>}
            </div>

            <button
              type="submit"
              className="add-course-button"
              disabled={isSubmitting || isLoadingCourses}
            >
              {isSubmitting ? "Adding..." : "Add Study Entry"}
            </button>

            {successMessage && (
              <p className="status-message status-message-success">
                {successMessage}
              </p>
            )}
            {errorMessage && (
              <p className="status-message status-message-error">
                {errorMessage}
              </p>
            )}
            <button
              type="button"
              className="modal-close-button"
              onClick={() => (window.location.href = "/study-progress")}
            >
              Close
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default AddStudyEntryOverview;
