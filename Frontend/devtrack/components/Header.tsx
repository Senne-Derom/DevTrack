"use client";

import React from "react";
import Link from "next/link";

const Header: React.FC = () => {
    return (
        <header>
            <Link href="/addStudy">Add Study</Link>
        </header>
    )
}

export default Header;
