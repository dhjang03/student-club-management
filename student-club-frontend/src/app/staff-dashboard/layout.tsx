'use client'

import type React from 'react'
import { StaffDashboardLayout } from './application-layout'
import { ToastContainer } from 'react-toastify'


export default function StudentLayout({ children }: { children: React.ReactNode }) {
  return (
    <>
      <StaffDashboardLayout>{children}</StaffDashboardLayout>
      <ToastContainer />
    </>
  )
}
