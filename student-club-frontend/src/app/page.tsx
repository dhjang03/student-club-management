import React from 'react';
import Link from 'next/link';

export default function Home() {
  return (
    <div>
      <h2>Home</h2>
        <div>
          {/* <Link href="/signin" legacyBehavior>signin</Link> */}
        </div>
        <div>
          <Link href="/student" legacyBehavior>student</Link>
        </div>
        <div>
          <Link href="/student-admin" legacyBehavior>admin-dashboard</Link>
        </div>
        <div>
          <Link href="/staff-admin" legacyBehavior>staff-dashboard</Link>
        </div>
    </div>
  );
}
