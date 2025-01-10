package com.studentclub.studentclubbackend.seed;

import com.studentclub.studentclubbackend.constants.ApplicationStatus;
import com.studentclub.studentclubbackend.constants.Roles;
import com.studentclub.studentclubbackend.models.*;
import com.studentclub.studentclubbackend.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
@AllArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ClubRepository clubRepository;
    private final VenueRepository venueRepository;
    private final EventRepository eventRepository;
    private final FundingApplicationRepository fundingRepository;
    private final RSVPRepository rsvpRepository;
    private final TicketRepository ticketRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create Dummy Users
        List<User> users = new ArrayList<>();
        String commonPassword = "$2a$10$/IdVLa7eV9pcAcbKK74/yeI88wkJoUFO2lsNd2BJqpHfdctiGXkkK"; // password
        String[][] userData = {
                {"john", "john.doe@example.com", "John", "Doe", "STUDENT_ADMIN"},
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
                {"sam", "sam.wilson@example.com", "Sam", "Wilson", "STUDENT"},
                {"tchalla", "tchalla@example.com", "Tchalla", "Wakanda", "STUDENT"}
        };

        for (String[] data : userData) {
            User user = new User();
            user.setUsername(data[0]);
            user.setPassword(commonPassword);
            user.setEmail(data[1]);
            user.setFirstName(data[2]);
            user.setLastName(data[3]);
            user.setRole(Roles.valueOf("ROLE_" + data[4]));
            users.add(user);
        }
        userRepository.saveAll(users);


        // Create Dummy Venues
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


        // Create Dummy Clubs
        List<Club> clubs = new ArrayList<>();
        Object[][] clubData = {
                {"Tech Club", "A club for tech enthusiasts.", new BigDecimal("5000.00")},
                {"Art Club", "A club for art lovers and creators.", new BigDecimal("2000.00")},
                {"Music Club", "A club for music enthusiasts.", new BigDecimal("3000.00")}
        };
        int mod = 2;
        for (Object[] data : clubData) {
            Club club = new Club();
            club.setName((String) data[0]);
            club.setDescription((String) data[1]);
            club.setFunds((BigDecimal) data[2]);
            club.setEvents(new HashSet<>());
            club.getAdmins().add(users.getFirst());
            Set<User> members = club.getMembers();
            for (int i = 1; i < users.size(); i++) {
                if (i % mod == 0) members.add(users.get(i));
            }
            clubs.add(club);
        }
        clubRepository.saveAll(clubs);


        // Create Events
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

        Map<String, Club> clubMap = new HashMap<>();
        for (Club club : clubs) clubMap.put(club.getName(), club);

        Map<String, Venue> venueMap = new HashMap<>();
        for (Venue venue : venues) venueMap.put(venue.getName(), venue);

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
            events.add(event);
        }
        eventRepository.saveAll(events);

        // Create RSVPs
        List<RSVP> rsvps = new ArrayList<>();
        Object[][] rsvpData = {
                {users.get(0), events.get(0)},
                {users.get(0), events.get(1)},
                {users.get(1), events.get(2)},
                {users.get(2), events.get(4)},
                {users.get(3), events.get(5)}
        };
        for (Object[] data : rsvpData) {
            RSVP rsvp = new RSVP();
            rsvp.setResponder((User) data[0]);
            rsvp.setEvent((Event) data[1]);
            rsvps.add(rsvp);
        }
        rsvpRepository.saveAll(rsvps);

        // Create Tickets
        List<Ticket> tickets = new ArrayList<>();
        Object[][] ticketData = {
                {rsvps.get(0), users.get(0)},
                {rsvps.get(0), users.get(1)},
                {rsvps.get(1), users.get(1)},
                {rsvps.get(1), users.get(4)},
                {rsvps.get(2), users.get(2)},
                {rsvps.get(2), users.get(1)},
                {rsvps.get(3), users.get(3)},
                {rsvps.get(3), users.get(5)},
                {rsvps.get(4), users.get(2)},
                {rsvps.get(4), users.get(4)}
        };
        for (Object[] data : ticketData) {
            Ticket ticket = new Ticket();
            ticket.setRsvp((RSVP) data[0]);
            ticket.setAttendee((User) data[1]);
            tickets.add(ticket);
            // Associate ticket with RSVP
            ((RSVP)data[0]).getTickets().add(ticket);
        }
        ticketRepository.saveAll(tickets);
        rsvpRepository.saveAll(rsvps);

        // Create FundingApplications
        List<FundingApplication> fundings = new ArrayList<>();
        Object[][] fundingData = {
                {"Art Club", "Art Gallery Exhibition Support", new BigDecimal("500.00"), "DRAFT", new Date()},
                {"Music Club", "Music Club Exhibition Support", new BigDecimal("1000.00"), "SUBMITTED", new Date()}
        };
        for (Object[] data : fundingData) {
            FundingApplication funding = new FundingApplication();
            funding.setClub(clubMap.get(data[0]));
            funding.setDescription((String) data[1]);
            funding.setAmount((BigDecimal) data[2]);
            // Assuming an enum ApplicationStatus and setting status based on string
            // You may map strings to enum values accordingly
            funding.setStatus(ApplicationStatus.valueOf(((String)data[3]).toUpperCase()));
            funding.setCreatedAt((Date) data[4]);
            fundings.add(funding);
        }
        fundingRepository.saveAll(fundings);

        System.out.println("Full dummy data seeding completed.");
    }

}
