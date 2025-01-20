import { api } from '@/api/axios';
import {
  Club,
  ClubMember,
  Venue,
  Event,
  Funding,
  Rsvp,
  User,
  ApplicationStatus,
} from '@/types/dashboard';

/**
 * Fetches the profile of the currently authenticated user.
 * @returns {Promise<User>} A promise that resolves to the user's profile.
 */
export const getMyProfile = async (): Promise<User> => {
  const response = await api.get<User>('/api/v1/user');
  return response.data;
};

/**
 * Retrieves a list of all clubs.
 * @returns {Promise<Club[]>} A promise that resolves to an array of clubs.
 */
export const getAllClubs = async (): Promise<Club[]> => {
  const response = await api.get<Club[]>('/api/v1/clubs');
  return response.data;
};

/**
 * Retrieves a list of clubs that the current user is a member of.
 * @returns {Promise<Club[]>} A promise that resolves to an array of clubs the user belongs to.
 */
export const getMyClubs = async (): Promise<Club[]> => {
  const response = await api.get<Club[]>('/api/v1/clubs/my');
  return response.data;
};

/**
 * Fetches details of a specific club by its ID.
 * @param {number} id - The ID of the club.
 * @returns {Promise<Club>} A promise that resolves to the club details.
 */
export const getClubById = async (id: number): Promise<Club> => {
  const response = await api.get<Club>(`/api/v1/clubs/${id}`);
  return response.data;
};

/**
 * Retrieves all events associated with a specific club.
 * @param {number} clubId - The ID of the club.
 * @returns {Promise<Event[]>} A promise that resolves to an array of events for the club.
 */
export const getClubEvents = async (clubId: number): Promise<Event[]> => {
  const response = await api.get<Event[]>(`/api/v1/clubs/${clubId}/events`);
  return response.data;
};

/**
 * Fetches details for a specific event of a club.
 * @param {number} clubId - The ID of the club.
 * @param {number} eventId - The ID of the event.
 * @returns {Promise<Event>} A promise that resolves to the event details.
 */
export const getClubEventById = async (
  clubId: number,
  eventId: number
): Promise<Event> => {
  const response = await api.get<Event>(`/api/v1/clubs/${clubId}/events/${eventId}`);
  return response.data;
};

/**
 * Creates a new event for a specific club.
 * @param {number} clubId - The ID of the club.
 * @param {Event} eventData - The data for the new event.
 * @returns {Promise<Event>} A promise that resolves to the newly created event.
 */
export const createClubEvent = async (
  clubId: number,
  eventData: Event
): Promise<Event> => {
  const response = await api.post<Event>(`/api/v1/clubs/${clubId}/events`, eventData);
  return response.data;
};

/**
 * Updates an existing event for a specific club.
 * @param {number} clubId - The ID of the club.
 * @param {Event} eventData - The updated data for the event.
 * @returns {Promise<Event>} A promise that resolves to the updated event.
 */
export const updateClubEvent = async (
  clubId: number,
  eventData: Event
): Promise<Event> => {
  const response = await api.put<Event>(`/api/v1/clubs/${clubId}/events`, eventData);
  return response.data;
};

/**
 * Deletes a specific event from a club.
 * @param {number} clubId - The ID of the club.
 * @param {number} eventId - The ID of the event to delete.
 * @returns {Promise<void>} A promise that resolves when the event is deleted.
 */
export const deleteClubEvent = async (
  clubId: number,
  eventId: number
): Promise<void> => {
  await api.delete<void>(`/api/v1/clubs/${clubId}/events/${eventId}`);
};

/**
 * Retrieves all funding opportunities.
 * @returns {Promise<Funding[]>} A promise that resolves to an array of fundings.
 */
export const getAllFundings = async (): Promise<Funding[]> => {
  const response = await api.get<Funding[]>('/api/v1/fundings');
  return response.data;
};

/**
 * Updates the status of a specific funding application.
 * @param {number} fundingId - The ID of the funding.
 * @param {ApplicationStatus} newStatus - The new status to apply.
 * @returns {Promise<Funding>} A promise that resolves to the updated funding.
 */
export const updateFundingStatus = async (
  fundingId: number,
  newStatus: ApplicationStatus
): Promise<Funding> => {
  const response = await api.put<Funding>(`/api/v1/fundings/${fundingId}`, {
    status: newStatus
  });
  return response.data;
};

/**
 * Retrieves funding information for a specific club, if available.
 * @param {number} clubId - The ID of the club.
 * @returns {Promise<Funding | null>} A promise that resolves to the funding or null if not found.
 */
export const getClubFunding = async (clubId: number): Promise<Funding | null> => {
  const response = await api.get<Funding>(`/api/v1/clubs/${clubId}/funding`);
  return response.data;
};

/**
 * Fetches a specific funding entry for a given club by funding ID.
 * @param {number} clubId - The ID of the club.
 * @param {number} fundingId - The ID of the funding.
 * @returns {Promise<Funding>} A promise that resolves to the funding details.
 */
export const getClubFundingByFundingId = async (
  clubId: number,
  fundingId: number
): Promise<Funding> => {
  const response = await api.get<Funding>(
    `/api/v1/clubs/${clubId}/funding/${fundingId}`
  );
  return response.data;
};

/**
 * Creates a new funding entry for a specific club.
 * @param {number} clubId - The ID of the club.
 * @param {Funding} fundingData - The data for the new funding.
 * @returns {Promise<Funding>} A promise that resolves to the newly created funding.
 */
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

/**
 * Updates an existing funding entry for a specific club.
 * @param {number} clubId - The ID of the club.
 * @param {Funding} fundingData - The updated funding data.
 * @returns {Promise<Funding>} A promise that resolves to the updated funding.
 */
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

/**
 * Deletes a specific funding entry for a club.
 * @param {number} clubId - The ID of the club.
 * @param {number} fundingId - The ID of the funding to delete.
 * @returns {Promise<void>} A promise that resolves when the funding is deleted.
 */
export const deleteFunding = async (
  clubId: number,
  fundingId: number
): Promise<void> => {
  await api.delete<void>(`/api/v1/clubs/${clubId}/funding/${fundingId}`);
};

/**
 * Retrieves a list of all members for a specific club.
 * @param {number} clubId - The ID of the club.
 * @returns {Promise<ClubMember[]>} A promise that resolves to an array of club members.
 */
export const getAllMembers = async (clubId: number): Promise<ClubMember[]> => {
  const response = await api.get<ClubMember[]>(`/api/v1/clubs/${clubId}/members`);
  return response.data;
};

/**
 * Promotes a club member to an admin role.
 * @param {number} clubId - The ID of the club.
 * @param {ClubMember} memberDTO - The club member data to promote.
 * @returns {Promise<void>} A promise that resolves when the promotion is complete.
 */
export const promoteToAdmin = async (
  clubId: number,
  memberDTO: ClubMember
): Promise<void> => {
  await api.put(`/api/v1/clubs/${clubId}/members/promote`, memberDTO);
};

/**
 * Demotes a club admin back to a regular member role.
 * @param {number} clubId - The ID of the club.
 * @param {ClubMember} memberDTO - The club member data to demote.
 * @returns {Promise<void>} A promise that resolves when the demotion is complete.
 */
export const demoteToMember = async (
  clubId: number,
  memberDTO: ClubMember
): Promise<void> => {
  await api.put(`/api/v1/clubs/${clubId}/members/demote`, memberDTO);
};

/**
 * Retrieves a list of all events across clubs.
 * @returns {Promise<Event[]>} A promise that resolves to an array of events.
 */
export const getAllEvents = async (): Promise<Event[]> => {
  const response = await api.get<Event[]>('/api/v1/events');
  return response.data;
};

/**
 * Fetches details for a specific event by its ID.
 * @param {number} eventId - The ID of the event.
 * @returns {Promise<Event>} A promise that resolves to the event details.
 */
export const getEventById = async (eventId: number): Promise<Event> => {
  const response = await api.get<Event>(`/api/v1/events/${eventId}`);
  return response.data;
};

/**
 * Searches for events based on a given keyword.
 * @param {string} keyword - The keyword to search events by.
 * @returns {Promise<Event[]>} A promise that resolves to an array of events matching the keyword.
 */
export const searchEvents = async (keyword: string): Promise<Event[]> => {
  const response = await api.get<Event[]>('/api/v1/events/search', {
    params: { keyword },
  });
  return response.data;
};

/**
 * Creates a new RSVP for a specific event.
 * @param {number} eventId - The ID of the event.
 * @param {Rsvp} rsvpData - The RSVP data.
 * @returns {Promise<Rsvp>} A promise that resolves to the created RSVP.
 */
export const createRsvp = async (
  eventId: number,
  rsvpData: Rsvp
): Promise<Rsvp> => {
  const response = await api.post<Rsvp>(`/api/v1/events/${eventId}/rsvp`, rsvpData);
  return response.data;
};

/**
 * Retrieves a list of RSVPs for the current user.
 * @returns {Promise<Rsvp[]>} A promise that resolves to an array of RSVPs made by the user.
 */
export const getMyRsvps = async (): Promise<Rsvp[]> => {
  const response = await api.get<Rsvp[]>('/api/v1/rsvps');
  return response.data;
};

/**
 * Fetches details for a specific RSVP by its ID.
 * @param {number} rsvpId - The ID of the RSVP.
 * @returns {Promise<Rsvp>} A promise that resolves to the RSVP details.
 */
export const getRsvpById = async (rsvpId: number): Promise<Rsvp> => {
  const response = await api.get<Rsvp>(`/api/v1/rsvps/${rsvpId}`);
  return response.data;
};

/**
 * Deletes a specific RSVP by its ID.
 * @param {number} rsvpId - The ID of the RSVP to delete.
 * @returns {Promise<void>} A promise that resolves when the RSVP is deleted.
 */
export const deleteRsvp = async (rsvpId: number): Promise<void> => {
  await api.delete<void>(`/api/v1/rsvps/${rsvpId}`);
};

/**
 * Retrieves a list of all venues.
 * @returns {Promise<Venue[]>} A promise that resolves to an array of venues.
 */
export const getAllVenues = async (): Promise<Venue[]> => {
  const response = await api.get<Venue[]>('/api/v1/venues');
  return response.data;
};
