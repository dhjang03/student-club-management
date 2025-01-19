'use client';

import { useState, useEffect, useMemo } from 'react';
import { Button } from '@/components/button';
import { ApplicationStatus, Funding } from '@/types/dashboard';

interface FundingModalProps {
  isOpen: boolean;
  onClose: () => void;
  onSubmit: (data: Funding) => void;
  funding?: Funding;
}

export function FundingModal({ isOpen, onClose, onSubmit, funding }: FundingModalProps) {
  const defaultFunding: Funding = useMemo(() => ({
    id: null,
    amount: 0,
    description: '',
    createdAt: null,
    status: ApplicationStatus.DRAFT,
  }), []);

  const [formData, setFormData] = useState<Funding>(funding || defaultFunding);

  useEffect(() => {
    if (funding) {
      setFormData(funding);
    } else {
      setFormData(defaultFunding);
    }
  }, [funding, defaultFunding]);

  if (!isOpen) return null;

  const handleSubmit = () => {
    onSubmit(formData);
    onClose();
  };

  const statusOptions = [ApplicationStatus.DRAFT, ApplicationStatus.SUBMITTED];

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-75">
      <div className="bg-white p-6 rounded-md w-1/3 space-y-4">
        <h2 className="text-lg font-semibold mb-4">
          {funding ? 'Edit Funding' : 'Create Funding'}
        </h2>
        
        {/* Description Field */}
        <div>
          <label htmlFor="description" className="block font-semibold mb-1">
            Description
          </label>
          <input
            id="description"
            className="border p-2 w-full mb-4"
            type="text"
            placeholder="Description"
            value={formData.description}
            onChange={(e) =>
              setFormData({ ...formData, description: e.target.value })
            }
          />
        </div>
        
        {/* Amount Field */}
        <div>
          <label htmlFor="amount" className="block font-semibold mb-1">
            Amount
          </label>
          <input
            id="amount"
            className="border p-2 w-full mb-4"
            type="number"
            placeholder="Amount"
            value={formData.amount}
            onChange={(e) =>
              setFormData({ ...formData, amount: Number(e.target.value) })
            }
          />
        </div>

        {/* Status Dropdown */}
        <div>
          <label htmlFor="status" className="block font-semibold mb-1">
            Status
          </label>
          <select
            id="status"
            className="border p-2 w-full mb-4"
            value={formData.status}
            onChange={(e) =>
              setFormData({ ...formData, status: e.target.value as ApplicationStatus })
            }
          >
            {statusOptions.map((status) => (
              <option key={status} value={status}>
                {status}
              </option>
            ))}
          </select>
        </div>

        <div className="flex justify-end space-x-2">
          <Button onClick={onClose}>Cancel</Button>
          <Button onClick={handleSubmit}>Submit</Button>
        </div>
      </div>
    </div>
  );
}
