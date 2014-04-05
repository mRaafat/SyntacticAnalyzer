import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;

public class Compiler {

	static HashMap<String, String> definitions;
	static final char[] SKIP_CHARACTERS = { '(', ')', '[', ']', '{', '}', '.' };
	static final String[] ESCAPE = { "*", "//", "/*", "*/" };
	static int id = 0;

	public static char getSkip(String in) {
		for (int i = 0; i < SKIP_CHARACTERS.length; i++)
			if (in.contains(SKIP_CHARACTERS[i] + ""))
				return SKIP_CHARACTERS[i];
		return ' ';

	}

	public static void initMap() {
		definitions = new HashMap<String, String>();
		definitions.put(">=", "GE");
		definitions.put("public", "KW");
		definitions.put("static", "KW");
		definitions.put("class", "KW");
		definitions.put("void", "KW");
		definitions.put("boolean", "KW");
		definitions.put("false", "KW");
		definitions.put("true", "KW");
		definitions.put("int", "KW");
		definitions.put("double", "KW");
		definitions.put("float", "KW");
		definitions.put("long", "KW");
		definitions.put("String", "KW");
		definitions.put("char", "KW");
		definitions.put("byte", "KW");
		definitions.put("short", "KW");
		definitions.put("new", "KW");
		definitions.put("if", "KW");
		definitions.put("for", "KW");
		definitions.put("while", "KW");
		definitions.put(",", "FA");
		definitions.put(";", "SM");
		definitions.put("[]", "AA");
		definitions.put("=", "AO");
		definitions.put("[", "LS");
		definitions.put("]", "RS");
		definitions.put(")", "RB");
		definitions.put("(", "LB");
		definitions.put("}", "RC");
		definitions.put("{", "LC");
		definitions.put("++", "PP");
		definitions.put("-", "MO");
		definitions.put("//", "ESC");
		definitions.put("*", "MB");
		definitions.put("/*", "ESC");
		definitions.put("", "");
		definitions.put("+", "PO");
		definitions.put("/", "DB");
		definitions.put("System", "KW");
		definitions.put("out", "KW");
		definitions.put("print", "KW");
		definitions.put("println", "KW");
		definitions.put(".", "DO");
		definitions.put("return", "KW");
		definitions.put("<=", "LE");
		definitions.put("&", "LA");
		definitions.put("!=", "NE");
		definitions.put("break", "KW");
		definitions.put("<", "LT");
		definitions.put("==", "EQ");
		definitions.put("!", "LN");
		definitions.put("|", "LO");

	}

	public static String writeSplittedLines(String fileOut, String word) {
		word = word.trim();
		if (word.startsWith("\""))
			return writeWord(fileOut, word.trim());
		if (word.contains(".")) {
			String number = "not a number";
			if (word.endsWith(",") || word.endsWith(";") || word.endsWith("}"))
				number = word.substring(0, word.length() - 1);
			try {
				Double.parseDouble(number);
				int offset = 0;
				if (word.endsWith(";"))
					offset = 1;
				if (!word.contains("E")) {
					fileOut = writeWord(fileOut, number);
				} else {
					fileOut = writeWord(fileOut,
							word.substring(0, word.lastIndexOf('E')));
					fileOut += getDefintion("E") + "\t" + "E" + "\n";
					fileOut += getDefintion(word.charAt(word.lastIndexOf('E'))
							+ "")
							+ "\t" + word.charAt(word.lastIndexOf('E')) + "\n";
					String suffix = word.substring(word.lastIndexOf('E') + 2,
							word.length() - 1 - offset);
					fileOut += getDefintion(suffix) + "\t" + suffix + "\n";
				}
				if (number.length() != word.length())
					fileOut += getDefintion(word.charAt(word.length() - 1) + "")
							+ "\t" + word.charAt(word.length() - 1) + "\n";
				return fileOut;
			} catch (Exception e) {

			}
		}
		if (word.startsWith("'"))
			return writeWord(fileOut, word);
		if (word.trim().equals(""))
			return fileOut;
		if (word.equals("[]")) {
			fileOut += getDefintion(word) + "\t" + "[]" + "\n";
			return fileOut;
		}
		if (getSkip(word) == ' ') {
			fileOut = writeWord(fileOut, word);
			return fileOut;
		}
		for (int i = 0; i < word.length(); i++) {
			for (int j = 0; j < SKIP_CHARACTERS.length; j++) {
				if (word.charAt(i) == SKIP_CHARACTERS[j]) {
					if (i != 0) {
						fileOut = writeWord(fileOut, word.substring(0, i));
					}
					fileOut += getDefintion(word.charAt(i) + "") + "\t";
					fileOut += word.charAt(i) + "\n";
					return writeSplittedLines(fileOut, word.substring(i + 1));
				}
			}
		}
		return "";
	}

	public static String getDefintion(String key) {
		key = key.trim();
		if (key.contains(";") && key.length() > 1)
			key = key.substring(0, key.indexOf(";")).trim();
		try {
			Double.parseDouble(key);
			return "NM";
		} catch (Exception e) {

		}
		if (key.startsWith("'") && key.endsWith("'"))
			return "CH";
		if (key.startsWith("\""))
			return "ST";
		key = key.trim();
		String ret = "";
		if ((ret = definitions.get(key)) == null)
			return "ID";
		return ret;
	}

	public static boolean isNumber(String in) {
		try {
			Double.parseDouble(in);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String writeWord(String fileOut, String word) {
		if (word.equals("&&")) {
			fileOut += getDefintion("&") + "\t" + "&" + "\n";
			fileOut += getDefintion("&") + "\t" + "&" + "\n";
			return fileOut;
		}
		if (word.equals("||")) {
			fileOut += getDefintion("|") + "\t" + "|" + "\n";
			fileOut += getDefintion("|") + "\t" + "|" + "\n";
			return fileOut;
		}
		if (word.startsWith("-") && word.length() > 1) {
			fileOut += getDefintion("-") + "\t" + "-" + "\n";
			word = word.substring(1);
		}
		if (word.endsWith(",")) {
			String suffix = getDefintion(",") + "\t" + "," + "\n";
			return writeWord(fileOut, word.substring(0, word.lastIndexOf(",")))
					+ suffix;
		}
		if (word.endsWith("++")) {
			String suffix = getDefintion("++") + "\t" + "++" + "\n";
			return writeWord(fileOut, word.substring(0, word.lastIndexOf("++")))
					+ suffix;
		}
		if (word.endsWith("}")) {
			String suffix = getDefintion("}") + "\t" + "}" + "\n";
			return writeWord(fileOut, word.substring(0, word.lastIndexOf("}")))
					+ suffix;
		}
		if (isNumber(word)) {
			if (word.contains("E")) {
				int split = word.lastIndexOf("E");
				String number = word.substring(0, split);
				fileOut += getDefintion(number) + "\t" + number + "\n";
				fileOut += getDefintion("E") + "\t" + "E" + "\n";
				return writeWord(fileOut, word.substring(split + 1));
			}
		}
		if (word.startsWith("!") && !word.equals("!=")) {
			fileOut += getDefintion("!") + "\t" + "!" + "\n";
			word = word.substring(1);
		}
		fileOut += getDefintion(word) + "\t";
		if (word.equals(";"))
			return fileOut += ";" + "\n";
		if (!word.endsWith(";")) {
			if (word.endsWith(",")) {
				word = word.substring(0, word.lastIndexOf(","));
				fileOut += word + "\n";
				fileOut += getDefintion(",") + "\t" + "," + "\n";
				return fileOut;
			}
			return fileOut += word + "\n";
		} else {
			word = word.substring(0, word.lastIndexOf(";"));
			fileOut += word + "\n";
			fileOut += getDefintion(";") + "\t" + ";" + "\n";
			return fileOut;
		}
	}

	public static boolean startsWithEscape(String line) {
		for (int i = 0; i < ESCAPE.length; i++)
			if (line.startsWith(ESCAPE[i]))
				return true;
		return false;
	}

	public static void main(String[] args) {
		initMap();
		String fileOut = "";
		BufferedReader reader;
		String line = "";
		try {
			reader = new BufferedReader(new FileReader(
					"D:\\AI\\JafaCombiler\\src\\LCode.java"));
			PrintWriter writer = new PrintWriter("filenamee.txt");
			String word = "";
			while ((line = reader.readLine()) != null) {
				id++;
				boolean isString = false;
				boolean isChar = false;
				line = line.trim();
				for (int i = 0; i < line.length(); i++) {
					if (line.charAt(i) == '\'' && !isString)
						isChar = !isChar;
					if (line.charAt(i) == ('"') && !isChar)
						isString = !isString;
					word += line.charAt(i);
					if (startsWithEscape(line)) {
						word = "";
						continue;
					}
					if (((line.charAt(i) + "").equals(" ") || i == line
							.length() - 1) && !isString && !isChar) {
						word = word.trim();
						if (word.equals(""))
							continue;
						if (getSkip(word) == ' ' || isString || isChar) {
							fileOut = writeWord(fileOut, word);
						} else {
							if (getDefintion(word).equals("NM"))
								fileOut = writeWord(fileOut, word);
							else if (word.startsWith("\"")
									|| word.startsWith("'"))
								fileOut = writeWord(fileOut, word);
							else
								fileOut = writeSplittedLines(fileOut, word);
						}
						word = "";
					}
				}
			}
			fileOut = fileOut.substring(0, fileOut.lastIndexOf("\n"));
			writer.println(fileOut);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
