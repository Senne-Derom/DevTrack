import Link from "next/link";

export default function Home() {
  return (
    <div className="page page-centered">
      <div className="page-header">
        <h1>DevTrack</h1>
        <p className="page-subtitle">
          Track your courses and study hours in one place.
        </p>
      </div>
      <div className="page-actions">
        <Link href="/study-progress" className="button">
          View study progress
        </Link>
        <Link href="/register" className="button">
          Register
        </Link>
      </div>
    </div>
  );
}
