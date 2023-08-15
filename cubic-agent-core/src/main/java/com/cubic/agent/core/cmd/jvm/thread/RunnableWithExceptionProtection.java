package com.cubic.agent.core.cmd.jvm.thread;

/**
 * @Author qinqixuan
 * @Date 2020/12/08
 * @Version V1.0
 **/
public class RunnableWithExceptionProtection implements Runnable{

	private Runnable run;
	private CallbackWhenException callback;

	public RunnableWithExceptionProtection(Runnable run, CallbackWhenException callback) {
		this.run = run;
		this.callback = callback;
	}

	@Override
	public void run() {
		try {
			run.run();
		} catch (Exception e) {
			callback.handle(e);
		}
	}

	public interface CallbackWhenException {
		void handle(Throwable th);
	}
}
