package org.wiigee.util;

public class Log {
    
        public static interface Logger {
            void write(int level, String message, Object caller);
        }

	public static final int OFF = -1;
	public static final int NORMAL = 0;
	public static final int DEBUG = 1;
	
	public static int level = Log.NORMAL;
        
        private static Logger logger = new Logger() {
            public void write(int level, String message, Object caller) {
                // console output enabled
                if(caller!=null) {
                        System.out.println(caller.getClass()+": "+message);
                } else {
                        System.out.println(message);
                }
            }
        };
	
	public static void setLevel(int n) {
		level = n;
	}
        
        public static void setLogger(Logger logger) {
            Log.logger = logger;
        }
	
	public static void write(String s) {
		write(Log.NORMAL, s, null);
	}
	
	public static void write(String s, Object o) {
		write(Log.NORMAL, s, o);
	}
	
	public static void write(int n, String s, Object o) {
		if(level>=n) {
			logger.write(n, s, o);
		}
	}
	
}
