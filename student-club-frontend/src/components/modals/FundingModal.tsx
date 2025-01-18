'use client';

import { useState } from 'react';
import { Button } from '@/components/button';
import { ApplicationStatus, Funding } from '@/types/dashboard'

interface FundingModalProps {
  isOpen: boolean;
  onClose: () => void;
  onSubmit: (data: Funding) => void;
  funding?: Funding;
}

export function FundingModal({ isOpen, onClose, onSubmit, funding }: FundingModalProps) {
  const [formData, setFormData] = useState(funding || 
    { id: null, amount: 0, description: '', createdAt: null, status: ApplicationStatus.DRAFT });

  if (!isOpen) return null;

  const handleSubmit = () => {
    onSubmit(formData);
    onClose();
  };

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-75">
      <div className="bg-white p-6 rounded-md w-1/3">
        <h2 className="text-lg font-semibold mb-4">{funding ? 'Edit Funding' : 'Create Funding'}</h2>
        <input
          className="border p-2 w-full mb-4"
          type="number"
          placeholder="Amount"
          value={formData.amount}
          onChange={(e) => setFormData({ ...formData, amount: Number(e.target.value) })}
        />
        <input
          className="border p-2 w-full mb-4"
          type="text"
          placeholder="Description"
          value={formData.description}
          onChange={(e) => setFormData({ ...formData, description: e.target.value })}
        />
        <div className="flex justify-end space-x-2">
          <Button onClick={onClose}>Cancel</Button>
          <Button onClick={handleSubmit}>Submit</Button>
        </div>
      </div>
    </div>
  );
}
