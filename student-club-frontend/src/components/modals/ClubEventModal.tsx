'use client';

import { useState, useEffect, useMemo } from 'react';
import { Button } from '@/components/button';
import { Event, Venue } from '@/types/dashboard';

interface ClubEventModalProps {
  isOpen: boolean;
  onClose: () => void;
  onSubmit: (data: Event) => void;
  eventData?: Event;
  venues: Venue[];
}

export function ClubEventModal({
  isOpen,
  onClose,
  onSubmit,
  eventData,
  venues,
}: ClubEventModalProps) {
  const defaultEvent: Event = useMemo(() => ({
    id: null,
    title: '',
    description: '',
    date: '',
    cost: 0,
    venue: { id: null, name: '', address: '', capacity: 0 },
    createdAt: null,
    capacity: 0,
    numOfAttendees: 0,
  }), []);

  const [formData, setFormData] = useState<Event>(eventData || defaultEvent);

  useEffect(() => {
    if (eventData) {
      setFormData(eventData);
    } else {
      setFormData(defaultEvent);
    }
  }, [eventData, defaultEvent]);

  if (!isOpen) return null;

  const handleSubmit = () => {
    onSubmit(formData);
    onClose();
  };

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-75">
      <div className="bg-white p-6 rounded-md w-1/3 space-y-4">
        <h2 className="text-lg font-semibold">
          {eventData ? 'Edit Event' : 'Create Event'}
        </h2>

        {/* Title Field */}
        <div>
          <label htmlFor="title" className="block font-semibold mb-1">
            Title
          </label>
          <input
            id="title"
            className="border p-2 w-full"
            type="text"
            placeholder="Title"
            value={formData.title}
            onChange={(e) =>
              setFormData({ ...formData, title: e.target.value })
            }
          />
        </div>

        {/* Description Field */}
        <div>
          <label htmlFor="description" className="block font-semibold mb-1">
            Description
          </label>
          <textarea
            id="description"
            className="border p-2 w-full"
            placeholder="Description"
            value={formData.description}
            onChange={(e) =>
              setFormData({ ...formData, description: e.target.value })
            }
          />
        </div>

        {/* Date Field */}
        <div>
          <label htmlFor="date" className="block font-semibold mb-1">
            Date
          </label>
          <input
            id="date"
            className="border p-2 w-full"
            type="date"
            value={formData.date}
            onChange={(e) =>
              setFormData({ ...formData, date: e.target.value })
            }
          />
        </div>

        {/* Cost Field */}
        <div>
          <label htmlFor="cost" className="block font-semibold mb-1">
            Cost
          </label>
          <input
            id="cost"
            className="border p-2 w-full"
            type="number"
            placeholder="Cost"
            value={formData.cost}
            onChange={(e) =>
              setFormData({ ...formData, cost: Number(e.target.value) })
            }
          />
        </div>

        {/* Capacity Field */}
        <div>
          <label htmlFor="capacity" className="block font-semibold mb-1">
            Capacity
          </label>
          <input
            id="capacity"
            className="border p-2 w-full"
            type="number"
            placeholder="Capacity"
            value={formData.capacity}
            onChange={(e) =>
              setFormData({ ...formData, capacity: Number(e.target.value) })
            }
          />
        </div>

        {/* Venue Dropdown */}
        <div>
          <label htmlFor="venue" className="block font-semibold mb-1">
            Venue
          </label>
          <select
            id="venue"
            className="border p-2 w-full"
            value={formData.venue.id ?? ''}
            onChange={(e) => {
              const selectedVenueId = Number(e.target.value);
              const selectedVenue =
                venues.find((venue) => venue.id === selectedVenueId) ||
                { id: null, name: '', address: '', capacity: 0 };
              setFormData({ ...formData, venue: selectedVenue });
            }}
          >
            <option value="" disabled>
              Select Venue
            </option>
            {venues.map((venue) => (
              <option key={venue.id} value={venue.id ?? ''}>
                {venue.name}
              </option>
            ))}
          </select>
        </div>

        {/* Action Buttons */}
        <div className="flex justify-end space-x-2">
          <Button onClick={onClose}>Cancel</Button>
          <Button onClick={handleSubmit}>Submit</Button>
        </div>
      </div>
    </div>
  );
}
