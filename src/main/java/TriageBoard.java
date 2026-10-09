import java.util.List;

public class TriageBoard
{
    public static void printTriageBoard(List<Ticket> tickets)
    {
        for (Ticket ticket : tickets)
        {
            System.out.println(ticket.toString() + " | effort: " + ticket.estimateEffortHours() + "h");
            System.out.println("    " + ticket.shortDescription());
            if (ticket instanceof Escalatable e)
            {
                System.out.println("    " + e.escalationBadge());
            }
        }
    }
}
