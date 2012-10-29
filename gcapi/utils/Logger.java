package gcapi.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.powerbot.game.api.methods.Environment;

public class Logger {

	private File file;
	private BufferedWriter out;
	private String logDir = null;

	public Logger(Object source) {

		Date currentDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");

		logDir = Environment.getStorageDirectory() + "\\GCLogs\\";

		System.out.println("Saving logs to: " + logDir);

		if (!new File(logDir).exists()) {
			System.out.println(logDir
					+ " does not exist, creating directories.");
			new File(logDir).mkdirs();
		}

		String logFile = logDir + "log-" + Environment.getDisplayName() + "-"
				+ source.getClass().getSimpleName() + ".txt";

		try {
			file = new File(logFile);
			if (!file.exists()) {
				file.createNewFile();
			} else {
				file.delete();
				file.createNewFile();
			}

			this.out = new BufferedWriter(new FileWriter(file, true));

			System.out.println("Started logging " + logFile + " at "
					+ format.format(currentDate));

			this.out.append("[ Log opened at " + format.format(currentDate)
					+ ". ]\n");
			this.out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void log(String str) {
		if (out != null) {
			try {
				Date currentDate = new Date();
				SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
				String log = "[" + format.format(currentDate) + "] " + str
						+ "\n";
				System.out.print(log);
				this.out.append(log);
				this.out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void close() {
		if(this.out != null) {
			try {
				this.out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}

}
