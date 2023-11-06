import java.util.Scanner;

public class AirlineCheckinSim
{
    private PassengerQueue frequentFlyerQueue = new PassengerQueue("Frequent Flyer");
    private PassengerQueue regularPassengerQueue = new PassengerQueue("Regular Passenger");
    private int frequentFlyerMax;
    private int maxProcessingTime;
    private int totalTime;
    private boolean showAll;
    private int clock = 0;
    private int timeDone;
    private int frequentFlyersSinceRP;

    public static void main(String args[])
    {
        AirlineCheckinSim sim = new AirlineCheckinSim();
        sim.enterData();
        sim.runSimulation();
        sim.showStats();
        System.exit(0);
    }

    private void runSimulation()
    {
        for (clock = 0; clock < totalTime; clock++)
        {
            frequentFlyerQueue.checkNewArrival(clock, showAll);
            regularPassengerQueue.checkNewArrival(clock, showAll);
            if (clock >= timeDone)
            {
                startServe();
            }
        }
    }

    private void enterData()
    {
        Scanner in = new Scanner(System.in);

        System.out.print("Expected number of frequent flyer arrivals per hour: ");
        frequentFlyerQueue.arrivalRate = in.nextDouble() / 60;

        System.out.print("Expected number of regular passenger arrivals per hour: ");
        regularPassengerQueue.arrivalRate = in.nextDouble() / 60;

        System.out.print("The maximum number of frequent flyers served between regular passengers: ");
        frequentFlyerMax = in.nextInt();

        System.out.print("Maximum service time in minutes: ");
        maxProcessingTime = in.nextInt();
        Passenger.setMaxProcessingTime(maxProcessingTime);

        System.out.print("The total simulation time in minutes: ");
        totalTime = in.nextInt();

        System.out.print("Display minute-by-minute trace of simulation (Y or N): ");
        if (in.next().equalsIgnoreCase("Y"))
            showAll = true;
        else if (in.next().equalsIgnoreCase("N"))
            showAll = false;
    }

    private void startServe()
    {
        if (!frequentFlyerQueue.isEmpty() && ((frequentFlyersSinceRP <= frequentFlyerMax) || regularPassengerQueue.isEmpty()))
        {
            frequentFlyersSinceRP++;
            timeDone = frequentFlyerQueue.update(clock, showAll);
        }
        else if (!regularPassengerQueue.isEmpty())
        {
            frequentFlyersSinceRP = 0;
            timeDone = regularPassengerQueue.update(clock, showAll);
        }
        else if (showAll)
        {
            System.out.println("Time is " + clock + ": server is idle");
        }
    }

    private void showStats()
    {
        System.out.println("\nThe number of regular passengers served was " + regularPassengerQueue.getNumServed());
        double averageWaitingTime = (double) regularPassengerQueue.getTotalWait() / (double) regularPassengerQueue.getNumServed();
        System.out.println(" with an average waiting time of " + averageWaitingTime);

        System.out.println("The number of frequent flyers served was " + frequentFlyerQueue.getNumServed());
        averageWaitingTime = (double) frequentFlyerQueue.getTotalWait() / (double) frequentFlyerQueue.getNumServed();
        System.out.println(" with an average waiting time of " + averageWaitingTime);

        System.out.println("Passengers in frequent flyer queue: " + frequentFlyerQueue.size());
        System.out.println("Passengers in regular passenger queue: " + regularPassengerQueue.size());
    }
}