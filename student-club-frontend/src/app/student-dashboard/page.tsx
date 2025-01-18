'use client'

import { Heading, Subheading } from '@/components/heading'
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/table'
import { useState, useEffect } from 'react'
import { Club } from '@/types/dashboard'
import { getMyClubs } from '@/api/dashboard'

export default function StudentDashboard() {

  const [clubs, setClubs] = useState<Club[]>([]);
  useEffect(() => {
      async function fetchClubs() {
        const token = localStorage.getItem('token');
        if (!token) return;
        try {
          const userClubs: Club[] = await getMyClubs(token);
          setClubs(userClubs);
        } catch (error) {
          console.error('Failed to fetch clubs', error);
        }
      }
      fetchClubs();
    }, []);

  return (
    <>
      <Heading>Student Clubs</Heading>
      <Subheading className="mt-14">My Clubs</Subheading>
      <Table className="mt-4 [--gutter:theme(spacing.6)] lg:[--gutter:theme(spacing.10)]">
        <TableHead>
          <TableRow>
            <TableHeader>Name</TableHeader>
            <TableHeader></TableHeader>
            <TableHeader className="text-left">Description</TableHeader>
          </TableRow>
        </TableHead>
        <TableBody>
          {clubs.map((club) => (
            <TableRow 
              key={club.id} 
              href={`/student-dashboard/clubs/${club.id}`} 
              title={club.name}>
              <TableCell className="text-500">{club.name}</TableCell>
              <TableCell></TableCell>
              <TableCell className="text-left">{club.description}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </>
  )
}
