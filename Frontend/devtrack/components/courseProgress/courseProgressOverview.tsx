import { getCourses } from "@/services/StudyService";
import { Course } from "@/types";
import { useEffect, useState } from "react";

const CourseProgressOverview: React.FC = () => {
  const [courses, setCourses] = useState<Course[]>([]);
  const [coursesError, setCOursesError] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [isLoadingCourses, setIsLoadingCourses] = useState(false);

  useEffect(() => {
    const loadCourses = async () => {
      try {
        setIsLoadingCourses(true);
        setCOursesError("");

        const loadedCourses = await getCourses();
        setCourses(loadedCourses);
      } catch (error) {
        setCOursesError(
          error instanceof Error ? error.message : "Failed to load courses",
        );
      } finally {
        setIsLoadingCourses(false);
      }
    };

    loadCourses();
  }, []);

  return (
    <div>
      {courses.map((course) => (
        <>
          <h3>{course.name}</h3>.
          <p>
            Total time:{" "}
            {course.studyEntries.reduce(
              (sum, entry) => sum + entry.timeSpent,
              0,
            )}{" "}
            hours
          </p>
        </>
      ))}
    </div>
  );
};

export default CourseProgressOverview;
