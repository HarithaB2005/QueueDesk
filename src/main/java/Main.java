import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            run();
        }
        catch (TicketNotFoundException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void run()
    {
        List<Ticket> tickets = new ArrayList<>();

        BugTicket b1 = new BugTicket("Login fails", "Users cannot login after the latest update was installed on the server", "alice", Priority.CRITICAL, Severity.HIGH, "1. Open app 2. Click login 3. Error shown");
        FeatureRequestTicket f1 = new FeatureRequestTicket("Dark mode", "Add dark mode to the app", "bob", Priority.MEDIUM, 42, "Requested by many users");
        AccessRequestTicket a1 = new AccessRequestTicket("VPN access", "Need VPN for remote work", "carol", Priority.HIGH, "CorpVPN", "STANDARD");

        b1.setAssignee("dave");
        f1.setAssignee("dave");
        a1.setAssignee("eve");
        b1.addTag("urgent");
        b1.escalate();

        tickets.add(b1);
        tickets.add(f1);
        tickets.add(a1);

        System.out.println("Triage board:");
        TriageBoard.printTriageBoard(tickets);

        Repository<Ticket> repo = new Repository<>();
        for (Ticket t : tickets)
        {
            repo.add(t);
        }

        System.out.println("\nFind by id 1: " + repo.findById(1));
        System.out.println("\nGrouped by assignee: " + repo.groupByAssignee());

        PriorityQueue<Ticket> naturalQueue = new PriorityQueue<>(tickets);
        System.out.println("\nNatural order poll: " + naturalQueue.poll());

        PriorityQueue<Ticket> assigneeQueue = new PriorityQueue<>(TicketComparators.byAssigneeThenPriority());
        for (Ticket t : tickets)
        {
            assigneeQueue.offer(t);
        }
        System.out.println("Assignee-then-priority poll: " + assigneeQueue.poll());

        Ticket extra = new BugTicket("Cache stale", "Old data served", "irene", Priority.HIGH, Severity.MEDIUM, "1. Load page 2. See old data");
        naturalQueue.offer(extra);
        System.out.println("After offer(), next in natural queue: " + naturalQueue.poll());

        System.out.println("\nOldest ticket: " + Repository.oldest(tickets));

        try
        {
            List<Ticket> loaded = TicketFileLoader.load(Path.of("tickets.txt"));
            System.out.println("\nLoaded " + loaded.size() + " tickets from file");
            tickets.addAll(loaded);
        }
        catch (Exception e)
        {
            System.out.println("Error loading tickets: " + e.getMessage());
        }

        try
        {
            ReportGenerator.generate(tickets, Path.of("report.txt"));
            System.out.println("Report written to report.txt");
        }
        catch (Exception e)
        {
            System.out.println("Error writing report: " + e.getMessage());
        }

        System.out.println("\nTotal tickets created: " + Ticket.totalCreated());

        try
        {
            TicketFileLoader.load(Path.of("bad_tickets.txt"));
        }
        catch (Exception e)
        {
            System.out.println("\nBad file rejected: " + e.getMessage());
        }

        System.out.println("\nLooking up ticket 999...");
        repo.findById(999);
        System.out.println("This line never prints");
    }
}
