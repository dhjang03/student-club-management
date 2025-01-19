'use client';

import { useState } from 'react';
import { Heading } from '@/components/heading';
import { Divider } from '@/components/divider';
import { Dropdown, DropdownButton, DropdownItem, DropdownMenu } from '@/components/dropdown';
import { EllipsisVerticalIcon } from '@heroicons/react/16/solid';
import { ClubMember } from '@/types/dashboard';
import { MemberModal } from '../modals/MemberModal';

interface MembersSectionProps {
  clubId: number;
  members: ClubMember[];
  isAdmin: boolean;
}

export function MembersSection({ clubId, members, isAdmin }: MembersSectionProps) {
  const [isModalOpen, setModalOpen] = useState(false);
  const [editingMember, setEditingMember] = useState<ClubMember | null>(null);

  const handleEdit = (member: ClubMember) => {
    setEditingMember(member);
    setModalOpen(true);
  };

  return (
    <div className="md:w-1/2">
      <Heading level={2}>Club Members</Heading>
      <ul className="mt-2 space-y-2 max-h-96 overflow-y-auto">
        {members.length > 0 ? (
          members.map(member => (
            <li key={member.id}>
              <Divider />
              <div className="flex items-center justify-between py-2">
                <span className="text-xs text-zinc-300">
                  {member.firstName} {member.lastName}
                </span>
                <div className="flex items-center gap-4">
                  <span className="text-xs text-zinc-300">
                    {member.membership}
                  </span>
                  {isAdmin && (
                    <Dropdown>
                      <DropdownButton plain aria-label="More options">
                        <EllipsisVerticalIcon />
                      </DropdownButton>
                      <DropdownMenu anchor="bottom end">
                        <DropdownItem onClick={() => handleEdit(member)}>
                          Edit Membership
                        </DropdownItem>
                      </DropdownMenu>
                    </Dropdown>
                  )}
                </div>
              </div>
            </li>
          ))
        ) : (
          <li className="text-sm text-gray-500">No members found.</li>
        )}
      </ul>
      {editingMember && (
        <MemberModal
          isOpen={isModalOpen}
          onClose={() => setModalOpen(false)}
          onSuccess={() => window.location.reload()}
          member={editingMember}
          clubId={clubId}
        />
      )}
    </div>
  );
}
