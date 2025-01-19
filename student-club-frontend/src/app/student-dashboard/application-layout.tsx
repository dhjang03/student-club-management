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
  Cog6ToothIcon,
  HomeIcon,
  Square2StackIcon,
  TicketIcon,
} from '@heroicons/react/20/solid'
import {
  User,
  Club,
} from '@/types/dashboard'
import {
  getMyProfile,
  getMyClubs,
} from '@/api/dashboard'


export function StudentDashboardLayout({
  children,
}: {
  children: React.ReactNode
}) {
  const pathname = usePathname();
  const router = useRouter();
  const [userName, setUserName] = useState(''); 
  const [showClubs, setShowClubs] = useState(false);
  const [clubs, setClubs] = useState<Club[]>([]);

  useEffect(() => {
    async function fetchUserData() {
      const token = localStorage.getItem('token');
      if (!token) return;
      try {
        const user: User = await getMyProfile(token);
        setUserName(user.firstName || 'User');
      } catch (error) {
        console.error('Failed to fetch user profile', error);
      }
    }
    fetchUserData();
  }, []);

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

  const handleLogout = () => {
    localStorage.removeItem('token');
    router.push('/');
  };

  const toggleClubs = (e: React.MouseEvent) => {
    e.preventDefault();
    setShowClubs((prev) => !prev);
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
                href="/student-dashboard"
                current={pathname === '/student-dashboard'}
              >
                <HomeIcon className="h-5 w-5" />
                <SidebarLabel>Home</SidebarLabel>
              </SidebarItem>

              <SidebarItem
                href="#"
                onClick={toggleClubs}
                current={showClubs}
              >
                <Square2StackIcon className="h-5 w-5" />
                <SidebarLabel>Clubs</SidebarLabel>
              </SidebarItem>
              {showClubs && (
                <div className="ml-6">
                  {clubs.map((club) => (
                    <SidebarItem
                      key={club.id}
                      href={`/student-dashboard/clubs/${club.id}`} 
                      current={false}
                    >
                      <SidebarLabel>{club.name}</SidebarLabel>
                    </SidebarItem>
                  ))}
                </div>
              )}

              <SidebarItem
                href="/student-dashboard/events"
                current={pathname.startsWith('/student-dashboard/events')}
              >
                <Square2StackIcon className="h-5 w-5" />
                <SidebarLabel>Events</SidebarLabel>
              </SidebarItem>
              <SidebarItem
                href="/student-dashboard/orders"
                current={pathname.startsWith('/student-dashboard/orders')}
              >
                <TicketIcon className="h-5 w-5" />
                <SidebarLabel>Orders</SidebarLabel>
              </SidebarItem>
              <SidebarItem
                href="/student-dashboard/settings"
                current={pathname.startsWith('/student-dashboard/settings')}
              >
                <Cog6ToothIcon className="h-5 w-5" />
                <SidebarLabel>Settings</SidebarLabel>
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
