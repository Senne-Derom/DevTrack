export type StudyEntry = {
    id: number;
    course: Course;
    date: Date;
    description: string;
};

export type Course = {
    id: number;
    name: string;
    studyEntries: StudyEntry[];
};

