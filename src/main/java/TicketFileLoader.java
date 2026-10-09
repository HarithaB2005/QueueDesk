import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TicketFileLoader
{
    public static List<Ticket> load(Path file) throws IOException, InvalidTicketDataException
    {
        List<Ticket> tickets = new ArrayList<>();
        int lineNumber = 0;

        try (BufferedReader reader = Files.newBufferedReader(file))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                lineNumber++;
                if (line.isBlank())
                {
                    continue;
                }

                String[] parts = line.split("\\|");
                if (parts.length != 7)
                {
                    throw new InvalidTicketDataException("Wrong field count at line " + lineNumber);
                }

                String type = parts[0].trim().toUpperCase();
                String title = parts[1].trim();
                String description = parts[2].trim();
                String requester = parts[3].trim();
                String extraOne = parts[5].trim();
                String extraTwo = parts[6].trim();
                Ticket ticket;

                try
                {
                    Priority priority = Priority.valueOf(parts[4].trim().toUpperCase());

                    if (type.equals("BUG"))
                    {
                        ticket = new BugTicket(title, description, requester, priority, Severity.valueOf(extraOne.toUpperCase()), extraTwo);
                    }
                    else if (type.equals("FEATURE"))
                    {
                        ticket = new FeatureRequestTicket(title, description, requester, priority, Integer.parseInt(extraOne), extraTwo);
                    }
                    else if (type.equals("ACCESS"))
                    {
                        ticket = new AccessRequestTicket(title, description, requester, priority, extraOne, extraTwo);
                    }
                    else
                    {
                        throw new InvalidTicketDataException("Unknown ticket type at line " + lineNumber);
                    }
                }
                catch (IllegalArgumentException e)
                {
                    throw new InvalidTicketDataException("Invalid value at line " + lineNumber);
                }

                tickets.add(ticket);
            }
        }

        return tickets;
    }
}
