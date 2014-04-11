import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class Brensawy {
	static BufferedReader bf = null;
	static Stack<String> curleyStack = null;
	static ArrayList<String> currentBlock = null;

	public static void main(String[] death) throws IOException {
		bf = new BufferedReader(new FileReader(new File("Code01out.java")));
		curleyStack = new Stack<String>();
		currentBlock = new ArrayList<String>();
		parseClass();
		// while(bf.ready()){
		// String token = bf.readLine();
		// String [] splitted = token.split("\t");
		//
		// }
	}

	public static boolean parseClass() throws IOException {
		String[] classDef = { "CS", "ID", "LC" };
		for (int i = 0; i < classDef.length; i++) {
			String token = getToken();
			if (!token.equals(classDef[i]))
				return false;
		}
		curleyHandle(1);
		if (!parseClassBlock()) {
			return false;
		}
		if (curleyStack.empty())
			return true;
		return false;
	}

	public static boolean parseClassBlock() {
		while (true) {
			String token = getToken();
			if (token.equals("LC"))
				curleyHandle(1);
			else if (token.equals("RC"))
				curleyHandle(2);
			if (curleyStack.empty())
				if (!getToken().equals("")) {
					return false;
				} else {
					return handleClassBlock();
					break;
				}
			currentBlock.add(token);
		}
	}

	public static boolean curleyHandle(int x) {
		// 1 for { LC
		// 2 for } RC
		if (x == 1) {
			curleyStack.push("LC");
		} else {
			if (curleyStack.peek() == "LC")
				curleyStack.pop();
			else if (curleyStack.empty())
				return false;
			else
				curleyStack.push("RC");
		}
		return true;
	}

	public static String getToken() {
		try {
			return bf.readLine().split("\t")[0];
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public static boolean parseMethodHead() {
		if (getToken().equals("AC")) {
			if (getToken().equals("ST")) {
				if (getToken().equals("RT")) {
					return true;
				}
			}
		}
		return false;

	}

	/***
	 * Check the correctness of the input arguments to a method
	 * 
	 * @return True if the input arguments match the java conventions
	 */
	public static boolean parseMethodInput() {
		String token = getToken();
		boolean result = false;
		boolean lastChComma = false;
		boolean lastChID = false;
		boolean lastChDT = false;
		boolean lastChLB = false;
		if (token.equals("LB")) {
			token = getToken();
			lastChLB = true;
			while (true) {
				// If the current token is RB and last token = comma, or Data
				// type, return false.
				if ((token.equals("RB") && lastChComma)
						|| (token.equals("RB") && lastChDT)) {
					result = false; // found Right paranthesis right after a
									// comma
					break;
				} else {
					// If the current token is RB and last token = ID, or left
					// bracket, return true.
					if (token.equals("RB") && lastChID || token.equals("RB")
							&& lastChLB) {
						result = true;
						break;
					} else {

						if (token.equals("DT")) {
							lastChLB = false;
							token = getToken();
							lastChDT = true;
							lastChID = false;
							lastChComma = false;
							if (token.equals("ID")) {
								token = getToken();
								lastChDT = false;
								lastChID = true;
								lastChComma = false;
								if (token.equals("FA")) {
									token = getToken();
									lastChComma = true;
									lastChDT = false;
									lastChID = false;
								} // else
									// The next token is not comma, however from
									// the previous two condition, it is sure
									// that,
									//
									// result = true;
							} else {
								// The last token was Datatype, but no ID found.
								result = false;
								break;
							}
						} else {
							// The token is not RB nor Datatype, so return false
							result = false;
							break;
						}
					}
				}
			}
		} else
			result = false;
		return result;
	}
}
