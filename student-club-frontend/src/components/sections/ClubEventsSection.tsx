import { useState } from 'react';
import { Button } from '@/components/button';
import { Heading } from '@/components/heading';
import { Divider } from '@/components/divider';
import { Dropdown, DropdownButton, DropdownItem, DropdownMenu } from '@/components/dropdown';
import { EllipsisVerticalIcon } from '@heroicons/react/16/solid';
import { Link } from '@/components/link';
import { ClubEventModal } from '@/components/modals/ClubEventModal';
import { createClubEvent, updateClubEvent, deleteClubEvent } from '@/api/dashboard';
import { Event, Venue } from '@/types/dashboard';

interface ClubEventsSectionProps {
  clubId: number;
  events: Event[];
  venues: Venue[];
  isAdmin: boolean;
  token: string | null;
}

export function ClubEventsSection({ clubId, events, venues, isAdmin, token }: ClubEventsSectionProps) {
  const [isModalOpen, setModalOpen] = useState(false);
  const [editingEvent, setEditingEvent] = useState<Event | null>(null);

  const handleCreate = () => {
    setEditingEvent(null);
    setModalOpen(true);
  };

  const handleEdit = (event: Event) => {
    setEditingEvent(event);
    setModalOpen(true);
  };

  const handleDelete = (eventId: number) => {
    if (!token) return;
    deleteClubEvent(clubId, eventId, token);
    window.location.reload();
  };

  const handleSubmit = (data: Event) => {
    if (!token) return;

    if (editingEvent) {
      updateClubEvent(clubId, data, token);
    } else {
      createClubEvent(clubId, data, token);
    }

    setModalOpen(false);
    window.location.reload();
  };

  return (
    <div>
      <div className="flex flex-wrap items-end justify-between gap-4">
        <div className="flex items-center justify-between w-full">
          <Heading level={2}>Club Events</Heading>
          {isAdmin && <Button onClick={handleCreate}>Create Event</Button>}
        </div>
      </div>
      <ul className="mt-10">
        {events.length > 0 ? (
          events.map((event, index) => (
            <li key={event.id}>
              {index > 0 && <Divider />}
              <div className="flex items-center justify-between py-6">
                <div className="flex gap-6">
                  <div className="space-y-1.5">
                    <div className="text-lg text-gray-100">
                      {event.title}
                    </div>
                    <div className="text-base text-gray-300">
                      {event.date} at {event.venue.name}
                    </div>
                    <div className="text-base text-gray-300">
                      {event.description}
                    </div>
                    <div className="text-base text-gray-400">
                      Cost: ${event.cost} · Capacity: {event.capacity} · Attendees: {event.numOfAttendees}
                    </div>
                  </div>
                </div>
                <div className="flex items-center gap-4">
                  {isAdmin && (
                    <Dropdown>
                      <DropdownButton plain aria-label="More options">
                        <EllipsisVerticalIcon />
                      </DropdownButton>
                      <DropdownMenu anchor="bottom end">
                        <DropdownItem onClick={() => handleEdit(event)}>Edit</DropdownItem>
                        <DropdownItem onClick={() => event.id !== null && handleDelete(event.id)}>Delete</DropdownItem>
                      </DropdownMenu>
                    </Dropdown>
                  )}
                </div>
              </div>
            </li>
          ))
        ) : (
          <p className="text-sm text-gray-500">No events found.</p>
        )}
      </ul>
      <ClubEventModal
        isOpen={isModalOpen}
        onClose={() => setModalOpen(false)}
        onSubmit={handleSubmit}
        eventData={editingEvent || undefined}
        venues={venues}
      />
    </div>
  );
}
