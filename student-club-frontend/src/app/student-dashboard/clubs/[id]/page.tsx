'use client';

import { useState, useEffect, useMemo, useCallback } from 'react';
import { useParams } from 'next/navigation';
import {
  getClubById,
  getClubEvents,
  getAllMembers,
  getClubFunding,
  getAllVenues,
} from '@/api/dashboard';
import {
  Club,
  Event,
  ClubMember,
  Funding,
  Venue,
  Membership,
} from '@/types/dashboard';
import { FundingSection } from '@/components/sections/FundingSection';
import { MembersSection } from '@/components/sections/MembersSection';
import { ClubEventsSection } from '@/components/sections/ClubEventsSection';
import { Heading } from '@/components/heading';
import { getToken, getUserId } from '@/utils/auth';

export default function StudentClubsPage() {
  const params = useParams();
  const clubId = Number(params.id);
  const token = getToken();

  const [club, setClub] = useState<Club | null>(null);
  const [events, setEvents] = useState<Event[]>([]);
  const [members, setMembers] = useState<ClubMember[]>([]);
  const [funding, setFunding] = useState<Funding | null>(null);
  const [venues, setVenues] = useState<Venue[]>([]);

  const fetchClubData = useCallback(async () => {
    try {
      const clubData = await getClubById(clubId);
      setClub(clubData);
    } catch (error) {
      console.error('Failed to fetch club data:', error);
    }
  }, [clubId]);

  const fetchEventsData = useCallback(async () => {
    try {
      const eventsData = await getClubEvents(clubId);
      setEvents(eventsData);
    } catch (error) {
      console.error('Failed to fetch events data:', error);
    }
  }, [clubId]);

  const fetchMembersData = useCallback(async () => {
    try {
      const membersData = await getAllMembers(clubId);
      setMembers(membersData);
    } catch (error) {
      console.error('Failed to fetch members data:', error);
    }
  }, [clubId]);

  const fetchFundingData = useCallback(async () => {
    try {
      const fundingData = await getClubFunding(clubId);
      setFunding(fundingData);
    } catch (error) {
      console.error('Failed to fetch funding data:', error);
    }
  }, [clubId]);

  const fetchVenuesData = useCallback(async () => {
    try {
      const venuesData = await getAllVenues();
      setVenues(venuesData);
    } catch (error) {
      console.error('Failed to fetch venues data:', error);
    }
  }, []);

  useEffect(() => {
    async function initialLoad() {
      await Promise.all([
        fetchClubData(),
        fetchEventsData(),
        fetchMembersData(),
        fetchVenuesData(),
      ]);
      await fetchFundingData();
    }

    initialLoad();
  }, [
    fetchClubData,
    fetchEventsData,
    fetchMembersData,
    fetchVenuesData,
    fetchFundingData,
  ]);

  const userId = getUserId(token);
  const isAdmin = useMemo(() => {
    return members.some(
      (member) => member.id === userId && member.membership === Membership.ADMIN
    );
  }, [members, userId]);

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
          onRefetchFunding={fetchFundingData}
        />

        <MembersSection
          clubId={clubId}
          members={members}
          isAdmin={isAdmin}
          onRefetchMembers={fetchMembersData}
        />
      </div>

      <ClubEventsSection
        clubId={clubId}
        events={events}
        venues={venues}
        isAdmin={isAdmin}
        onRefetchEvents={fetchEventsData}
        onRefetchClub={fetchClubData}
      />
    </>
  );
}
