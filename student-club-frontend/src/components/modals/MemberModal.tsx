'use client';

import { useState } from 'react';
import { Button } from '@/components/button';
import { ClubMember } from '@/types/dashboard';
import { promoteToAdmin, demoteToMember } from '@/api/dashboard';
import { toast } from 'react-toastify';

interface MemberModalProps {
  isOpen: boolean;
  onClose: () => void;
  onSuccess: () => void;
  member: ClubMember;
  clubId: number;
}

export function MemberModal({ isOpen, onClose, onSuccess, member, clubId }: MemberModalProps) {
  const [action, setAction] = useState<'promote' | 'demote'>('promote');

  if (!isOpen) return null;

  const handleSubmit = async () => {
    if (action === 'promote') {
      await promoteToAdmin(clubId, member);
      toast.success('Member promoted to admin successfully.');
    } else {
      await demoteToMember(clubId, member);
      toast.success('Admin demoted to member successfully.');
    }
    onSuccess();
    onClose();
  };

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-75">
      <div className="bg-white p-6 rounded-md w-1/3 space-y-4">
        <h2 className="text-lg font-semibold mb-4 text-gray-500">Edit Membership</h2>

        <div>
          <p className="font-semibold mb-2 text-gray-500">Choose Action:</p>
          <div className="flex items-center mb-2 text-gray-500">
            <input
              type="radio"
              id="promote"
              name="action"
              value="promote"
              checked={action === 'promote'}
              onChange={() => setAction('promote')}
              className="mr-2 text-gray-500"
            />
            <label htmlFor="promote">Promote to Admin</label>
          </div>
          <div className="flex items-center text-gray-500">
            <input
              type="radio"
              id="demote"
              name="action"
              value="demote"
              checked={action === 'demote'}
              onChange={() => setAction('demote')}
              className="mr-2"
            />
            <label htmlFor="demote">Demote to Member</label>
          </div>
        </div>

        <div className="flex justify-end space-x-2">
          <Button onClick={onClose}>Cancel</Button>
          <Button onClick={handleSubmit}>Submit</Button>
        </div>
      </div>
    </div>
  );
}
