export type StudyEntry = {
    id: number;
    course: Course;
    description: String;
    date: Date;
};

export type Course = {
    id: number;
    name: String;
    studyEntries: StudyEntry[];
};

