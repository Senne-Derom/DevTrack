"use client";

import React from "react";
import Link from "next/link";
import { usePathname } from "next/navigation";

const NAV_LINKS = [
  { href: "/", label: "Home" },
  { href: "/study-progress", label: "Study Progress" },
  { href: "/register", label: "Register" },
  {href: "/login", label: "Login"}
];

const Header: React.FC = () => {
  const pathname = usePathname();

  return (
    <header className="site-header panel">
      {NAV_LINKS.map((link) => {
        const isActive = pathname === link.href;
        return (
          <Link
            key={link.href}
            href={link.href}
            className={`nav-link${isActive ? " nav-link-active" : ""}`}
          >
            {link.label}
          </Link>
        );
      })}
    </header>
  );
};

export default Header;
