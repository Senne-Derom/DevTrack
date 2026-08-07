import CourseProgressOverview from "@/components/courseProgress/CourseProgressOverview";
import Link from "next/link";
import AddCourseOverview from "@/components/addCourse/AddCourseOverview";
import React from "react";
// import StudyEntryOverview from "@/components/studyEntryOverview/StudyEntryOverview";

type SearchParamProps = {
    searchParams: Promise<Record<string, string>> | null | undefined;
}

export default async function studyProgressPage({ searchParams }: SearchParamProps) {
    const params = await searchParams;
    const showAddCourse = params?.["show-add-course"] === "true";

  return (
    <div>
      <Link href="/study-progress?show-add-course=true" className="button">Add course</Link>

      {showAddCourse && <AddCourseOverview />}

      <h2>Progress overview per course</h2>
      <CourseProgressOverview />
      <h2>Overview of all study entries</h2>
      {/*<StudyEntryOverview />*/}
    </div>
  );
}
