package de.hyper.worlds.common.util;

import java.util.ArrayList;
import java.util.Arrays;

public class Converter {

	static ArrayList<Character> numbers = new ArrayList<Character>(
			Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));

	public static Integer getInteger(String string) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < string.length(); i++) {
			if (numbers.contains(string.charAt(i)) || string.charAt(i) == '-')
				sb.append(string.charAt(i));
			if (string.charAt(i) == '.')
				return Integer.parseInt(new String(sb).length() > 0 ? new String(sb) : "0");
		}
		return Integer.parseInt(new String(sb).length() > 0 ? new String(sb) : "0");
	}

	public void g() {

	}

	public static Long getPositiveLong(String string) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < string.length(); i++) {
			if (numbers.contains(string.charAt(i)))
				sb.append(string.charAt(i));
			if (string.charAt(i) == '.')
				return Long.parseLong(new String(sb).length() > 0 ? new String(sb) : "0");
		}
		return Long.parseLong(new String(sb).length() > 0 ? new String(sb) : "0");
	}

	public static Integer getPositiveInteger(String string) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < string.length(); i++) {
			if (numbers.contains(string.charAt(i)))
				sb.append(string.charAt(i));
			if (string.charAt(i) == '.')
				return Integer.parseInt(new String(sb).length() > 0 ? new String(sb) : "0");
		}
		return Integer.parseInt(new String(sb).length() > 0 ? new String(sb) : "0");
	}

	public static Float getPositiveFloat(String string) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < string.length(); i++) {
			if (numbers.contains(string.charAt(i)) || (string.charAt(i) == '.' && !sb.toString().contains(".")))
				sb.append(string.charAt(i));
		}
		return Float.parseFloat(new String(sb).length() > 0 ? new String(sb) : "0");
	}

	public static Float getFloat(String string) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < string.length(); i++) {
			if (numbers.contains(string.charAt(i)) || (string.charAt(i) == '.' && !sb.toString().contains("."))
					|| string.charAt(i) == '-')
				sb.append(string.charAt(i));
		}
		return Float.parseFloat(new String(sb).length() > 0 ? new String(sb) : "0");
	}

	public static boolean getBoolean(String string) {
		string = string.toLowerCase();
		return (string.equals("true") || string.equals("on") || string.equals("enable") || string.equals("an")
				|| string.equals("✔"));
	}

	public static String getString(boolean value) {
		return value ? "on" : "off";
	}

	public static Double getDouble(String string) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < string.length(); i++) {
			if (numbers.contains(string.charAt(i)) || (string.charAt(i) == '.' && !sb.toString().contains("."))
					|| string.charAt(i) == '-')
				sb.append(string.charAt(i));
		}
		return Double.parseDouble(new String(sb).length() > 0 ? new String(sb) : "0");
	}

	public static Long getLong(String string) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < string.length(); i++) {
			if (numbers.contains(string.charAt(i)) || string.charAt(i) == '-')
				sb.append(string.charAt(i));
			if (string.charAt(i) == '.')
				return Long.parseLong(new String(sb).length() > 0 ? new String(sb) : "0");
		}
		return Long.parseLong(new String(sb).length() > 0 ? new String(sb) : "0");
	}
}
