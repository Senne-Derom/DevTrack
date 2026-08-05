import type { Course, StudyEntry } from "@/types";

const configuredApiBaseUrl = process.env.NEXT_PUBLIC_API_BASE_URL?.trim();

const API_BASE_URL = (
  configuredApiBaseUrl &&
  configuredApiBaseUrl !== "undefined" &&
  configuredApiBaseUrl !== "null"
    ? configuredApiBaseUrl
    : "http://localhost:8080"
).replace(/\/$/, "");

const apiUrl = (path: string) => `${API_BASE_URL}${path}`;

export type AddStudyEntryInput = {
  course: Pick<Course, "id" | "name">;
  description: string;
  timeSpent: number;
  date: string;
};

export const getCourses = async (): Promise<Course[]> => {
  const response = await fetch(apiUrl("/courses"), {
      // this caches the result of /courses
      next: {tags: ["courses"]}
  });

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(errorText || "Failed to load courses");
  }

  return response.json();
};

export const getStudyEntries = async (): Promise<StudyEntry[]> => {
  const response = await fetch(apiUrl("/study_entries"));

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(errorText || "Failed to load study entries");
  }

  return response.json();
};

export const addStudyEntry = async (studyEntry: AddStudyEntryInput) => {
  const response = await fetch(apiUrl("/study_entries/addStudyEntry"), {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(studyEntry),
  });

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(errorText || "Failed to add study entry");
  }

  return response.json();
};

export const addCourse = async (course: Course) => {
    const response = await fetch(apiUrl("/courses/addCourse"), {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(course)
    });

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || "Failed to add course");
    }

    return response.json();
};
