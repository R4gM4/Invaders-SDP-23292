package engine;

/**
 * Implements a simple game timer to measure elapsed time.
 * @author Amartsogt / CHO
 */
public class GameTimer {

    private long startTime;
    private long stopTime;
    private boolean running;
    private boolean paused;
    private long pausedStartTime;
    private long pausedAccumulated;
    
    public GameTimer() {
        this.startTime = 0L;
        this.stopTime = 0L;
        this.running = false;
        this.paused = false;
        this.pausedStartTime = 0L;
        this.pausedAccumulated = 0L;
    }

    /**
     * Starts the timer.
     */
    public void start() {
        this.startTime = System.nanoTime();
        this.running = true;
        this.stopTime = 0L;
        this.paused = false;
        this.pausedAccumulated = 0L;
    }

    /**
     * Stops the timer.
     */
    public void stop() {
        if (this.running) {
            if (this.paused) {
                resume();
            }
            this.stopTime = System.nanoTime();
            this.running = false;
        }
    }

    /**
     * Pauses the timer without losing elapsed time.
     */
    public void pause() {
        if (this.running && !this.paused) {
            this.paused = true;
            this.pausedStartTime = System.nanoTime();
        }
    }

    /**
     * Resumes the timer after being paused.
     */
    public void resume() {
        if (this.running && this.paused) {
            this.pausedAccumulated += System.nanoTime() - this.pausedStartTime;
            this.paused = false;
        }
    }

    /**
     * @return Elapsed time in milliseconds.
     */
    public long getElapsedTime() {
        final long endTime;
        if (this.running) {
            endTime = this.paused ? this.pausedStartTime : System.nanoTime();
        } else {
            endTime = this.stopTime;
        }
        return (endTime - this.startTime - this.pausedAccumulated) / 1_000_000;
    }

    /**
     * Checks if the timer is currently running.
     * @return True if the timer is running.
     */
    public boolean isRunning() {
        return this.running;
    }
}
