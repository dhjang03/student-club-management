import { Button } from '@/components/button'
import { Link } from '@/components/link'
import { Divider } from '@/components/divider'
import { DescriptionDetails, DescriptionList, DescriptionTerm } from '@/components/description-list'
import { Heading, Subheading } from '@/components/heading'
import { getEvent } from '@/data'
import { ChevronLeftIcon } from '@heroicons/react/16/solid'
import { notFound } from 'next/navigation'
import { EditEvent } from './edit'

import type { Metadata } from 'next'

export async function generateMetadata({ params }: { params: { id: string } }): Promise<Metadata> {
  const { id } = await params

  let event = await getEvent(id)

  return {
    title: event?.name,
  }
}

export default async function Event({ params }: { params: { id: string } }) {
  const { id } = await params

  let event = await getEvent(id)

  if (!event) {
    notFound()
  }

  return (
    <>
      <div className="max-lg:hidden">
        <Link href="/student-admin/events" className="inline-flex items-center gap-2 text-sm/6 text-zinc-500 dark:text-zinc-400">
          <ChevronLeftIcon className="size-4 fill-zinc-400 dark:fill-zinc-500" />
          Events
        </Link>
      </div>
      <div className="mt-4 flex flex-wrap items-end justify-between gap-4">
        <div className="flex flex-wrap items-center gap-6">
          <div>
            <div className="flex flex-wrap items-center gap-x-4 gap-y-2">
              <Heading>{event.name}</Heading>
            </div>
          </div>
        </div>
        <div className="flex gap-4">
          <EditEvent outline event={event}>Edit</EditEvent>
          <Button>View</Button>
        </div>
      </div>
      <div className="mt-12">
        <Subheading>Details</Subheading>
        <Divider className="mt-4" />
        <DescriptionList>
          <DescriptionTerm>Description</DescriptionTerm>
          <DescriptionDetails>Placeholder</DescriptionDetails>
          <DescriptionTerm>Event</DescriptionTerm>
          <DescriptionDetails>Placeholder</DescriptionDetails>
          <DescriptionTerm>Amount</DescriptionTerm>
          <DescriptionDetails>Placeholder</DescriptionDetails>
        </DescriptionList>
      </div>
    </>
  )
}
