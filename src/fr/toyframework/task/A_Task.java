package fr.toyframework.task;

import java.util.Date;
import java.util.TimerTask;

public abstract class A_Task extends TimerTask {
	public abstract void run();
	public abstract boolean isEnable();
	public abstract long getDelay();
	public abstract Date getFirstTime();
	public abstract long getPeriod();
}
