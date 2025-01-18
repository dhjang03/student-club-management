'use client';

import { useState } from 'react';
import { Button } from '@/components/button';
import { Event, Venue } from '@/types/dashboard';

interface ClubEventModalProps {
  isOpen: boolean;
  onClose: () => void;
  onSubmit: (data: Event) => void;
  eventData?: Event;
}

export function ClubEventModal({ isOpen, onClose, onSubmit, eventData }: ClubEventModalProps) {
  const [formData, setFormData] = useState<Event>(eventData || 
    { id: null, title: '', description: '', date: '', cost: 0, 
        venue: { id: null, name: '', address: '', capacity: 0 }, 
        createdAt: null, capacity: 0, numOfAttendees: 0 }
    );

  if (!isOpen) return null;

  const handleSubmit = () => {
    onSubmit(formData);
    onClose();
  };

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-75">
      <div className="bg-white p-6 rounded-md w-1/3">
        <h2 className="text-lg font-semibold mb-4">{eventData ? 'Edit Event' : 'Create Event'}</h2>
        <input
          className="border p-2 w-full mb-4"
          type="text"
          placeholder="Title"
          value={formData.title}
          onChange={(e) => setFormData({ ...formData, title: e.target.value })}
        />
        <textarea
          className="border p-2 w-full mb-4"
          placeholder="Description"
          value={formData.description}
          onChange={(e) => setFormData({ ...formData, description: e.target.value })}
        />
        {/* Add more fields as required */}
        <div className="flex justify-end space-x-2">
          <Button onClick={onClose}>Cancel</Button>
          <Button onClick={handleSubmit}>Submit</Button>
        </div>
      </div>
    </div>
  );
}
