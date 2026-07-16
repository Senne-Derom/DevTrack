import { getStudyEntries } from "@/services/StudyService";
import { StudyEntry } from "@/types";
import { useEffect, useState } from "react";

const StudyEntryOverview: React.FC = () => {
  const [studyEntries, setStudyEntries] = useState<StudyEntry[]>([]);
  const [studyEntriesError, setStudyEntriesError] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [isLoadingStudyEntries, setIsLoadingStudyEntries] = useState(false);

  useEffect(() => {
    const loadStudyEntries = async () => {
      try {
        setIsLoadingStudyEntries(true);
        setStudyEntriesError("");

        const loadedStudyEntries = await getStudyEntries();
        setStudyEntries(loadedStudyEntries);
      } catch (error) {
        setStudyEntriesError(
          error instanceof Error
            ? error.message
            : "Failed to load study entries",
        );
      } finally {
        setIsLoadingStudyEntries(false);
      }
    };

    loadStudyEntries();
  }, []);

  return (
    <div>
      {studyEntries.map((studyEntry) => (
        <>
          <h3>{studyEntry.courseName}</h3>
          <p>{studyEntry.date.toString()}</p>
          <p>{studyEntry.description}</p>
          <p>{studyEntry.timeSpent} hours</p>
        </>
      ))}
    </div>
  );
};

export default StudyEntryOverview;
