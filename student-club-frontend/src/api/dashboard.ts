import axios from 'axios';
import {
    ClubDTO,
    ClubMemberDTO,
    VenueDTO,
    EventDTO,
    FundingDTO,
    RsvpDTO,
} from '@/types/dashboard';


export const getAllClubs = async (): Promise<ClubDTO[]> => {
    const response = await axios.get<ClubDTO[]>('/api/v1/clubs');
    return response.data;
};

export const getMyClubs = async (token: string): Promise<ClubDTO[]> => {
    const response = await axios.get<ClubDTO[]>('/api/v1/clubs/my', {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
    return response.data;
};

export const getClubById = async (id: number): Promise<ClubDTO> => {
    const response = await axios.get<ClubDTO>(`/api/v1/clubs/${id}`);
    return response.data;
};

export const getClubEvents = async (clubId: number, token: string): Promise<EventDTO[]> => {
    const response = await axios.get<EventDTO[]>(`/api/v1/clubs/${clubId}/events`, {
        headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
};

export const getClubEventById = async (
    clubId: number,
    eventId: number,
    token: string
): Promise<EventDTO> => {
    const response = await axios.get<EventDTO>(`/api/v1/clubs/${clubId}/events/${eventId}`, {
        headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
};

export const createClubEvent = async (
    clubId: number,
    eventData: EventDTO,
    token: string
): Promise<EventDTO> => {
    const response = await axios.post<EventDTO>(`/api/v1/clubs/${clubId}/events`, eventData, {
        headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
};

export const updateClubEvent = async (
    clubId: number,
    eventData: EventDTO,
    token: string
): Promise<EventDTO> => {
    const response = await axios.put<EventDTO>(`/api/v1/clubs/${clubId}/events`, eventData, {
        headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
};

export const deleteClubEvent = async (
    clubId: number,
    eventId: number,
    token: string
): Promise<void> => {
    await axios.delete<void>(`/api/v1/clubs/${clubId}/events/${eventId}`, {
        headers: { Authorization: `Bearer ${token}` },
    });
};

export const getClubFunding = async (
    clubId: number,
    token: string
): Promise<FundingDTO> => {
    const response = await axios.get<FundingDTO>(`/api/v1/clubs/${clubId}/funding`, {
        headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
};

export const getClubFundingByFundingId = async (
    clubId: number,
    fundingId: number,
    token: string
): Promise<FundingDTO> => {
    const response = await axios.get<FundingDTO>(
        `/api/v1/clubs/${clubId}/funding/${fundingId}`,
        {
            headers: { Authorization: `Bearer ${token}` },
        }
    );
    return response.data;
};

export const createFunding = async (
    clubId: number,
    fundingData: FundingDTO,
    token: string
): Promise<FundingDTO> => {
    const response = await axios.post<FundingDTO>(
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
    fundingData: FundingDTO,
    token: string
): Promise<FundingDTO> => {
    const response = await axios.put<FundingDTO>(
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
    await axios.delete<void>(`/api/v1/clubs/${clubId}/funding/${fundingId}`, {
        headers: { Authorization: `Bearer ${token}` },
    });
};

export const getAllMembers = async (
    clubId: number,
    token: string
): Promise<ClubMemberDTO[]> => {
    const response = await axios.get<ClubMemberDTO[]>(`/api/v1/clubs/${clubId}/members`, {
        headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
};

export const promoteToAdmin = async (
    clubId: number,
    memberDTO: ClubMemberDTO,
    token: string
): Promise<void> => {
    await axios.put(
        `/api/v1/clubs/${clubId}/members/promote`,
        memberDTO,
        {
            headers: { Authorization: `Bearer ${token}` },
        }
    );
};

export const demoteToMember = async (
    clubId: number,
    memberDTO: ClubMemberDTO,
    token: string
): Promise<void> => {
    await axios.put(
        `/api/v1/clubs/${clubId}/members/demote`,
        memberDTO,
        {
            headers: { Authorization: `Bearer ${token}` },
        }
    );
};

export const getAllEvents = async (): Promise<EventDTO[]> => {
    const response = await axios.get<EventDTO[]>('/api/v1/events');
    return response.data;
};

export const getEventById = async (eventId: number): Promise<EventDTO> => {
    const response = await axios.get<EventDTO>(`/api/v1/events/${eventId}`);
    return response.data;
};

export const searchEvents = async (keyword: string): Promise<EventDTO[]> => {
    const response = await axios.get<EventDTO[]>('/api/v1/events/search', {
        params: { keyword },
    });
    return response.data;
};

export const createRsvp = async (
    eventId: number,
    rsvpData: RsvpDTO,
    token: string
): Promise<RsvpDTO> => {
    const response = await axios.post<RsvpDTO>(`/api/v1/events/${eventId}/rsvp`, rsvpData, {
        headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
};

export const getMyRsvps = async (token: string): Promise<RsvpDTO[]> => {
    const response = await axios.get<RsvpDTO[]>('/api/v1/rsvps', {
        headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
};

export const getRsvpById = async (rsvpId: number, token: string): Promise<RsvpDTO> => {
    const response = await axios.get<RsvpDTO>(`/api/v1/rsvps/${rsvpId}`, {
        headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
};

export const deleteRsvp = async (rsvpId: number, token: string): Promise<void> => {
    await axios.delete<void>(`/api/v1/rsvps/${rsvpId}`, {
        headers: { Authorization: `Bearer ${token}` },
    });
};

export const getAllVenues = async (): Promise<VenueDTO[]> => {
    const response = await axios.get<VenueDTO[]>('/api/v1/venues');
    return response.data;
};