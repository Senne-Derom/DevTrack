import CourseProgressOverview from "@/components/courseProgress/CourseProgressOverview";
import StudyEntryOverview from "@/components/studyEntryOverview/StudyEntryOverview";

export default function studyProgressPage() {
  return (
    <div>
      <h2>Progress overview per course</h2>
      <CourseProgressOverview />
      <h2>Overview of all study entries</h2>
      <StudyEntryOverview />
    </div>
  );
}
