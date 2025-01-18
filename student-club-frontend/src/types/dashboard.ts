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


export interface Club {
    id: number;
    name: string;
    description: string;
    funds: number;
}

export interface ClubMember {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    membership: string;
}

export interface Venue {
    id: number | null;
    capacity: number;
    name: string;
    address: string;
}

export interface Event {
    id: number | null;
    title: string;
    description: string;
    date: string;
    venue: Venue;
    cost: number;
    createdAt: string | null;
    capacity: number;
    numOfAttendees: number;
}

export interface Funding {
    id: number | null;
    amount: number;
    description: string;
    createdAt: string | null;
    status: ApplicationStatus;
}

export interface LoginRequest {
    username: string;
    password: string;
}

export interface LoginResponse {
    token: string;
}

export interface RegisterRequest {
    username: string;
    password: string;
    email: string;
    firstName: string;
    lastName: string;
    roles: UserRole[];
}

export interface Ticket {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
}

export interface User {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    roles: UserRole[];
}

export interface Rsvp {
    id: string;
    responder: User;
    event: Event;
    tickets: Ticket[];
    createdAt: string;
}