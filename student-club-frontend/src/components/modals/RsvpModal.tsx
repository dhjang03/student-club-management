'use client'

import { useState } from 'react'
import { Button } from '@/components/button'
import { Ticket } from '@/types/dashboard'

interface RsvpModalProps {
  isOpen: boolean;
  eventId: number;
  onClose: () => void;
  onSubmit: (eventId: number, tickets: Ticket[]) => void;
}

export function RsvpModal({ isOpen, eventId, onClose, onSubmit }: RsvpModalProps) {
  const [tickets, setTickets] = useState<Ticket[]>([
    { id: null, firstName: '', lastName: '', email: '' }
  ]);

  const handleChange = (
    index: number, 
    field: 'firstName' | 'lastName' | 'email', 
    value: string
  ) => {
    const updatedTickets = [...tickets];
    updatedTickets[index][field] = value;
    setTickets(updatedTickets);
  };

  const addTicket = () => {
    setTickets([...tickets, { id: null, firstName: '', lastName: '', email: '' }]);
  };

  const handleFormSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit(eventId, tickets);
    onClose();
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
      <div className="bg-white rounded-lg w-full max-w-md mx-4 p-6 flex flex-col space-y-4 max-h-[80vh] overflow-y-auto min-h-0">
        <h2 className="text-2xl font-semibold mb-4 text-gray-500">RSVP for Event</h2>
        <form onSubmit={handleFormSubmit} className="flex flex-col space-y-4">
          <div className="pr-2 space-y-4 min-h-0">
            {tickets.map((ticket, index) => (
              <div key={index} className="border p-4 rounded">
                <h3 className="font-semibold mb-2 text-gray-500">Ticket {index + 1}</h3>
                <div className="flex flex-col gap-2">
                  <div className="flex items-center gap-2">
                    <label className="w-24 text-gray-500">First Name</label>
                    <input
                      value={ticket.firstName}
                      onChange={(e) => handleChange(index, 'firstName', e.target.value)}
                      required
                      className="flex-1 border border-gray-300 rounded px-2 py-1 text-gray-500"
                    />
                  </div>
                  <div className="flex items-center gap-2">
                    <label className="w-24 text-gray-500">Last Name</label>
                    <input
                      value={ticket.lastName}
                      onChange={(e) => handleChange(index, 'lastName', e.target.value)}
                      required
                      className="flex-1 border border-gray-300 rounded px-2 py-1 text-gray-500"
                    />
                  </div>
                  <div className="flex items-center gap-2">
                    <label className="w-24 text-gray-500">Email</label>
                    <input
                      type="email"
                      value={ticket.email}
                      onChange={(e) => handleChange(index, 'email', e.target.value)}
                      required
                      className="flex-1 border border-gray-300 rounded px-2 py-1 text-gray-500"
                    />
                  </div>
                </div>
              </div>
            ))}
          </div>
          <div className="flex justify-between items-center">
            <Button type="button" onClick={addTicket}>
              + Add Another Ticket
            </Button>
            <div className="flex gap-2">
              <Button type="button" onClick={onClose}>
                Cancel
              </Button>
              <Button type="submit">
                Submit RSVP
              </Button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}
