import type { Course } from "@/types";

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
  date: string;
};

export const getCourses = async (): Promise<Course[]> => {
  const response = await fetch(apiUrl("/courses"));

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(errorText || "Failed to load courses");
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
