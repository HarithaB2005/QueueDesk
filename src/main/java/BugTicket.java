public class BugTicket extends Ticket implements Escalatable
{
    private Severity severity;
    private String stepsToReproduce;
    private boolean escalated;
    private int escalationLevel;

    public BugTicket(String title, String description, String requester, Priority priority, Severity severity, String stepsToReproduce)
    {
        super(title, description, requester, priority);
        this.severity = severity;
        this.stepsToReproduce = stepsToReproduce;
        this.escalated = false;
        this.escalationLevel = 0;
    }

    public Severity getSeverity()
    {
        return severity;
    }

    public String getStepsToReproduce()
    {
        return stepsToReproduce;
    }

    @Override
    public double estimateEffortHours()
    {
        switch (severity)
        {
            case CRITICAL:
                return 12.0;
            case HIGH:
                return 8.0;
            case MEDIUM:
                return 4.0;
            default:
                return 2.0;
        }
    }

    @Override
    public void escalate()
    {
        this.escalated = true;
        this.escalationLevel++;
    }

    @Override
    public boolean isEscalated()
    {
        return escalated;
    }

    @Override
    public int escalationLevel()
    {
        return escalationLevel;
    }

    @Override
    public String toString()
    {
        return super.toString() + " [BUG severity=" + severity + "]";
    }
}
