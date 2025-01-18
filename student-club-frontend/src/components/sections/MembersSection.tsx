'use client';

import { Heading } from '@/components/heading';
import { Divider } from '@/components/divider';
import { Dropdown, DropdownButton, DropdownItem, DropdownMenu } from '@/components/dropdown';
import { EllipsisVerticalIcon } from '@heroicons/react/16/solid';
import { ClubMember } from '@/types/dashboard';

interface MembersSectionProps {
  members: ClubMember[];
}

export function MembersSection({ members }: MembersSectionProps) {
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
                  <Dropdown>
                    <DropdownButton plain aria-label="More options">
                      <EllipsisVerticalIcon />
                    </DropdownButton>
                    <DropdownMenu anchor="bottom end">
                      <DropdownItem>Edit</DropdownItem>
                      <DropdownItem>Delete</DropdownItem>
                    </DropdownMenu>
                  </Dropdown>
                </div>
              </div>
            </li>
          ))
        ) : (
          <li className="text-sm text-gray-500">No members found.</li>
        )}
      </ul>
    </div>
  );
}
