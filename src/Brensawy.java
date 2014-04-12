import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class Brensawy {
	static BufferedReader bf = null;
	static Stack<String> curleyStack = null;
	static int index = 0;
	static ArrayList<String> currentBlock = null;
	static ArrayList<String> inputFileTokens = null;// The array list that will
													// hold all the input file
													// tokens
	static ArrayList<String> inputFileTypes = null;

	//

	public static void main(String[] death) throws IOException {
		inputFileTokens = new ArrayList<String>();
		curleyStack = new Stack<String>();
		currentBlock = new ArrayList<String>();

		bf = new BufferedReader(new FileReader(new File("code.txt")));
		String token;
		while ((token = bf.readLine()) != null) {

			inputFileTokens.add(token.split("\t")[0]);
			inputFileTypes.add(token.split("\t")[1]);

		}

		System.out.print(parseWhileHead(inputFileTokens));
		// parseClass();
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

	public static boolean parseWhileHead(ArrayList<String> token) {
		boolean result = false;
		int originalIndex = index;

		if (token.get(index).equals("WL")) {
			index++;
			if (token.get(index).equals("LB")) {
				index++;
				if (token.get(index).equals("BV")
						|| token.get(index).equals("ID")) {
					index++;
					if (token.get(index).equals("RB")) {
						result = true;
					} else
						index = originalIndex;
				} else
					index = originalIndex;
			} else
				index = originalIndex;
		}

		return result;

	}

	public static boolean parseStatement(ArrayList<String> token) {
		boolean result = false;
		int originIndex = index;
		int typeIndex = 0;
		if (token.get(index).equals("DT")) {
			typeIndex = index;
			index++;
			if (token.get(index).equals("ID")) {
				index++;
				if (token.get(index).equals("AO")) {
					index++;
					if (token.get(index).equals("AN")) {
						index++;
						if (token.get(index).equals("SM")) {
							result = true;
							// Not handling the match of correct data types with
							// assigned values, reaching this part of the
							// code will make sure I have the Form Datatype
							// Identifier = Alpha_numeric;
						} else
							index = originIndex;
					} else
						index = originIndex;
				} else
					index = originIndex;
			} else
				index = originIndex;
		}

		return result;

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
					return true;
					// break;
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
		String token = getToken();
		if (token.equals("AC")) {
			token = getToken();
			if (token.equals("ST")) {
				token = getToken();
				if (token.equals("RT")) {
					if (parseMethodHead()) {
						return true;
					}
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
