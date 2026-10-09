public class AccessRequestTicket extends Ticket implements Escalatable
{
    private String systemName;
    private String accessLevel;
    private boolean escalated;
    private int escalationLevel;

    public AccessRequestTicket(String title, String description, String requester, Priority priority, String systemName, String accessLevel)
    {
        super(title, description, requester, priority);
        this.systemName = systemName;
        this.accessLevel = accessLevel;
        this.escalated = false;
        this.escalationLevel = 0;
    }

    public String getSystemName()
    {
        return systemName;
    }

    public String getAccessLevel()
    {
        return accessLevel;
    }

    @Override
    public double estimateEffortHours()
    {
        return "ADMIN".equalsIgnoreCase(accessLevel) ? 3.0 : 1.0;
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
        return super.toString() + " [ACCESS system=" + systemName + ", level=" + accessLevel + "]";
    }
}
