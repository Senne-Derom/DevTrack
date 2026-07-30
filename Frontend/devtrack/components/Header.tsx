"use client";

import React from "react";
import Link from "next/link";

const Header: React.FC = () => {
  return (
    <header>
      <Link href="/">Home</Link>
      <Link href="/studyProgress">Study Progress</Link>
      <Link href="/addStudy">Add StudyEntry</Link>
      <Link href="/addCourse">Add Course</Link>
    </header>
  );
};

export default Header;
