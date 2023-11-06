import java.util.Random;

public class Passenger
{
    private int passengerId;
    private int processingTime;
    private int arrivalTime;
    private static int maxProcessingTime;
    private static int idNum = 0;
    private Random Random = new Random();

    public Passenger(int arrivalTime)
    {
        this.arrivalTime = arrivalTime;
        processingTime = 1 + Random.nextInt(maxProcessingTime);
        passengerId = idNum++;
    }

    public int getArrivalTime()
    {
        return arrivalTime;
    }

    public int getProcessingTime()
    {
        return processingTime;
    }

    public int getId()
    {
        return passengerId;
    }

    public static void setMaxProcessingTime(int maxProcessTime)
    {
        maxProcessingTime = maxProcessTime;
    }
}