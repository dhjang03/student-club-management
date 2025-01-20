'use client'

import { useEffect, useState, useCallback, ChangeEvent } from 'react'
import { Divider } from '@/components/divider'
import { Dropdown, DropdownButton, DropdownItem, DropdownMenu } from '@/components/dropdown'
import { Heading } from '@/components/heading'
import { Input, InputGroup } from '@/components/input'
import { Select } from '@/components/select'
import { EllipsisVerticalIcon, MagnifyingGlassIcon } from '@heroicons/react/16/solid'
import { Event, Ticket, Rsvp } from '@/types/dashboard'
import { getAllEvents, createRsvp } from '@/api/dashboard'
import { RsvpModal } from '@/components/modals/RsvpModal'
import { toast } from 'react-toastify'

export default function Events() {
  const [events, setEvents] = useState<Event[]>([]);
  const [rsvpEvent, setRsvpEvent] = useState<Event | null>(null);
  const [isModalOpen, setModalOpen] = useState(false);
  const [sortBy, setSortBy] = useState<'name' | 'date'>('name');
  const [searchQuery, setSearchQuery] = useState<string>('');

  const fetchEventsData = useCallback(async () => {
    try {
      const eventsData = await getAllEvents();
      setEvents(eventsData);
    } catch (error) {
      console.error('Failed to fetch events data:', error);
    }
  }, []);

  useEffect(() => {
    async function initialLoad() {
      await fetchEventsData();
    }
    initialLoad();
  }, [fetchEventsData]);

  const handleRsvp = (event: Event) => {
    setRsvpEvent(event);
    setModalOpen(true);
  }

  const handleSubmit = async (eventId: number, tickets: Ticket[]) => {
    const rsvp: Rsvp = {
      id: null,
      responder: null,
      event: null,
      createdAt: null,
      tickets: tickets
    }
    await createRsvp(eventId, rsvp);
    toast.success('Rsvp submitted successfully.');
    await fetchEventsData();
  }

  const handleSortChange = (e: ChangeEvent<HTMLSelectElement>) => {
    const value = e.target.value as 'name' | 'date';
    setSortBy(value);
  };

  const handleSearchChange = (e: ChangeEvent<HTMLInputElement>) => {
    setSearchQuery(e.target.value);
  };

  const filteredEvents = events.filter(event => {
    const query = searchQuery.toLowerCase();
    return (
      event.title.toLowerCase().includes(query) ||
      event.description.toLowerCase().includes(query)
    );
  });

  const sortedEvents = [...filteredEvents].sort((a, b) => {
    if (sortBy === 'name') {
      return a.title.localeCompare(b.title);
    } else if (sortBy === 'date') {
      return new Date(a.date).getTime() - new Date(b.date).getTime();
    }
    return 0;
  });

  return (
    <>
      <div className="flex flex-wrap items-end justify-between gap-4">
        <div className="max-sm:w-full sm:flex-1">
          <Heading>Events</Heading>
          <div className="mt-4 flex max-w-xl gap-4">
            <div className="flex-1">
              <InputGroup>
                <MagnifyingGlassIcon />
                {/* Attach onChange handler for search input */}
                <Input
                  name="search"
                  placeholder="Search events…"
                  value={searchQuery}
                  onChange={handleSearchChange}
                />
              </InputGroup>
            </div>
            <div>
              <Select name="sort_by" value={sortBy} onChange={handleSortChange}>
                <option value="name">Sort by name</option>
                <option value="date">Sort by date</option>
              </Select>
            </div>
          </div>
        </div>
      </div>
      <ul className="mt-10">
        {sortedEvents.length > 0 ? (
          sortedEvents.map((event, index) => (
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
                  </div>
                </div>
                <div className="flex items-center gap-4">
                  <Dropdown>
                    <DropdownButton plain aria-label="More options">
                      <EllipsisVerticalIcon />
                    </DropdownButton>
                    <DropdownMenu anchor="bottom end">
                      <DropdownItem onClick={() => handleRsvp(event)}>Rsvp</DropdownItem>
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
      {isModalOpen && rsvpEvent && rsvpEvent.id && (
        <RsvpModal
          isOpen={isModalOpen}
          eventId={rsvpEvent.id}
          onClose={() => setModalOpen(false)}
          onSubmit={handleSubmit}
        />
      )}
    </>
  )
}
