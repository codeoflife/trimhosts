package com.britoso;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class TrimHosts {

	/**
	 * @author Sonal Brito
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length == 0 || args[0].length() == 0) {
			System.out.println("Usage: TrimHosts <path to hosts file>");
		} else {
			ArrayList<String> lines = readFile(args[0]);
			if (lines == null || lines.size() == 0) {
				System.out.println("An error occured while reading the file.");
			} else {
				ArrayList<String> trimmedlines = removeDuplicates(lines);
				if (trimmedlines != null && trimmedlines.size() > 0) {
					writeFile(trimmedlines, args[0]);
				}
			}
		}
		System.out.println("Exiting.");
	}

	private static void writeFile(ArrayList<String> trimmedlines,
			String filename) {
		System.out.println("Writing to file: " + filename);
		BufferedWriter bufferedWriter = null;

		try {
			bufferedWriter = new BufferedWriter(new FileWriter(filename));
			int count = trimmedlines.size();
			for (int i = 0; i < count; i++) {
				bufferedWriter.write(trimmedlines.get(i)+"\n");//dont use .newLine()
			}

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			// Close the BufferedWriter
			try {
				if (bufferedWriter != null) {
					bufferedWriter.flush();
					bufferedWriter.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	private static ArrayList<String> removeDuplicates(ArrayList<String> lines) {
		System.out.println("Removing duplicate host definitions...");
		HashMap<String, String> map = new HashMap<String, String>(lines.size());
		String ip = null, name = null, line = null;
		int spacePos = 0;
		for (int i = 0; i < lines.size(); i++) {
			line = lines.get(i);
			if (line == null || line.length() == 0)
				continue;// skip empty lines.
			if (line.charAt(0) == '#')
				continue;// skip comments.
			spacePos = line.indexOf(" ");
			if (spacePos == -1)
			{
				//maybe theres a tab instead.
				spacePos = line.indexOf("\t");
				if (spacePos == -1)
				{
					continue;// invalid line
				}
			}
			ip = line.substring(0, spacePos);
			name = line.substring(spacePos + 1);
			map.put(name, ip);// If the map previously contained a mapping for
								// the key, the old value is replaced.
		}
		int mapcount = map.size();
		System.out.println("Found " + mapcount + " unique hostnames.");
		ArrayList<String> newlines = new ArrayList<String>(mapcount);
		TreeMap<String, String> tree = new TreeMap<String, String>(map);
		Object[] keys = tree.keySet().toArray();
		for (int i = 0; i < mapcount; i++) {
			newlines.add(map.get(keys[i]) + " " + keys[i]);
		}
		return newlines;
	}

	private static ArrayList<String> readFile(String file) {
		ArrayList<String> lines = new ArrayList<String>(2000);
		System.out.println("Reading file: " + file);
		InputStream in = null;
		int linecount = 0;
		try {
			in = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			String line = null;
			while ((line = reader.readLine()) != null) {
				lines.add(line.trim());
				linecount++;
			}
		} catch (IOException x) {
			System.out.println("Error readign the file.");
			System.err.println(x);
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
				}
		}
		System.out.println("Read " + linecount + " lines.");
		return lines;
	}

}
