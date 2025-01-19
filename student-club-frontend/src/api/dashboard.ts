import { api } from '@/api/axios';
import {
  Club,
  ClubMember,
  Venue,
  Event,
  Funding,
  Rsvp,
  User,
} from '@/types/dashboard';

export const getMyProfile = async (): Promise<User> => {
  const response = await api.get<User>('/api/v1/user');
  return response.data;
};

export const getAllClubs = async (): Promise<Club[]> => {
  const response = await api.get<Club[]>('/api/v1/clubs');
  return response.data;
};

export const getMyClubs = async (): Promise<Club[]> => {
  const response = await api.get<Club[]>('/api/v1/clubs/my');
  return response.data;
};

export const getClubById = async (id: number): Promise<Club> => {
  const response = await api.get<Club>(`/api/v1/clubs/${id}`);
  return response.data;
};

export const getClubEvents = async (clubId: number): Promise<Event[]> => {
  const response = await api.get<Event[]>(`/api/v1/clubs/${clubId}/events`);
  return response.data;
};

export const getClubEventById = async (
  clubId: number,
  eventId: number
): Promise<Event> => {
  const response = await api.get<Event>(`/api/v1/clubs/${clubId}/events/${eventId}`);
  return response.data;
};

export const createClubEvent = async (
  clubId: number,
  eventData: Event
): Promise<Event> => {
  const response = await api.post<Event>(`/api/v1/clubs/${clubId}/events`, eventData);
  return response.data;
};

export const updateClubEvent = async (
  clubId: number,
  eventData: Event
): Promise<Event> => {
  const response = await api.put<Event>(`/api/v1/clubs/${clubId}/events`, eventData);
  return response.data;
};

export const deleteClubEvent = async (
  clubId: number,
  eventId: number
): Promise<void> => {
  await api.delete<void>(`/api/v1/clubs/${clubId}/events/${eventId}`);
};

export const getAllFundings = async (): Promise<Funding[]> => {
  const response = await api.get<Funding[]>('/api/v1/fundings');
  return response.data;
}

export const updateFundingById = async (): Promise<Funding[]> => {
}

export const getClubFunding = async (clubId: number): Promise<Funding | null> => {
  const response = await api.get<Funding>(`/api/v1/clubs/${clubId}/funding`);
  return response.data;
};

export const getClubFundingByFundingId = async (
  clubId: number,
  fundingId: number
): Promise<Funding> => {
  const response = await api.get<Funding>(
    `/api/v1/clubs/${clubId}/funding/${fundingId}`
  );
  return response.data;
};

export const createFunding = async (
  clubId: number,
  fundingData: Funding
): Promise<Funding> => {
  const response = await api.post<Funding>(
    `/api/v1/clubs/${clubId}/funding`,
    fundingData
  );
  return response.data;
};

export const updateFundingByClubId = async (
  clubId: number,
  fundingData: Funding
): Promise<Funding> => {
  const response = await api.put<Funding>(
    `/api/v1/clubs/${clubId}/funding`,
    fundingData
  );
  return response.data;
};

export const deleteFunding = async (
  clubId: number,
  fundingId: number
): Promise<void> => {
  await api.delete<void>(`/api/v1/clubs/${clubId}/funding/${fundingId}`);
};

export const getAllMembers = async (clubId: number): Promise<ClubMember[]> => {
  const response = await api.get<ClubMember[]>(`/api/v1/clubs/${clubId}/members`);
  return response.data;
};

export const promoteToAdmin = async (
  clubId: number,
  memberDTO: ClubMember
): Promise<void> => {
  await api.put(`/api/v1/clubs/${clubId}/members/promote`, memberDTO);
};

export const demoteToMember = async (
  clubId: number,
  memberDTO: ClubMember
): Promise<void> => {
  await api.put(`/api/v1/clubs/${clubId}/members/demote`, memberDTO);
};

export const getAllEvents = async (): Promise<Event[]> => {
  const response = await api.get<Event[]>('/api/v1/events');
  return response.data;
};

export const getEventById = async (eventId: number): Promise<Event> => {
  const response = await api.get<Event>(`/api/v1/events/${eventId}`);
  return response.data;
};

export const searchEvents = async (keyword: string): Promise<Event[]> => {
  const response = await api.get<Event[]>('/api/v1/events/search', {
    params: { keyword },
  });
  return response.data;
};

export const createRsvp = async (
  eventId: number,
  rsvpData: Rsvp
): Promise<Rsvp> => {
  const response = await api.post<Rsvp>(`/api/v1/events/${eventId}/rsvp`, rsvpData);
  return response.data;
};

export const getMyRsvps = async (): Promise<Rsvp[]> => {
  const response = await api.get<Rsvp[]>('/api/v1/rsvps');
  return response.data;
};

export const getRsvpById = async (rsvpId: number): Promise<Rsvp> => {
  const response = await api.get<Rsvp>(`/api/v1/rsvps/${rsvpId}`);
  return response.data;
};

export const deleteRsvp = async (rsvpId: number): Promise<void> => {
  await api.delete<void>(`/api/v1/rsvps/${rsvpId}`);
};

export const getAllVenues = async (): Promise<Venue[]> => {
  const response = await api.get<Venue[]>('/api/v1/venues');
  return response.data;
};
