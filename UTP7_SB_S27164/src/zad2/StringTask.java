package zad2;

public class StringTask implements Runnable
{
    private final String word;
    private final int number;
    private volatile TaskState state;
    private String concatResult;

    public StringTask(String word, int number)
    {
        this.word = word;
        this.number = number;
        this.concatResult = "";
        this.state = TaskState.CREATED;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < number; i++) {
            if (state == TaskState.RUNNING) {
                this.concatResult += this.word;
            } else if (state == TaskState.ABORTED) {
                return;
            }
        }
        state = TaskState.READY;
    }

    public String getResult()
    {
        return concatResult;
    }

    public TaskState getState()
    {
        return state;
    }

    public void start()
    {
        Thread newThread = new Thread(this);
        newThread.start();
        state = TaskState.RUNNING;
    }

    public void abort()
    {
        if (state == TaskState.RUNNING)
            state = TaskState.ABORTED;
    }

    public boolean isDone()
    {
        return state == TaskState.READY || state == TaskState.ABORTED;
    }

}
