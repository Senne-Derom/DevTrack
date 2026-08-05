// "use client";
//
// import { getStudyEntries } from "@/services/StudyService";
// import { StudyEntry } from "@/types";
// import { useEffect, useState } from "react";
//
// const StudyEntryOverview: React.FC = () => {
//   const [studyEntries, setStudyEntries] = useState<StudyEntry[]>([]);
//   const [studyEntriesError, setStudyEntriesError] = useState("");
//   const [isLoadingStudyEntries, setIsLoadingStudyEntries] = useState(false);
//
//   useEffect(() => {
//     const loadStudyEntries = async () => {
//       try {
//         setIsLoadingStudyEntries(true);
//         setStudyEntriesError("");
//
//         const loadedStudyEntries = await getStudyEntries();
//         setStudyEntries(loadedStudyEntries);
//       } catch (error) {
//         setStudyEntriesError(
//           error instanceof Error
//             ? error.message
//             : "Failed to load study entries",
//         );
//       } finally {
//         setIsLoadingStudyEntries(false);
//       }
//     };
//
//     loadStudyEntries();
//   }, []);
//
//   return (
//     <div className="course-progress-overview">
//       {studyEntriesError && (
//         <p className="status-message status-message-error">{studyEntriesError}</p>
//       )}
//       {isLoadingStudyEntries && <p className="status-message">Loading study entries...</p>}
//       {!isLoadingStudyEntries && (
//         <div className="table-wrapper">
//           <table className="course-progress-table">
//             <thead>
//               <tr>
//                 <th>Course</th>
//                 <th>Date</th>
//                 <th>Description</th>
//                 <th>Time spent</th>
//               </tr>
//             </thead>
//             <tbody>
//               {studyEntries.length === 0 ? (
//                 <tr>
//                   <td colSpan={4}>No study entries found.</td>
//                 </tr>
//               ) : (
//                 studyEntries.map((studyEntry) => (
//                   <tr key={`${studyEntry.courseName}-${studyEntry.date.toString()}-${studyEntry.description}`}>
//                     <td>{studyEntry.courseName}</td>
//                     <td>{studyEntry.date.toString()}</td>
//                     <td>{studyEntry.description}</td>
//                     <td>{studyEntry.timeSpent} hours</td>
//                   </tr>
//                 ))
//               )}
//             </tbody>
//           </table>
//         </div>
//       )}
//     </div>
//   );
// };
//
// export default StudyEntryOverview;
