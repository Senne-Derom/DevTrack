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
      {courses.map((course) => {
        const totalTime = course.studyEntries.reduce(
          (sum, entry) => sum + entry.timeSpent,
          0,
        );
        console.log(course);
        return (
          <div>
            <h3>{course.name}</h3>
            <p
              style={{
                color:
                  totalTime < 1 ? "green" : totalTime <= 2 ? "orange" : "red",
              }}
            >
              {totalTime} Total time
            </p>
          </div>
        );
      })}
    </div>
  );
};

export default CourseProgressOverview;
