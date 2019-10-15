package jarLinuxAlso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLine {

	public static void main(String args[]) throws IOException, InterruptedException {
		//
		ProcessBuilder processBuilder = new ProcessBuilder();
		//
		if (System.getProperty("os.name").contains("Windows"))
			processBuilder.command("cmd.exe", "/c", "ping -n 3 www.google.com");
		else
			processBuilder.command("bash", "-c", "ping -c 3 www.google.com");
		//
		Process process = processBuilder.start();
		//
		BufferedReader reader = new BufferedReader((new InputStreamReader(process.getInputStream())));

		String line;

		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		int exitCode = process.waitFor();
		System.out.println("\n Process exited with code " + exitCode);
	}

}
