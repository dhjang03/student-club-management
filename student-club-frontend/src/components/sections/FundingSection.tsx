'use client';

import { useState } from 'react';
import { Button } from '@/components/button';
import { Heading } from '@/components/heading';
import { Dropdown, DropdownButton, DropdownItem, DropdownMenu } from '@/components/dropdown';
import { EllipsisVerticalIcon } from '@heroicons/react/16/solid';
import { ApplicationStatus, Funding } from '@/types/dashboard';
import { FundingModal } from '@/components/modals/FundingModal';

interface FundingSectionProps {
  funding: Funding | null;
  onFundingChange: (newFunding: Funding | null) => void;
}

export function FundingSection({ funding, onFundingChange }: FundingSectionProps) {
  const [isModalOpen, setModalOpen] = useState(false);
  const [editingFunding, setEditingFunding] = useState<Funding | null>(null);

  const handleCreate = () => {
    setEditingFunding(null);
    setModalOpen(true);
  };

  const handleEdit = () => {
    setEditingFunding(funding);
    setModalOpen(true);
  };

  const handleDelete = () => {
    // TODO: Implement delete logic
    onFundingChange(null);
  };

  const handleSubmit = (data: Funding) => {
    // TODO: Update or create funding logic
    // For demonstration, we'll call onFundingChange directly
    onFundingChange({ ...data, createdAt: new Date().toISOString(), status: ApplicationStatus.DRAFT });
  };

  return (
    <div className="md:w-1/2">
      <div className="flex items-center justify-between">
        <Heading level={2}>Funding Application</Heading>
        {!funding && <Button onClick={handleCreate}>Create Funding Application</Button>}
      </div>
      <div className="mt-2 space-y-4">
        {funding ? (
          <div key={funding.id} className="p-4 border rounded-md bg-gray-800 flex justify-between items-start">
            <div>
              <p className="text-sm text-gray-300">Amount: ${funding.amount}</p>
              <p className="text-sm text-gray-300">Description: {funding.description}</p>
              <p className="text-sm text-gray-300">Applied on: {funding.createdAt}</p>
              <p className="text-sm text-gray-300">Status: {funding.status}</p>
            </div>
            <Dropdown>
              <DropdownButton plain aria-label="More options">
                <EllipsisVerticalIcon />
              </DropdownButton>
              <DropdownMenu anchor="bottom end">
                <DropdownItem onClick={handleEdit}>Edit</DropdownItem>
                <DropdownItem onClick={handleDelete}>Delete</DropdownItem>
              </DropdownMenu>
            </Dropdown>
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
