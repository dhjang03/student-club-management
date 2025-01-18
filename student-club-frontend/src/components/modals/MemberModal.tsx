'use client';

import { useState } from 'react';
import { Button } from '@/components/button';
import { ClubMember } from '@/types/dashboard';

interface MemberModalProps {
  isOpen: boolean;
  onClose: () => void;
  onSubmit: (data: ClubMember) => void;
  member: ClubMember;
}

export function FundingModal({ isOpen, onClose, onSubmit, member }: MemberModalProps) {
  const [formData, setFormData] = useState(member);

  if (!isOpen) return null;

  const handleSubmit = () => {
    onSubmit(formData);
    onClose();
  };

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-75">
      <div className="bg-white p-6 rounded-md w-1/3">
        <h2 className="text-lg font-semibold mb-4">{member ? 'Edit Funding' : 'Create Funding'}</h2>
        <input
          className="border p-2 w-full mb-4"
          type="number"
          placeholder="Amount"
          value={formData.membership}
          onChange={(e) => setFormData({ ...formData, membership: e.target.value})}
        />
        <div className="flex justify-end space-x-2">
          <Button onClick={onClose}>Cancel</Button>
          <Button onClick={handleSubmit}>Submit</Button>
        </div>
      </div>
    </div>
  );
}