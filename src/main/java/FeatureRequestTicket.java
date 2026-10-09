public class FeatureRequestTicket extends Ticket
{
    private int votes;
    private String businessJustification;

    public FeatureRequestTicket(String title, String description, String requester, Priority priority, int votes, String businessJustification)
    {
        super(title, description, requester, priority);
        this.votes = votes;
        this.businessJustification = businessJustification;
    }

    public int getVotes()
    {
        return votes;
    }

    public String getBusinessJustification()
    {
        return businessJustification;
    }

    @Override
    public double estimateEffortHours()
    {
        return 6.0 + votes * 0.5;
    }

    @Override
    public String toString()
    {
        return super.toString() + " [FEATURE votes=" + votes + "]";
    }
}
