import java.util.Scanner;

public class QueueSimulator
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter starting ticket number: ");
        int startTicket = sc.nextInt();

        System.out.print("Enter number of customers to simulate: ");
        int count = sc.nextInt();

        int served = 0;
        int vip = 0;
        int priorityRecheck = 0;

        for (int i = 0; i < count; i++)
        {
            int ticketNumber = startTicket + i;
            System.out.println("Now serving ticket #" + ticketNumber);
            served++;

            if (ticketNumber % 10 == 0)
            {
                System.out.println("  -> priority recheck");
                priorityRecheck++;
            }
            else if (ticketNumber % 5 == 0)
            {
                System.out.println("  -> VIP lane");
                vip++;
            }
        }

        System.out.println();
        System.out.println("Total served: " + served);
        System.out.println("VIP lane: " + vip);
        System.out.println("Priority recheck: " + priorityRecheck);

        sc.close();
    }
}
