import React from "react";
import Link from "next/link";

const Header: React.FC = () => {
  return (
    <header>
      <Link href="/">Home</Link>
      <Link href="/study-progress">Study Progress</Link>
    </header>
  );
};

export default Header;
