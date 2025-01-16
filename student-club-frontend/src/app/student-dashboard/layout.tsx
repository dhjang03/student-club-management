import { getEvents } from '@/data'
import type { Metadata } from 'next'
import type React from 'react'
import { StudentDashboardLayout } from './application-layout'

export const metadata: Metadata = {
  title: {
    template: '%s - SCM',
    default: 'Student Club Management',
  },
  description: '',
}

export default async function StudentLayout({ children }: { children: React.ReactNode }) {
  let events = await getEvents()

  return (
    <StudentDashboardLayout events={events}>{children}</StudentDashboardLayout>
  )
}
