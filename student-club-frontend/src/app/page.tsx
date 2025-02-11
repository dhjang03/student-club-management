'use client';

import React from 'react';
import Image from 'next/image';
import { Button } from '@/components/button';
import { useRedirectBasedOnToken } from '@/hooks/useRedirectBasedOnToken';

export default function Home() {
  const { redirect: handleDashboardRedirect } = useRedirectBasedOnToken();

  return (
    <div className="relative isolate overflow-hidden bg-white">
      <svg
        aria-hidden="true"
        className="absolute inset-0 -z-10 h-full w-full stroke-gray-200 [mask-image:radial-gradient(100%_100%_at_top_right,white,transparent)]"
      >
        <defs>
          <pattern
            id="pattern-grid"
            width={200}
            height={200}
            patternUnits="userSpaceOnUse"
            x="50%"
            y={-1}
          >
            <path d="M.5 200V.5H200" fill="none" />
          </pattern>
        </defs>
        <rect fill="url(#pattern-grid)" width="100%" height="100%" strokeWidth={0} />
      </svg>

      <div className="mx-auto max-w-7xl px-6 pb-24 pt-10 sm:pb-32 lg:flex lg:px-8 lg:py-40">
        <div className="mx-auto max-w-2xl lg:mx-0 lg:shrink-0 lg:pt-8">
          <h1 className="mt-10 text-pretty text-5xl font-semibold tracking-tight text-gray-900 sm:text-7xl">
            Student Club Management
          </h1>
          <p className="mt-8 text-pretty text-lg font-medium text-gray-500 sm:text-xl">
            Welcome to the Student Club Management system. This platform allows you to efficiently
            manage student clubs. Stay organized and keep track of all club activities in one place.
          </p>
          <div className="mt-10 flex items-center gap-x-6">
            <Button
              color="blue"
              className="rounded-md bg-indigo-600 px-3.5 py-2.5 text-sm font-semibold text-white shadow-sm 
                         hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 
                         focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
              onClick={handleDashboardRedirect}
            >
              Go to Dashboard
            </Button>
          </div>
        </div>
        <div className="mx-auto mt-16 flex max-w-2xl sm:mt-24 lg:ml-10 lg:mr-0 lg:mt-0 lg:max-w-none lg:flex-none xl:ml-32">
          <div className="max-w-3xl flex-none sm:max-w-5xl lg:max-w-none">
            <div className="-m-2 rounded-xl bg-gray-900/5 p-2 ring-1 ring-inset ring-gray-900/10 
                            lg:-m-4 lg:rounded-2xl lg:p-4">
              <Image
                alt="App screenshot"
                src="/assets/home-img.png"
                width={2432}
                height={1442}
                className="w-[76rem] rounded-md shadow-2xl ring-1 ring-gray-900/10"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
