package com.studentclub.studentclubbackend.seed;

import com.studentclub.studentclubbackend.constants.ApplicationStatus;
import com.studentclub.studentclubbackend.constants.MembershipRole;
import com.studentclub.studentclubbackend.constants.UserRole;
import com.studentclub.studentclubbackend.models.*;
import com.studentclub.studentclubbackend.repositories.*;
import com.studentclub.studentclubbackend.services.MembershipService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
@Profile("dev")
public class DatabaseSeeder implements CommandLineRunner {

    private final MembershipService membershipService;
    private final UserRepository userRepository;
    private final ClubRepository clubRepository;
    private final VenueRepository venueRepository;
    private final EventRepository eventRepository;
    private final FundingApplicationRepository fundingRepository;
    private final RSVPRepository rsvpRepository;
    private final TicketRepository ticketRepository;
    private final ClubMembershipRepository clubMembershipRepository;

    @Override
    public void run(String... args) throws Exception {
        Date currentDate = new Date();

        // ----------------------------------
        // 1. Create Dummy Users
        // ----------------------------------
        List<User> users = new ArrayList<>();
        String commonPassword = "$2a$10$/IdVLa7eV9pcAcbKK74/yeI88wkJoUFO2lsNd2BJqpHfdctiGXkkK"; // "password"
        String[][] userData = {
                {"john", "john.doe@example.com", "John", "Doe", "STUDENT"},
                {"jane", "jane.smith@example.com", "Jane", "Smith", "STUDENT"},
                {"alice", "alice.wong@example.com", "Alice", "Wong", "STUDENT"},
                {"peter", "peter.parker@example.com", "Peter", "Parker", "STUDENT"},
                {"mary", "mary.jane@example.com", "Mary", "Jane", "STUDENT"},
                {"tony", "tony.stark@example.com", "Tony", "Stark", "STUDENT"},
                {"bruce", "bruce.wayne@example.com", "Bruce", "Wayne", "STUDENT"},
                {"clark", "clark.kent@example.com", "Clark", "Kent", "STUDENT"},
                {"steve", "steve.rogers@example.com", "Steve", "Rogers", "STUDENT"},
                {"natasha", "natasha.romanoff@example.com", "Natasha", "Romanoff", "STUDENT"},
                {"wanda", "wanda.maximoff@example.com", "Wanda", "Maximoff", "STUDENT"},
                {"vision", "vision@example.com", "Vision", "Synthezoid", "STUDENT"},
                {"scott", "scott.lang@example.com", "Scott", "Lang", "STUDENT"},
                {"tchalla", "tchalla@example.com", "Tchalla", "Wakanda", "STUDENT"},
                {"sam", "sam.wilson@example.com", "Sam", "Wilson", "STAFF"}
        };

        for (String[] data : userData) {
            User user = new User();
            user.setUsername(data[0]);
            user.setPassword(commonPassword);
            user.setEmail(data[1]);
            user.setFirstName(data[2]);
            user.setLastName(data[3]);
            user.getRoles().add(UserRole.valueOf("ROLE_" + data[4]));
            users.add(user);
        }
        userRepository.saveAll(users);


        // ----------------------------------
        // 2. Create Dummy Venues
        // ----------------------------------
        List<Venue> venues = new ArrayList<>();
        Object[][] venueData = {
                {"Tech Hall", "123 Tech St", 100},
                {"Art Gallery", "456 Art Ave", 50},
                {"Music Arena", "789 Music Blvd", 200},
                {"Science Center", "101 Science Way", 150},
                {"Sports Stadium", "303 Sports Rd", 300}
        };
        for (Object[] data : venueData) {
            Venue venue = new Venue();
            venue.setName((String) data[0]);
            venue.setAddress((String) data[1]);
            venue.setCapacity((Integer) data[2]);
            venue.setEvents(new HashSet<>());
            venues.add(venue);
        }
        venueRepository.saveAll(venues);


        // ----------------------------------
        // 3. Create Dummy Clubs and Add Memberships
        // ----------------------------------
        List<Club> clubs = new ArrayList<>();
        Object[][] clubData = {
                {"Tech Club", "A club for tech enthusiasts.", new BigDecimal("5000.00")},
                {"Art Club", "A club for art lovers and creators.", new BigDecimal("2000.00")},
                {"Music Club", "A club for music enthusiasts.", new BigDecimal("3000.00")}
        };

        for (Object[] data : clubData) {
            Club club = new Club();
            club.setName((String) data[0]);
            club.setDescription((String) data[1]);
            club.setFunds((BigDecimal) data[2]);
            club.setEvents(new HashSet<>());
            club.setMemberships(new HashSet<>());
            clubs.add(club);
        }
        clubRepository.saveAll(clubs);

        List<Club> existingClubs = clubRepository.findAll();
        List<User> existingUsers = userRepository.findAll();
        for (Club club : existingClubs) {
            for (User user : existingUsers) {
                if (user.getUsername().equals("john")) {
                    membershipService.addMembership(club.getId(), user.getId(), MembershipRole.ADMIN);
                } else if (user.getId() % club.getId() == 0) {
                    membershipService.addMembership(club.getId(), user.getId(), MembershipRole.MEMBER);
                }
            }
        }

        // ----------------------------------
        // 4. Create Events
        // ----------------------------------
        List<Event> events = new ArrayList<>();
        Object[][] eventData = {
                // clubName, venueName, title, description, capacity, date, cost, numAttendees
                {"Tech Club", "Tech Hall", "Tech Symposium", "An event for tech discussions and presentations.", 500, "2024-10-01", new BigDecimal("100.10"), 0},
                {"Tech Club", "Tech Hall", "Tech Hackathon", "A hackathon event for solving tech challenges.", 100, "2024-10-15", new BigDecimal("120.50"), 70},
                {"Art Club", "Art Gallery", "Art Exhibition", "A showcase of the best art from our club members.", 50, "2024-10-20", new BigDecimal("210.90"), 50},
                {"Art Club", "Art Gallery", "Art Workshop", "A hands-on workshop on painting techniques.", 50, "2024-11-01", new BigDecimal("310.30"), 35},
                {"Music Club", "Music Arena", "Music Festival", "A festival celebrating music from around the world.", 200, "2024-11-10", new BigDecimal("150.20"), 150},
                {"Music Club", "Music Arena", "Concert Night", "A concert showcasing our members' talents.", 200, "2024-11-25", new BigDecimal("500"), 180}
        };

        Map<String, Club> clubMap = clubs.stream()
                .collect(Collectors.toMap(Club::getName, Function.identity()));

        Map<String, Venue> venueMap = venues.stream()
                .collect(Collectors.toMap(Venue::getName, Function.identity()));

        for (Object[] data : eventData) {
            Event event = new Event();
            event.setClub(clubMap.get(data[0]));
            event.setVenue(venueMap.get(data[1]));
            event.setTitle((String) data[2]);
            event.setDescription((String) data[3]);
            event.setCapacity((Integer) data[4]);
            event.setDate(new Date());
            event.setCost((BigDecimal) data[6]);
            event.setNumOfAttendees((Integer) data[7]);
            event.setCreatedAt(currentDate);
            events.add(event);
        }
        eventRepository.saveAll(events);

        // ----------------------------------
        // 5. Create RSVPs
        // ----------------------------------
        List<Rsvp> rsvps = new ArrayList<>();
        Object[][] rsvpData = {
                {users.get(0), events.get(0)},
                {users.get(0), events.get(1)},
                {users.get(1), events.get(2)},
                {users.get(2), events.get(4)},
                {users.get(3), events.get(5)}
        };
        for (Object[] data : rsvpData) {
            Rsvp rsvp = new Rsvp();
            rsvp.setResponder((User) data[0]);
            rsvp.setEvent((Event) data[1]);
            rsvp.setCreatedAt(currentDate);
            rsvps.add(rsvp);
        }
        rsvpRepository.saveAll(rsvps);

        // ----------------------------------
        // 6. Create Tickets
        // ----------------------------------
        List<Ticket> tickets = new ArrayList<>();
        Object[][] ticketData = {
                {rsvps.get(0), "john.doe@example.com", "John", "Doe"},
                {rsvps.get(0), "jane.smith@example.com", "Jane", "Smith"},
                {rsvps.get(1), "jane.smith@example.com", "Jane", "Smith"},
                {rsvps.get(1), "mary.jane@example.com", "Mary", "Jane"},
                {rsvps.get(2), "alice.wong@example.com", "Alice", "Wong"},
                {rsvps.get(2), "jane.smith@example.com", "Jane", "Smith"},
                {rsvps.get(3), "peter.parker@example.com", "Peter", "Parker"},
                {rsvps.get(3), "tony.stark@example.com", "Tony", "Stark"},
                {rsvps.get(4), "alice.wong@example.com", "Alice", "Wong"},
                {rsvps.get(4), "mary.jane@example.com", "Mary", "Jane"}
        };
        for (Object[] data : ticketData) {
            Ticket ticket = new Ticket();
            ticket.setRsvp((Rsvp) data[0]);
            ticket.setEmail((String) data[1]);
            ticket.setFirstName((String) data[2]);
            ticket.setLastName((String) data[3]);
            tickets.add(ticket);
            ((Rsvp) data[0]).getTickets().add(ticket);
        }
        ticketRepository.saveAll(tickets);
        rsvpRepository.saveAll(rsvps);

        // ----------------------------------
        // 7. Create FundingApplications
        // ----------------------------------
        List<FundingApplication> fundings = new ArrayList<>();
        Object[][] fundingData = {
                {"Art Club", "Art Gallery Exhibition Support", new BigDecimal("500.00"), "DRAFT"},
                {"Music Club", "Music Club Exhibition Support", new BigDecimal("1000.00"), "SUBMITTED"}
        };
        for (Object[] data : fundingData) {
            FundingApplication funding = new FundingApplication();
            funding.setClub(clubMap.get(data[0]));
            funding.setDescription((String) data[1]);
            funding.setAmount((BigDecimal) data[2]);
            funding.setStatus(ApplicationStatus.valueOf(((String)data[3]).toUpperCase()));
            funding.setCreatedAt(currentDate);
            fundings.add(funding);
        }
        fundingRepository.saveAll(fundings);

        log.info("\n\nFull dummy data seeding completed.\n");
    }
}
