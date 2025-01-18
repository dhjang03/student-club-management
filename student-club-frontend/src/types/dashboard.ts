export enum UserRole {
    STUDENT = 'ROLE_STUDENT',
    STAFF = 'ROLE_STAFF'
}


export enum ApplicationStatus {
    DRAFT = "DRAFT",
    SUBMITTED = "SUBMITTED",
    IN_REVIEW = "IN_REVIEW",
    APPROVED = "APPROVED",
    REJECTED = "REJECTED"
}


export interface ClubDTO {
    id: number;
    name: string;
    description: string;
    funds: number;
}

export interface ClubMemberDTO {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    membership: string;
}

export interface VenueDTO {
    id: number;
    capacity: number;
    name: string;
    address: string;
}

export interface EventDTO {
    id: number;
    title: string;
    description: string;
    date: string;
    venue: VenueDTO;
    cost: number;
    createdAt: string;
    capacity: number;
    numOfAttendees: number;
}

export interface FundingDTO {
    id: number;
    amount: number;
    description: string;
    createdAt: string;
    status: ApplicationStatus;
}

export interface LoginRequestDTO {
    username: string;
    password: string;
}

export interface LoginResponseDTO {
    token: string;
}

export interface RegisterRequestDTO {
    username: string;
    password: string;
    email: string;
    firstName: string;
    lastName: string;
    roles: UserRole[];
}

export interface TicketDTO {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
}

export interface UserDTO {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    roles: UserRole[];
}

export interface RsvpDTO {
    id: string;
    responder: UserDTO;
    event: EventDTO;
    tickets: TicketDTO[];
    createdAt: string;
}