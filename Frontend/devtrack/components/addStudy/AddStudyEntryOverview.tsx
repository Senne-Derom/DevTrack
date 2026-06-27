"use client";

import React, { useContext, useState } from "react";
import {StudyEntry} from "../../types";

interface Props {
    studyEntries: StudyEntry[];
}

const AddStudyEntryOverview: React.fc = () => {
    const [course, setCourse] = useState("");

    return (
        <form>
            <div>
                <label htmlFor="courseInput">Select course:</label>
                <select id="courseInput">
                    <option value="">--Select a course--</option>

                </select>

            </div>
        </form>
    )
}

