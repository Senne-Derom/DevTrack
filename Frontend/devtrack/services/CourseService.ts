import {Course} from "@/types";

const backend_url = process.env.NEXT_PUBLIC_API_BASE_URL?.trim();

const API_BASE_URL = (
  backend_url &&
  backend_url !== "undefined" &&
  backend_url !== "null"
    ? backend_url
    : "http://localhost:8080"
).replace(/\/$/, "");

const apiUrl = (path: string) => `${API_BASE_URL}${path}`;

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