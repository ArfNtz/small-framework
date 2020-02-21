package fr.toyframework.task;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;

public class Scheduler {

	private Map<A_Task,Timer> tasks;
	
	
	public static void run_now(A_Task task) {new Timer().schedule(task, 0l);}
	
	public void run() {
		Iterator<A_Task> i_tasks = getTasks().keySet().iterator();
		A_Task t;
		Timer timer;
		// FB20141026 - 03/03/2015
		while (i_tasks.hasNext()&&(t=i_tasks.next()).isEnable()) {
			timer = new Timer();
			if (t.getFirstTime() == null)	timer.schedule(t,t.getDelay(),t.getPeriod());
			else							timer.schedule(t,t.getFirstTime(),t.getPeriod());			
			getTasks().put(t, timer);
		}
	}

	public void addTask(A_Task t) {getTasks().put(t,null);}

	public Map<A_Task,Timer> getTasks() {
		if (tasks==null) tasks = new HashMap<A_Task,Timer>();
		return tasks;
	}

	public void setTasks(Map<A_Task,Timer> tasks) {
		this.tasks = tasks;
	}

}
