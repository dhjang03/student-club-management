'use client'

import type React from 'react'
import { StudentDashboardLayout } from './application-layout'
import { ToastContainer } from 'react-toastify'


export default function StudentLayout({ children }: { children: React.ReactNode }) {
  return (
    <>
      <StudentDashboardLayout>{children}</StudentDashboardLayout>
      <ToastContainer />
    </>
  )
}
