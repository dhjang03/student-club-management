'use client'

import type React from 'react'
import { StudentDashboardLayout } from './application-layout'
import { ToastContainer } from 'react-toastify'
import ProtectedRoute from '@/hocs/ProtectedRoutes'


export default function StudentLayout({ children }: { children: React.ReactNode }) {
  return (
    <ProtectedRoute>
      <StudentDashboardLayout>{children}</StudentDashboardLayout>
      <ToastContainer />
    </ProtectedRoute>
  )
}
