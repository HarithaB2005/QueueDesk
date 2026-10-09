import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;

public class ReportGenerator
{
    public static void generate(List<Ticket> tickets, Path outputFile) throws IOException
    {
        Map<Status, Integer> statusCount = new TreeMap<>();
        Map<Priority, Integer> priorityCount = new TreeMap<>();
        Ticket oldestOpen = null;

        StringBuilder sb = new StringBuilder();
        sb.append("End of Day Report\n");

        for (Ticket t : tickets)
        {
            statusCount.merge(t.getStatus(), 1, Integer::sum);
            priorityCount.merge(t.getPriority(), 1, Integer::sum);

            if (t.getStatus() == Status.OPEN)
            {
                if (oldestOpen == null || t.getCreatedAt().isBefore(oldestOpen.getCreatedAt()))
                {
                    oldestOpen = t;
                }
            }
        }

        sb.append("\nCounts by status:\n");
        for (Map.Entry<Status, Integer> e : statusCount.entrySet())
        {
            sb.append(e.getKey()).append(": ").append(e.getValue()).append("\n");
        }

        sb.append("\nCounts by priority:\n");
        for (Map.Entry<Priority, Integer> e : priorityCount.entrySet())
        {
            sb.append(e.getKey()).append(": ").append(e.getValue()).append("\n");
        }

        sb.append("\nOldest open ticket: ");
        sb.append(oldestOpen == null ? "None" : oldestOpen.toString());
        sb.append("\n");

        sb.append("\nOverdue CRITICAL tickets (age > 24h):\n");
        for (Ticket t : tickets)
        {
            if (t.getPriority() == Priority.CRITICAL && t.ageInHours() > 24)
            {
                sb.append(t.toString()).append("\n");
            }
        }

        try (BufferedWriter writer = Files.newBufferedWriter(outputFile))
        {
            writer.write(sb.toString());
        }
    }
}
