"use client";

import React from "react";
import Link from "next/link";

const Header: React.FC = () => {
  return (
    <header>
      <Link href="/">Home</Link>
      <Link href="/addStudy">Add Study</Link>
      <Link href="/studyProgress">Study Progress</Link>
    </header>
  );
};

export default Header;
