public interface Escalatable
{
    void escalate();

    boolean isEscalated();

    int escalationLevel();

    default String escalationBadge()
    {
        return isEscalated() ? "ESCALATED-L" + escalationLevel() : "NOT ESCALATED";
    }
}
