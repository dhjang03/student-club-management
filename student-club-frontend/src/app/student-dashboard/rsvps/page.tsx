'use client'

import { useEffect, useState } from 'react'
import { Divider } from '@/components/divider'
import { Dropdown, DropdownButton, DropdownItem, DropdownMenu } from '@/components/dropdown'
import { Heading } from '@/components/heading'
import { EllipsisVerticalIcon, ChevronRightIcon } from '@heroicons/react/20/solid'
import { getToken } from '@/utils/auth'
import { Rsvp } from '@/types/dashboard'
import { getMyRsvps, deleteRsvp } from '@/api/dashboard'
import { toast } from 'react-toastify'

export default function RsvpList() {
  const token = getToken();
  const [rsvps, setRsvps] = useState<Rsvp[]>([]);
  const [expandedRsvpIds, setExpandedRsvpIds] = useState<Set<number>>(new Set());

  useEffect(() => {
    async function fetchData() {
      try {
        const rsvpsData = await getMyRsvps();
        setRsvps(rsvpsData);
      } catch (error) {
        console.error(error);
      }
    }
    fetchData();
  }, [token]);

  const toggleExpand = (rsvpId: number) => {
    setExpandedRsvpIds(prev => {
      const newSet = new Set(prev);
      if (newSet.has(rsvpId)) {
        newSet.delete(rsvpId);
      } else {
        newSet.add(rsvpId);
      }
      return newSet;
    });
  };

  const handleDelete = async (rsvpId: number) => {
    try {
      await deleteRsvp(rsvpId);
      setRsvps(prev => prev.filter(rsvp => rsvp.id !== rsvpId));
      toast.success('RSVP deleted successfully.');
    } catch (error) {
      console.error('Error deleting RSVP:', error);
    }
  };

  return (
    <>
      <div className="flex flex-wrap items-end justify-between gap-4">
        <div className="max-sm:w-full sm:flex-1">
          <Heading>My RSVPs</Heading>
        </div>
      </div>
      <ul className="mt-10">
        {rsvps.length > 0 ? (
          rsvps.map((rsvp) => (
            <li key={rsvp.id}>
              <Divider />
              <div className="flex items-center justify-between py-4">
                <div className="flex items-center gap-4">
                  {/* Expand/Collapse Icon */}
                  <button 
                    onClick={() => rsvp.id && toggleExpand(rsvp.id)} 
                    aria-label="Expand details"
                    className="text-gray-500 hover:text-gray-300"
                  >
                    <ChevronRightIcon className={`h-5 w-5 transform ${rsvp.id && expandedRsvpIds.has(rsvp.id) ? 'rotate-90' : ''}`} />
                  </button>
                  {/* Event Info */}
                  <div>
                    <div className="text-lg font-medium text-gray-100 text-gray-500">
                      {rsvp.event?.title || 'Untitled Event'}
                    </div>
                    <div className="text-sm text-gray-300 text-gray-500">
                      {rsvp.event?.date} at {rsvp.event?.venue.name}
                    </div>
                  </div>
                </div>
                <div className="flex items-center">
                  <Dropdown>
                    <DropdownButton plain aria-label="More options">
                      <EllipsisVerticalIcon className="h-5 w-5 text-gray-500"/>
                    </DropdownButton>
                    <DropdownMenu anchor="bottom end">
                      <DropdownItem onClick={() => rsvp.id && handleDelete(rsvp.id)}>
                        Delete
                      </DropdownItem>
                    </DropdownMenu>
                  </Dropdown>
                </div>
              </div>
              {/* Expanded Ticket Details */}
              {rsvp.id && expandedRsvpIds.has(rsvp.id) && (
                <div className="ml-8 pl-4 border-l border-gray-100">
                  {rsvp.tickets.length > 0 ? (
                    <table className="min-w-full text-sm text-left text-gray-500">
                      <thead>
                        <tr>
                          <th className="py-1">First Name</th>
                          <th className="py-1">Last Name</th>
                          <th className="py-1">Email</th>
                        </tr>
                      </thead>
                      <tbody>
                        {rsvp.tickets.map((ticket, idx) => (
                          <tr key={idx}>
                            <td className="py-1">{ticket.firstName || 'N/A'}</td>
                            <td className="py-1">{ticket.lastName || 'N/A'}</td>
                            <td className="py-1">{ticket.email || 'N/A'}</td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  ) : (
                    <p className="text-sm text-gray-500">No tickets available.</p>
                  )}
                </div>
              )}
            </li>
          ))
        ) : (
          <p className="text-sm text-gray-500">No RSVPs found.</p>
        )}
      </ul>
    </>
  )
}
