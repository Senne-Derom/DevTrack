"use client";

import {FormEvent, useState} from "react";
import {addCourse} from "@/services/StudyService";
import {revalidateCourses} from "@/app/actions/revalidateCourses";

const AddCourseOverview : React.FC = () => {
    const [name, setName] = useState("");
    const [study_points, setstudy_points] = useState<number>(0);
    const [nameError, setNameError] = useState("");
    const [study_pointsError, setstudy_pointsError] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");
    const [successMessage, setSuccessMessage] = useState("");

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        setErrorMessage("");
        setSuccessMessage("");
        setNameError("");
        setstudy_pointsError("");

        if (name.trim() === "") {
            setNameError("Name is required");
            return;
        }

        if (study_points <= 0) {
            setstudy_pointsError("Study points must be greater than 0");
            return;
        }

        try {
            setIsSubmitting(true);

            await addCourse({ name, study_points });
            await revalidateCourses();

            setSuccessMessage("Course added successfully");
            setName("");
            // setstudy_points(0);
        } catch {
            setErrorMessage("An error occurred while adding the course");
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <div className="add-course-overview">
            <form className="add-course-form" onSubmit={handleSubmit}>
                <div className="form-field">
                    <label className="form-label" htmlFor="courseNameInput">
                        Name:
                    </label>
                    <input
                        id="courseNameInput"
                        type="text"
                        value={name}
                        onChange={(e) => {
                            setName(e.target.value);
                            if (nameError) {
                                setNameError("");
                            }
                        }}
                        placeholder="Enter course name"
                    />
                    {nameError && <span className="field-error">{nameError}</span>}
                </div>
                <div className="form-field">
                    <label className="form-label" htmlFor="studyPointsInput">
                        Study Points:
                    </label>
                    <input
                        id="studyPointsInput"
                        type="number"
                        min="1"
                        value={study_points}
                        onChange={(e) => {
                            setstudy_points(Number(e.target.value));
                            if (study_pointsError) {
                                setstudy_pointsError("");
                            }
                        }}
                    />
                    {study_pointsError && <span className="field-error">{study_pointsError}</span>}
                </div>
                <button type="submit" className="add-course-button" disabled={isSubmitting}>
                    {isSubmitting ? "Adding..." : "Add Course"}
                </button>
                {errorMessage && <p className="status-message status-message-error">{errorMessage}</p>}
                {successMessage && <p className="status-message status-message-success">{successMessage}</p>}
            </form>
        </div>
    )
}

export default AddCourseOverview;