import { useState } from 'react';
import { Button } from '@/components/button';
import { Heading } from '@/components/heading';
import { Divider } from '@/components/divider';
import {
  Dropdown,
  DropdownButton,
  DropdownItem,
  DropdownMenu,
} from '@/components/dropdown';
import { EllipsisVerticalIcon } from '@heroicons/react/16/solid';
import { ClubEventModal } from '@/components/modals/ClubEventModal';
import {
  createClubEvent,
  updateClubEvent,
  deleteClubEvent,
} from '@/api/dashboard';
import { Event, Venue } from '@/types/dashboard';
import { toast } from 'react-toastify';

interface ClubEventsSectionProps {
  clubId: number;
  events: Event[];
  venues: Venue[];
  isAdmin: boolean;
  onRefetchEvents: () => Promise<void>;
  onRefetchClub: () => Promise<void>;
}

export function ClubEventsSection({
  clubId,
  events,
  venues,
  isAdmin,
  onRefetchEvents,
  onRefetchClub,
}: ClubEventsSectionProps) {
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

  const handleDelete = async (eventId: number) => {
    if (!eventId) return;

    try {
      await deleteClubEvent(clubId, eventId);
      toast.success('Event deleted successfully.');
      await onRefetchEvents();
      await onRefetchClub();
    } catch (error) {
      console.error('Failed to delete event:', error);
    }
  };

  const handleSubmit = async (data: Event) => {
    try {
      if (editingEvent) {
        await updateClubEvent(clubId, data);
        toast.success('Event updated successfully.');
      } else {
        await createClubEvent(clubId, data);
        toast.success('Event created successfully.');
      }
      setModalOpen(false);
      await onRefetchEvents();
      await onRefetchClub();
    } catch (error) {
      console.error('Failed to submit event:', error);
    }
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
                <div className="space-y-1.5">
                  <div className="text-lg text-gray-500 font-extrabold">{event.title}</div>
                  <div className="text-base text-gray-500">
                    {event.date} at {event.venue.name}
                  </div>
                  <div className="text-base text-gray-500">
                    {event.description}
                  </div>
                  <div className="text-base text-gray-500">
                    Cost: ${event.cost} · Capacity: {event.capacity} · Attendees: {event.numOfAttendees}
                  </div>
                </div>
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
