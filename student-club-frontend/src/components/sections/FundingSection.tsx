'use client';

import { useState } from 'react';
import { Button } from '@/components/button';
import { Heading } from '@/components/heading';
import {
  Dropdown,
  DropdownButton,
  DropdownItem,
  DropdownMenu,
} from '@/components/dropdown';
import { EllipsisVerticalIcon } from '@heroicons/react/16/solid';
import { ApplicationStatus, Funding } from '@/types/dashboard';
import { FundingModal } from '@/components/modals/FundingModal';
import {
  createFunding,
  updateFundingByClubId,
  deleteFunding,
} from '@/api/dashboard';
import { toast } from 'react-toastify';

interface FundingSectionProps {
  clubId: number;
  funding: Funding | null;
  isAdmin: boolean;
  onRefetchFunding: () => Promise<void>;
}

export function FundingSection({
  clubId,
  funding,
  isAdmin,
  onRefetchFunding,
}: FundingSectionProps) {
  const [isModalOpen, setModalOpen] = useState(false);
  const [editingFunding, setEditingFunding] = useState<Funding | null>(null);

  const nonEditableStatuses = [
    ApplicationStatus.IN_REVIEW,
    ApplicationStatus.APPROVED,
    ApplicationStatus.REJECTED,
  ];

  const isEditable = funding ? !nonEditableStatuses.includes(funding.status) : true;

  const handleCreate = () => {
    setEditingFunding(null);
    setModalOpen(true);
  };

  const handleEdit = () => {
    setEditingFunding(funding);
    setModalOpen(true);
  };

  const handleDelete = async () => {
    if (!funding?.id || !isEditable) return;

    try {
      await deleteFunding(clubId, funding.id);
      toast.success('Funding application deleted successfully.');
      await onRefetchFunding();
    } catch (error) {
      console.error('Failed to delete funding:', error);
    }
  };

  const handleSubmit = async (data: Funding) => {
    try {
      if (editingFunding) {
        await updateFundingByClubId(clubId, data);
        toast.success('Funding application updated successfully.');
      } else {
        await createFunding(clubId, data);
        toast.success('Funding application created successfully.');
      }
      setModalOpen(false);
      await onRefetchFunding();
    } catch (error) {
      console.error('Failed to submit funding:', error);
    }
  };

  return (
    <div className="md:w-1/2">
      <div className="flex items-center justify-between">
        <Heading level={2}>Funding Application</Heading>
        {isAdmin && !funding && (
          <Button onClick={handleCreate}>Create Funding Application</Button>
        )}
      </div>
      <div className="mt-2 space-y-4">
        {funding ? (
          <div
            key={funding.id}
            className="p-4 border rounded-md flex justify-between items-start"
          >
            <div>
              <p className="text-sm text-gray-500">Amount: ${funding.amount}</p>
              <p className="text-sm text-gray-500">
                Description: {funding.description}
              </p>
              <p className="text-sm text-gray-500">
                Applied on: {funding.createdAt}
              </p>
              <p className="text-sm text-gray-500">Status: {funding.status}</p>
            </div>
            {/* Only display dropdown for admins */}
            {isAdmin && (
              <Dropdown>
                <DropdownButton plain aria-label="More options">
                  <EllipsisVerticalIcon />
                </DropdownButton>
                <DropdownMenu anchor="bottom end">
                  {isEditable && <DropdownItem onClick={handleEdit}>Edit</DropdownItem>}
                  {isEditable && (
                    <DropdownItem onClick={handleDelete}>Delete</DropdownItem>
                  )}
                  {!isEditable && (
                    <DropdownItem disabled>Not Editable</DropdownItem>
                  )}
                </DropdownMenu>
              </Dropdown>
            )}
          </div>
        ) : (
          <div className="flex flex-col items-center justify-center h-32">
            <p className="text-sm text-gray-500 text-center">
              No funding applications found.
            </p>
          </div>
        )}
      </div>
      <FundingModal
        isOpen={isModalOpen}
        onClose={() => setModalOpen(false)}
        onSubmit={handleSubmit}
        funding={editingFunding || undefined}
      />
    </div>
  );
}
