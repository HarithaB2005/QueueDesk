import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repository<T extends Ticket>
{
    private List<T> tickets;

    public Repository()
    {
        this.tickets = new ArrayList<>();
    }

    public void add(T ticket)
    {
        tickets.add(ticket);
    }

    public List<T> all()
    {
        return Collections.unmodifiableList(tickets);
    }

    public T findById(int id)
    {
        for (T ticket : tickets)
        {
            if (ticket.getId() == id)
            {
                return ticket;
            }
        }
        throw new TicketNotFoundException("No ticket found with id " + id);
    }

    public Map<String, List<T>> groupByAssignee()
    {
        Map<String, List<T>> map = new HashMap<>();
        for (T ticket : tickets)
        {
            String key = ticket.getAssignee() == null ? "UNASSIGNED" : ticket.getAssignee();
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(ticket);
        }
        return map;
    }

    public static <T extends Ticket> T oldest(List<T> tickets)
    {
        T oldest = null;
        for (T ticket : tickets)
        {
            if (oldest == null || ticket.getCreatedAt().isBefore(oldest.getCreatedAt()))
            {
                oldest = ticket;
            }
        }
        return oldest;
    }
}
