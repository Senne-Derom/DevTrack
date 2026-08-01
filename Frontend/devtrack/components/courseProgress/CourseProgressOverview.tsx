"use client";

import { getCourses } from "@/services/StudyService";
import { Course } from "@/types";
import { useEffect, useState } from "react";

const CourseProgressOverview: React.FC = () => {
  const [courses, setCourses] = useState<Course[]>([]);
  const [coursesError, setCoursesError] = useState("");
  const [isLoadingCourses, setIsLoadingCourses] = useState(false);

  useEffect(() => {
    const loadCourses = async () => {
      try {
        setIsLoadingCourses(true);
        setCoursesError("");

        const loadedCourses = await getCourses();
        setCourses(loadedCourses);
      } catch (error) {
        setCoursesError(
          error instanceof Error ? error.message : "Failed to load courses",
        );
      } finally {
        setIsLoadingCourses(false);
      }
    };

    loadCourses();
  }, []);

  return (
    <div className="course-progress-overview">
      {coursesError && <p className="status-message status-message-error">{coursesError}</p>}
      {isLoadingCourses && <p className="status-message">Loading courses...</p>}
      {!isLoadingCourses && (
        <div className="table-wrapper">
          <table className="course-progress-table">
            <thead>
              <tr>
                <th>Course</th>
                <th>Study points</th>
                <th>Hours spent</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {courses.length === 0 ? (
                <tr>
                  <td colSpan={4}>No courses found.</td>
                </tr>
              ) : (
                courses.map((course) => {
                  const totalTime = (course.studyEntries ?? []).reduce(
                    (sum, entry) => sum + entry.timeSpent,
                    0,
                  );
                  const targetHours = course.study_points * 30;
                  const progressClass =
                    totalTime < targetHours * 0.75
                      ? "progress-good"
                      : totalTime <= targetHours
                        ? "progress-warn"
                        : "progress-over";
                  const progressLabel =
                    totalTime < targetHours * 0.75
                      ? "Building up"
                      : totalTime <= targetHours
                        ? "Close"
                        : "Exceeded";

                  return (
                    <tr key={course.id}>
                      <td>{course.name}</td>
                      <td>{course.study_points}</td>
                      <td>{totalTime} hour(s)</td>
                      <td>
                        <span className={`progress-badge ${progressClass}`}>
                          {progressLabel}
                        </span>
                      </td>
                    </tr>
                  );
                })
              )}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default CourseProgressOverview;
