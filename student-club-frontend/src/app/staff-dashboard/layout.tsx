'use client'

import type React from 'react'
import { StaffDashboardLayout } from './application-layout'
import { ToastContainer } from 'react-toastify'
import ProtectedRoute from '@/hocs/ProtectedRoutes'


export default function StudentLayout({ children }: { children: React.ReactNode }) {
  return (
    <ProtectedRoute>
      <StaffDashboardLayout>{children}</StaffDashboardLayout>
      <ToastContainer />
    </ProtectedRoute>
  )
}
