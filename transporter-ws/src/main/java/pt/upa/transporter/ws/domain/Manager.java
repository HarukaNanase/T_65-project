package pt.upa.transporter.ws.domain;

import pt.upa.transporter.ws.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static pt.upa.transporter.ws.JobStateView.*;

/**
 * Created by nucleardannyd on 13/04/16.
 */
public class Manager {
    private static Manager ourInstance;
    private String keyName;
    private ArrayList<JobView> _jobs;


    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyName() {
        return this.keyName;
    }

    public static Manager getInstance() {
        if (ourInstance == null) {
            ourInstance = new Manager();
        }
        return ourInstance;
    }

    private Manager() {
        _jobs = new ArrayList<>();
    }


    public ArrayList<JobView> getJobs() {
        return _jobs;
    }

    /**
     * searches for a jobview by id
     * @param id of the JobView
     * @return returns the JobView with the corresponding id
     */
    public JobView getJobView(String id){
        JobView job = null;
        for (JobView j : _jobs)
            if (j.getJobIdentifier().equals(id))
                return  j;
        return job;
    }

    /**
     * adds a JobView to the arraylist
     * @param job is JobView to be added
     */
    public void addJobView(JobView job){
        _jobs.add(job);
    }

    /**
     * clears the _jobs arraylist
     */
    public void clearJobs(){
        _jobs.clear();
    }

    public void changeState(JobView job, JobStateView state){
        Timer t = new Timer();
        TimerTask newState = new TimerTask() {
            @Override
            public void run() {
                job.setJobState(state);
            }
        };
        t.schedule(newState, ThreadLocalRandom.current().nextInt(1000,5000));
    }

    /**
     * Simulates a Transportation job
     * @param job is the job to be simulated
     */
    public void simulateJob(JobView job){
        Runnable simulate = () -> {
            changeState(job, HEADING);
            changeState(job, ONGOING);
            changeState(job, COMPLETED);
        };
        Thread thread = new Thread(simulate);
        thread.start();
    }
}
