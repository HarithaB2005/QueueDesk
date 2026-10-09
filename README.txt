QueueDesk - Java Fundamentals (Weeks 1-4)

Needs Java 17 or newer and Maven. Run from inside this folder:
    mvn package
    java -jar target/queuedesk-1.0.jar

Build 1 on its own:
    java -cp target/classes QueueSimulator

Code is in src/main/java. tickets.txt and bad_tickets.txt stay in this folder,
because Main reads tickets.txt from where you run it.

QueueSimulator: Build 1, asks for a starting ticket number and a count.
Main: Builds 2 to 5 together. Reads tickets.txt, writes report.txt.
bad_tickets.txt is a deliberately wrong file to show InvalidTicketDataException.

tickets.txt line format:
    TYPE|title|description|requester|priority|extra1|extra2
    BUG     -> extra1 = severity, extra2 = steps to reproduce
    FEATURE -> extra1 = votes,    extra2 = business justification
    ACCESS  -> extra1 = system,   extra2 = access level
