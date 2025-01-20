'use client';

import { useState, useEffect } from 'react';
import { getAllFundings, updateFundingStatus } from '@/api/dashboard';
import { Funding, ApplicationStatus } from '@/types/dashboard';
import {
  Dropdown,
  DropdownButton,
  DropdownMenu,
  DropdownItem,
} from '@/components/dropdown';
import { EllipsisVerticalIcon } from '@heroicons/react/24/outline';
import { toast } from 'react-toastify';


export default function StaffDashboard() {
  const [fundings, setFundings] = useState<Funding[]>([]);
  const [statusFilter, setStatusFilter] = useState<ApplicationStatus | 'ALL'>('ALL');

  useEffect(() => {
    async function fetchFunding() {
      try {
        const fundingData = await getAllFundings();
        setFundings(fundingData);
      } catch (error) {
        console.error('Failed to fetch funding data:', error);
        setFundings([]);
      }
    }
    fetchFunding();
  }, []);

  const handleUpdateStatus = async (fundingId: number, newStatus: ApplicationStatus) => {
    try {
      const updatedFunding = await updateFundingStatus(fundingId, newStatus);
      toast.success('Funding status updated successfully.');
      setFundings((prevFundings) =>
        prevFundings.map((f) => (f.id === fundingId ? updatedFunding : f))
      );
    } catch (error) {
      console.error('Failed to update funding status:', error);
    }
  };

  const filteredFundings = fundings.filter((funding) => {
    if (funding.status === ApplicationStatus.DRAFT) {
      return false;
    }
    if (statusFilter === 'ALL') {
      return true;
    }
    return funding.status === statusFilter;
  });

  return (
    <div className="p-4">
      <div className="flex items-center justify-between mb-4">
        <h2 className="text-xl font-bold text-gray-100">Staff Dashboard - Funding Applications</h2>

        {/* Status Filter Dropdown */}
        <div className="flex items-center space-x-2">
          <span>Filter:</span>
          <select
            className="border rounded px-2 py-1 bg-gray-300" 
            value={statusFilter}
            onChange={(e) => setStatusFilter(e.target.value as ApplicationStatus | 'ALL')}
          >
            <option value="ALL">All</option>
            <option value={ApplicationStatus.SUBMITTED}>Submitted</option>
            <option value={ApplicationStatus.IN_REVIEW}>In Review</option>
            <option value={ApplicationStatus.APPROVED}>Approved</option>
            <option value={ApplicationStatus.REJECTED}>Rejected</option>
          </select>
        </div>
      </div>

      {filteredFundings.length === 0 ? (
        <div className="text-gray-400">No funding applications found for this filter.</div>
      ) : (
        <div className="space-y-4">
          {filteredFundings.map((funding) => (
            <div
              key={funding.id}
              className="p-4 border rounded-md bg-gray-800 flex justify-between items-start"
            >
              <div>
                <p className="text-sm text-gray-300">ID: {funding.id}</p>
                <p className="text-sm text-gray-300">Amount: ${funding.amount}</p>
                <p className="text-sm text-gray-300">
                  Description: {funding.description}
                </p>
                <p className="text-sm text-gray-300">
                  Applied on: {funding.createdAt ? new Date(funding.createdAt).toLocaleString() : 'N/A'}
                </p>
                <p className="text-sm text-gray-300">Status: {funding.status}</p>
              </div>

              {/* Three-dot dropdown for changing status if not APPROVED/REJECTED */}
              <div>
                {funding.status === ApplicationStatus.APPROVED ||
                funding.status === ApplicationStatus.REJECTED ? (
                  // If approved/rejected, no further status changes
                  <div className="text-gray-400">No further actions</div>
                ) : (
                  <Dropdown>
                    <DropdownButton plain aria-label="More options">
                      <EllipsisVerticalIcon className="w-5 h-5" />
                    </DropdownButton>
                    <DropdownMenu anchor="bottom end">
                      {/* For each possible status update */}
                      {funding.status !== ApplicationStatus.IN_REVIEW && (
                        <DropdownItem
                          onClick={() =>
                            funding.id !== null && handleUpdateStatus(funding.id, ApplicationStatus.IN_REVIEW)
                          }
                        >
                          In Review
                        </DropdownItem>
                      )}
                      {funding.status !== ApplicationStatus.APPROVED && (
                        <DropdownItem
                          onClick={() =>
                            funding.id !== null && handleUpdateStatus(funding.id, ApplicationStatus.APPROVED)
                          }
                        >
                          Approve
                        </DropdownItem>
                      )}
                      {funding.status !== ApplicationStatus.REJECTED && (
                        <DropdownItem
                          onClick={() =>
                            funding.id !== null && handleUpdateStatus(funding.id, ApplicationStatus.REJECTED)
                          }
                        >
                          Reject
                        </DropdownItem>
                      )}
                    </DropdownMenu>
                  </Dropdown>
                )}
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
