import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public abstract class Ticket implements Comparable<Ticket>
{
    private int id;
    private String title;
    private String description;
    private String requester;
    private Priority priority;
    private Status status;
    private LocalDateTime createdAt;
    private String assignee;
    private Set<String> tags;

    private static int nextId = 0;

    public Ticket(String title, String description, String requester, Priority priority)
    {
        this.id = ++nextId;
        this.title = title;
        this.description = description == null ? "" : description.trim();
        this.requester = requester;
        this.priority = priority;
        this.status = Status.OPEN;
        this.createdAt = LocalDateTime.now();
        this.assignee = null;
        this.tags = new HashSet<>();
    }

    public Ticket(String title, String requester)
    {
        this(title, "No description provided", requester, Priority.MEDIUM);
    }

    public int getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public String getRequester()
    {
        return requester;
    }

    public Priority getPriority()
    {
        return priority;
    }

    public Status getStatus()
    {
        return status;
    }

    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }

    public String getAssignee()
    {
        return assignee;
    }

    public Set<String> getTags()
    {
        return tags;
    }

    public void setDescription(String description)
    {
        if (description == null || description.isBlank())
        {
            this.description = "";
            return;
        }
        this.description = description.trim();
    }

    public String shortDescription()
    {
        if (description.length() > 40)
        {
            return description.substring(0, 40) + "...";
        }
        return description;
    }

    public void setPriority(Priority priority)
    {
        this.priority = priority;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public void setAssignee(String assignee)
    {
        this.assignee = assignee;
    }

    public void addTag(String tag)
    {
        this.tags.add(tag);
    }

    public long ageInHours()
    {
        return Duration.between(this.createdAt, LocalDateTime.now()).toHours();
    }

    public static int totalCreated()
    {
        return nextId;
    }

    public abstract double estimateEffortHours();

    @Override
    public int compareTo(Ticket other)
    {
        int p = other.priority.compareTo(this.priority);
        if (p != 0)
        {
            return p;
        }
        return this.createdAt.compareTo(other.createdAt);
    }

    @Override
    public String toString()
    {
        return "[" + id + "] " + title + " (" + priority + ", " + status + ")";
    }
}
