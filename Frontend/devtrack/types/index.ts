export type StudyEntry = {
  id: number;
  course: Course;
  date: string;
  timeSpent: number;
  description: string;
  courseName: string;
};

export type Course = {
  id?: number;
  name: string;
  study_points: number;
  studyEntries?: StudyEntry[];
};
