import {FormEvent, useState} from "react";
import {addCourse} from "@/services/StudyService";

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
        
        if (name.trim() === "") {
            setNameError("Name is required.");
            return;
        }
        
        if (study_points <= 0) {
            setstudy_pointsError("Study points must be greater than 0.");
            return;
        }
        
        try {
            setIsSubmitting(true);
            
            await addCourse({ name, study_points });
            
            setSuccessMessage("Course added successfully.");
        } catch (error) {
            setErrorMessage("An error occurred while adding the course.");
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <label>
                    Name:
                    <input type="text" value={name} onChange={(e) => setName(e.target.value)} />
                </label>
                <label>
                    Study Points:
                    <input type="number" value={study_points} onChange={(e) => setstudy_points(Number(e.target.value))} />
                </label>
                <button type="submit">Add Course</button>
            </form>
            {errorMessage && <p>{errorMessage}</p>}
            {successMessage && <p>{successMessage}</p>}
        </div>
    )
}

export default AddCourseOverview;