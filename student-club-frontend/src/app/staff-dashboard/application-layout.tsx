'use client'

import React, { useEffect, useState } from 'react';
import { usePathname, useRouter } from 'next/navigation';
import {
  Dropdown,
  DropdownButton,
  DropdownDivider,
  DropdownItem,
  DropdownLabel,
  DropdownMenu,
} from '@/components/dropdown'
import { Navbar } from '@/components/navbar'
import {
  Sidebar,
  SidebarBody,
  SidebarHeader,
  SidebarItem,
  SidebarLabel,
  SidebarSection,
} from '@/components/sidebar'
import { SidebarLayout } from '@/components/sidebar-layout'
import {
  ChevronDownIcon,
  Cog8ToothIcon,
} from '@heroicons/react/16/solid'
import {
  UserCircleIcon,
  HomeIcon,
} from '@heroicons/react/20/solid'
import {
  User,
} from '@/types/dashboard'
import {
  getMyProfile,
} from '@/api/dashboard'
import { clearToken } from '@/utils/auth';


export function StaffDashboardLayout({
  children,
}: {
  children: React.ReactNode
}) {
  const pathname = usePathname();
  const router = useRouter();
  const [userName, setUserName] = useState(''); 

  useEffect(() => {
    async function fetchUserData() {
      try {
        const user: User = await getMyProfile();
        setUserName(user.firstName || 'User');
      } catch (error) {
        console.error('Failed to fetch user profile', error);
      }
    }
    fetchUserData();
  }, []);

  const handleLogout = () => {
    clearToken();
    router.push('/');
  };

  return (
    <SidebarLayout
      navbar={<Navbar />}
      sidebar={
        <Sidebar>
          <SidebarHeader>
            <Dropdown>
              <DropdownButton as={SidebarItem}>
                <UserCircleIcon />
                <SidebarLabel> { userName || "Loading..." } </SidebarLabel>
                <ChevronDownIcon />
              </DropdownButton>
              <DropdownMenu className="min-w-80 lg:min-w-64" anchor="bottom start">
                <DropdownItem href="/#">
                  <Cog8ToothIcon />
                  <DropdownLabel>Placeholder</DropdownLabel>
                </DropdownItem>
                <DropdownDivider />
                <DropdownItem href="/" onClick={handleLogout}>        
                  <DropdownLabel>Loggout</DropdownLabel>
                </DropdownItem>
              </DropdownMenu>
            </Dropdown>
          </SidebarHeader>

          <SidebarBody>
            <SidebarSection>
              <SidebarItem
                href="/staff-dashboard"
                current={pathname === '/staff-dashboard'}
              >
                <HomeIcon className="h-5 w-5" />
                <SidebarLabel>Home</SidebarLabel>
              </SidebarItem>
            </SidebarSection>
          </SidebarBody>
        </Sidebar>
      }
    >
      {children}
    </SidebarLayout>
  )
}
