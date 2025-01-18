'use client';

import { useState } from 'react';
import { Button } from '@/components/button';
import { Heading } from '@/components/heading';
import { Divider } from '@/components/divider';
import { Dropdown, DropdownButton, DropdownItem, DropdownMenu } from '@/components/dropdown';
import { EllipsisVerticalIcon } from '@heroicons/react/16/solid';
import { Link } from '@/components/link';
import { Event } from '@/types/dashboard';
import { ClubEventModal } from '@/components/modals/ClubEventModal';

interface ClubEventsSectionProps {
  events: Event[];
  isAdmin: boolean;
}

export function ClubEventsSection({ events, isAdmin }: ClubEventsSectionProps) {
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
    // TODO: Implement delete logic
  };

  const handleSubmit = (data: Event) => {
    // TODO: Implement create/edit logic and update events list
    setModalOpen(false);
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
                      <Link href={`/student-admin/events/${event.id}`}>{event.title}</Link>
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
                  <Dropdown>
                    <DropdownButton plain aria-label="More options">
                      <EllipsisVerticalIcon />
                    </DropdownButton>
                    <DropdownMenu anchor="bottom end">
                      <DropdownItem onClick={() => handleEdit(event)}>Edit</DropdownItem>
                      <DropdownItem onClick={() => handleDelete(event.id)}>Delete</DropdownItem>
                    </DropdownMenu>
                  </Dropdown>
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
      />
    </div>
  );
}
