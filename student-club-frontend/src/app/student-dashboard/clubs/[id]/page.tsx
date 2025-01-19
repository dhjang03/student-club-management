'use client';

import { useState, useEffect } from 'react';
import { useParams } from 'next/navigation';
import { getClubById, getClubEvents, getAllMembers, getClubFunding, getAllVenues } from '@/api/dashboard';
import { Club, Event, ClubMember, Funding, Venue } from '@/types/dashboard';
import { FundingSection } from '@/components/sections/FundingSection';
import { MembersSection } from '@/components/sections/MembersSection';
import { ClubEventsSection } from '@/components/sections/ClubEventsSection';
import { Heading } from '@/components/heading';
import { getToken } from '@/utils/auth';

export default function StudentClubsPage() {
  const params = useParams();
  const clubId = Number(params.id);
  const token = getToken();
  const isAdmin = true; // Placeholder

  const [club, setClub] = useState<Club | null>(null);
  const [events, setEvents] = useState<Event[]>([]);
  const [members, setMembers] = useState<ClubMember[]>([]);
  const [funding, setFunding] = useState<Funding | null>(null);
  const [venues, setVenues] = useState<Venue[]>([]);

  useEffect(() => {
    async function fetchData() {
      if (!token) return;
      try {
        console.log('Fetching data for club:', clubId);
        console.log('Token:', token);
        const [clubData, eventsData, membersData, venuesData] = await Promise.all([
          getClubById(clubId, token),
          getClubEvents(clubId, token),
          getAllMembers(clubId, token),
          getAllVenues(),
        ]);
        setClub(clubData);
        setEvents(eventsData);
        setMembers(membersData);
        setVenues(venuesData);
      } catch (error) {
        console.error(error);
      }
    }
    fetchData();
  }, [clubId, token]);

  useEffect(() => {
    async function fetchFunding() {
      if (!token) return;
      try {
        const fundingData = await getClubFunding(clubId, token);
        setFunding(fundingData);
      } catch (error) {
        console.error('Failed to fetch funding data:', error);
        setFunding(null);
      }
    }
    fetchFunding();
  }, [clubId, token]);

  return (
    <>
      <div className="mb-8">
        <Heading>{club?.name || 'Loading...'}</Heading>
        {club && (
          <div className="mt-4">
            <p className="text-lg text-gray-300">{club.description}</p>
            <p className="text-base text-gray-300 font-semibold mt-2">
              Funds Available: {club.funds ? `$${club.funds}` : 'N/A'}
            </p>
          </div>
        )}
      </div>

      <div className="flex flex-col md:flex-row gap-8 mb-8">
        <FundingSection 
          clubId={clubId}
          funding={funding}
          isAdmin={isAdmin}
          token={token} />
        <MembersSection 
          clubId={clubId}
          members={members}
          isAdmin={isAdmin}
          token={token} />
      </div>

      <ClubEventsSection 
        clubId={clubId}
        events={events} 
        venues={venues} 
        isAdmin={isAdmin}
        token={token} />
    </>
  );
}
