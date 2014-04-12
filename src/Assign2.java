import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class Assign2 {
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
		inputFileTypes = new ArrayList<String>();
		curleyStack = new Stack<String>();
		currentBlock = new ArrayList<String>();

		bf = new BufferedReader(new FileReader(new File("test1.txt")));
		String token;
		while (bf.ready()) {
			token = bf.readLine();
			System.out.println(token);
			inputFileTokens.add(token.split("\t")[0]);
			inputFileTypes.add(token.split("\t")[1]);

		}

		//System.out.print(parseWhileHead(inputFileTokens));
		 System.out.println(parseClass());
	}

	public static boolean parseClass() throws IOException {
		String[] classDef = { "CL", "ID", "LC" };
		for (int i = 0; i < classDef.length; i++) {
			String token = getToken();
			if (!token.equals(classDef[i]))
				return false;
		}
		curleyHandle(1);
		return parseClassBlock();
	}

	public static boolean parseWhileHead() {
		ArrayList<String> token = inputFileTokens;
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
	public static boolean checkEndClass(){
		curleyHandle(2);
		System.out.println(index);
		if(index == inputFileTokens.size() && curleyStack.empty())
			return true;
		return false;
	}

	public static boolean parseClassBlock() {
		boolean heads = false, blocks = false;
		while (true) {
			String token = getToken();
			System.out.println(token + "fff");
			if(token.equals("RC"))
				if(checkEndClass())
					return true;
				else
					return false;
			heads = parseMethodHead();
			blocks = parseMethodBlock(curleyStack.size());
			if(!heads || !blocks)
				return false;
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
			return inputFileTokens.get(index++);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public static boolean parseMethodHead() {
		int currentIndex = index;
		String token = getToken();
		if (token.equals("AC")) {
			token = getToken();
			if (token.equals("ST")) {
				token = getToken();
				if (token.equals("RT")) {
					token = getToken();
					if(token.equals("ID"))
						if (parseMethodInput()) {
							return true;
						}
				}
			}
		}
		index = currentIndex;
		token = getToken();
		if (token.equals("ST")) {
			token = getToken();
			if (token.equals("RT")) {
				token = getToken();
				if(token.equals("ID"))
					if (parseMethodInput()) {
						return true;
					}
				}
			}
		index = currentIndex;
		token = getToken();
		if (token.equals("ST")) {
			token = getToken();
			if (token.equals("RT")) {
				token = getToken();
				if(token.equals("ID"))
					if (parseMethodInput()) {
						return true;
					}
				}
			}
		return false;

	}
	public static boolean parseMethodBlock(int level){
		String token = getToken();
		boolean blockParsed = false;
		if(!token.equals("LC"))
			return false;
		curleyHandle(1);
		while(true){
			token = getToken();
			if(token.equals("RC")){
				curleyHandle(2);
				if(curleyStack.size() == level)
					return true;
			}
			if(!parseBlock())
				return false;
		}		
	}
	
	public static boolean parseBlock(){
		int currentIndex = index;
		boolean res = true;
		if(parseIfHead())
			res = parseBlock();
		index = currentIndex;
		if(parseWhileHead())
			res = parseBlock();
		index = currentIndex;
		if(parseForHead())
			res = parseBlock();
		index = currentIndex;
		if(parseStatment())
			res = parseBlock();
		
		return res;
	}
	
	public static boolean parseStatment(){
		int currentIndex = index;
		String token = getToken();
		if(token.equals("AC")){
			token = getToken();
			if(token.equals("DT")){
				token = getToken();
				if(token.equals("ID")){
					token = getToken();
					if(token.equals("AO")){
						token = getToken();
						if(token.equals("ST") || token.equals("ID") || token.equals("NM")){
							boolean lastOp = false;
							while(true){
								token = getToken();
								if(token.equals("SM") && !lastOp)
									return true;
								if(token.equals("OPP"))
									lastOp = true;
								if(token.equals("ST") || token.equals("ID") || token.equals("NM"))
									lastOp = false;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	public static boolean parseForHead(){
		int currentIndex = index;
		String token = getToken();
		if(token.equals("FR")){
			token = getToken();
			if(token.equals("LB")){
				token = getToken();
				if(token.equals("ID")){
					token = getToken();
					if(token.equals("AO")){
						token = getToken();
						if(token.equals("NM")){
							token = getToken();
							if(token.equals("SM")){
								token = getToken();
								if(token.equals("CND")){
									token = getToken();
									if(token.equals("SM")){
										token = getToken();
										if(token.equals("ID")){
											token = getToken();
											if(token.equals("PP") || token.equals("MM")){
												token = getToken();
												if(token.equals("RB")){
													return true;
												}
											}
										}
									}
								}
							}
						}
					}	
				}
			}
		}
		return false;
	}
	
	public static boolean parseIfHead(){
		int currentIndex = index;
		String token = getToken();
		if(token.equals("IF")){
			token = getToken();
			if(token.equals("LB")){
				token = getToken();
				if(token.equals("CND")){
					token = getToken();
					if(token.equals("RB")){
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
		String token = inputFileTokens.get(index);
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
			token = getToken();
			}
		} else
			result = false;
		return result;
	}
}
