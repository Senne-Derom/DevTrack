import CourseProgressOverview from "@/components/courseProgress/CourseProgressOverview";
import Link from "next/link";
import AddCourseOverview from "@/components/addCourse/AddCourseOverview";
import React from "react";
import AddStudyEntryOverview from "@/components/addStudy/AddStudyEntryOverview";
// import StudyEntryOverview from "@/components/studyEntryOverview/StudyEntryOverview";

type SearchParamProps = {
    searchParams: Promise<Record<string, string>> | null | undefined;
}

export default async function studyProgressPage({ searchParams }: SearchParamProps) {
    const params = await searchParams;
    const showAddCourse = params?.["show-add-course"] === "true";
    const showAddStudyEntry = params?.["show-add-study-entry"] === "true";

  return (
    <div>
      <Link href="/study-progress?show-add-course=true" className="button">Add course</Link>
      <Link href="/study-progress?show-add-study-entry=true" className="button">Add study entry</Link>

      {showAddCourse && <AddCourseOverview />}

      {showAddStudyEntry && <AddStudyEntryOverview />}

      <h2>Progress overview per course</h2>
      <CourseProgressOverview />
      <h2>Overview of all study entries</h2>
      {/*<StudyEntryOverview />*/}
    </div>
  );
}
