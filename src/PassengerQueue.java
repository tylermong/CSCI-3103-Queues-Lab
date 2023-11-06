import java.util.LinkedList;
import java.util.Queue;

public class PassengerQueue
{
    private Queue<Passenger> theQueue;
    private int numServed;
    private int totalWait;
    private String queueName;
    private int serviceTime;
    double arrivalRate;

    public PassengerQueue(String queueName)
    {
        numServed = 0;
        totalWait = 0;
        this.queueName = queueName;
        theQueue = new LinkedList<>();
    }

    public void checkNewArrival(int clock, boolean showAll)
    {
        if (Math.random() < arrivalRate)
        {
            theQueue.add(new Passenger(clock));
            if (showAll)
            {
                System.out.println("Time is " + clock + ": " + queueName + " arrival, new queue size is " + theQueue.size());
            }
        }
    }

    public int update(int clock, boolean showAll)
    {
        Passenger nextPassenger = theQueue.remove();
        int timeStamp = nextPassenger.getArrivalTime();
        int wait = clock - timeStamp;
        totalWait += wait;
        numServed++;
        if (showAll)
        {
            System.out.println("Time is " + clock + ": Serving " + queueName + " with time stamp " + timeStamp + ", service time is " + nextPassenger.getProcessingTime());
        }
        return clock + nextPassenger.getProcessingTime();
    }

    public int getTotalWait()
    {
        return totalWait;
    }

    public int getNumServed()
    {
        return numServed;
    }

    public boolean isEmpty()
    {
        return theQueue.isEmpty();
    }

    public int size()
    {
        return theQueue.size();
    }
}