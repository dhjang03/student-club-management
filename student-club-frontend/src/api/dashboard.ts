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

export const getMyProfile = async (token: string): Promise<User> => {
    const response = await api.get<User>('/api/v1/user', {
        headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
}

export const getAllClubs = async (): Promise<Club[]> => {
    const response = await api.get<Club[]>('/api/v1/clubs');
    return response.data;
};

export const getMyClubs = async (token: string): Promise<Club[]> => {
    const response = await api.get<Club[]>('/api/v1/clubs/my', {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
    return response.data;
};

export const getClubById = async (id: number, token: string): Promise<Club> => {
    const response = await api.get<Club>(`/api/v1/clubs/${id}`, {
        headers: { 
            Authorization: `Bearer ${token}` 
        },
    });
    console.log(response);
    return response.data;
};

export const getClubEvents = async (clubId: number, token: string): Promise<Event[]> => {
    const response = await api.get<Event[]>(`/api/v1/clubs/${clubId}/events`, {
        headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
};

export const getClubEventById = async (
    clubId: number,
    eventId: number,
    token: string
): Promise<Event> => {
    const response = await api.get<Event>(`/api/v1/clubs/${clubId}/events/${eventId}`, {
        headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
};

export const createClubEvent = async (
    clubId: number,
    eventData: Event,
    token: string
): Promise<Event> => {
    const response = await api.post<Event>(`/api/v1/clubs/${clubId}/events`, eventData, {
        headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
};

export const updateClubEvent = async (
    clubId: number,
    eventData: Event,
    token: string
): Promise<Event> => {
    const response = await api.put<Event>(`/api/v1/clubs/${clubId}/events`, eventData, {
        headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
};

export const deleteClubEvent = async (
    clubId: number,
    eventId: number,
    token: string
): Promise<void> => {
    await api.delete<void>(`/api/v1/clubs/${clubId}/events/${eventId}`, {
        headers: { Authorization: `Bearer ${token}` },
    });
};

export const getClubFunding = async (
    clubId: number,
    token: string
): Promise<Funding> => {
    const response = await api.get<Funding>(`/api/v1/clubs/${clubId}/funding`, {
        headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
};

export const getClubFundingByFundingId = async (
    clubId: number,
    fundingId: number,
    token: string
): Promise<Funding> => {
    const response = await api.get<Funding>(
        `/api/v1/clubs/${clubId}/funding/${fundingId}`,
        {
            headers: { Authorization: `Bearer ${token}` },
        }
    );
    return response.data;
};

export const createFunding = async (
    clubId: number,
    fundingData: Funding,
    token: string
): Promise<Funding> => {
    const response = await api.post<Funding>(
        `/api/v1/clubs/${clubId}/funding`,
        fundingData,
        {
            headers: { Authorization: `Bearer ${token}` },
        }
    );
    return response.data;
};

export const updateFunding = async (
    clubId: number,
    fundingData: Funding,
    token: string
): Promise<Funding> => {
    const response = await api.put<Funding>(
        `/api/v1/clubs/${clubId}/funding`,
        fundingData,
        {
            headers: { Authorization: `Bearer ${token}` },
        }
    );
    return response.data;
};

export const deleteFunding = async (
    clubId: number,
    fundingId: number,
    token: string
): Promise<void> => {
    await api.delete<void>(`/api/v1/clubs/${clubId}/funding/${fundingId}`, {
        headers: { Authorization: `Bearer ${token}` },
    });
};

export const getAllMembers = async (
    clubId: number,
    token: string
): Promise<ClubMember[]> => {
    const response = await api.get<ClubMember[]>(`/api/v1/clubs/${clubId}/members`, {
        headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
};

export const promoteToAdmin = async (
    clubId: number,
    memberDTO: ClubMember,
    token: string
): Promise<void> => {
    await api.put(
        `/api/v1/clubs/${clubId}/members/promote`,
        memberDTO,
        {
            headers: { Authorization: `Bearer ${token}` },
        }
    );
};

export const demoteToMember = async (
    clubId: number,
    memberDTO: ClubMember,
    token: string
): Promise<void> => {
    await api.put(
        `/api/v1/clubs/${clubId}/members/demote`,
        memberDTO,
        {
            headers: { Authorization: `Bearer ${token}` },
        }
    );
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
    rsvpData: Rsvp,
    token: string
): Promise<Rsvp> => {
    const response = await api.post<Rsvp>(`/api/v1/events/${eventId}/rsvp`, rsvpData, {
        headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
};

export const getMyRsvps = async (token: string): Promise<Rsvp[]> => {
    const response = await api.get<Rsvp[]>('/api/v1/rsvps', {
        headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
};

export const getRsvpById = async (rsvpId: number, token: string): Promise<Rsvp> => {
    const response = await api.get<Rsvp>(`/api/v1/rsvps/${rsvpId}`, {
        headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
};

export const deleteRsvp = async (rsvpId: number, token: string): Promise<void> => {
    await api.delete<void>(`/api/v1/rsvps/${rsvpId}`, {
        headers: { Authorization: `Bearer ${token}` },
    });
};

export const getAllVenues = async (): Promise<Venue[]> => {
    const response = await api.get<Venue[]>('/api/v1/venues');
    return response.data;
};