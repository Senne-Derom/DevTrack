import {getStudyEntries} from "@/services/StudyEntryService";

export default async function StudyEntryOverview() {
    const studyEntries = await getStudyEntries();

      return (
    <div className="course-progress-overview">
        <div className="table-wrapper">
          <table className="course-progress-table">
            <thead>
              <tr>
                <th>Course</th>
                <th>Date</th>
                <th>Description</th>
                <th>Time spent</th>
              </tr>
            </thead>
            <tbody>
              {studyEntries.length === 0 ? (
                <tr>
                  <td colSpan={4}>No study entries found.</td>
                </tr>
              ) : (
                studyEntries.map((studyEntry) => (
                  <tr key={`${studyEntry.courseName}-${studyEntry.date.toString()}-${studyEntry.description}`}>
                    <td>{studyEntry.courseName}</td>
                    <td>{studyEntry.date.toString()}</td>
                    <td>{studyEntry.description}</td>
                    <td>{studyEntry.timeSpent} hours</td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
    </div>
  );
}