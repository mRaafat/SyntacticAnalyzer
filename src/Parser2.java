import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Parser2 {
	
	public static boolean isNum(String s){
		for(int i=0 ; i < s.length() ; i++)
			if("0123456789".indexOf(s.charAt(i)) < 0)
				return false;
		return true;
	}
	public static String checkWord(String y) {
		//String result = "";
		y = y.trim();
		if(y.equals(" ") || y.equals(""))
			return "";
		String id_pattern = "([A-Za-z_])*|([A-Za-z_]+([0-9]*)?)*";
		String num_pattern = "\\d+\\.{0,1}\\d*";
		String neg_num_pattern = "-\\d+\\.{0,1}\\d*";
		if (y.equals("class") || y.equals("static") || y.equals("public")
				|| y.equals("int") || y.equals("void")
				|| y.equals("double") || y.equals("float")
				|| y.equals("while") || y.equals("if")
				|| y.equals("for") || y.equals("switch")
				|| y.equals("char") || y.equals("String")
				|| y.equals("System") || y.equals("out")
				|| y.equals("false") || y.equals("println")
				|| y.equals("true") || y.equals("return")
				|| y.equals("long") || y.equals("boolean")
				|| y.equals("byte") || y.equals("short")
				|| y.equals("break") || y.equals("new")
				|| y.equals("continue")){
			return "KW\t"+ y;
		}else if(y.equals("!=")){
			return "NE\t" +y;
		}else if (y.equalsIgnoreCase("!")) {
			return "LN\t" + y;
		} else if (y.equalsIgnoreCase("|")) {
			return "LO\t" + y;
		} else if (y.equalsIgnoreCase("==")) {
			return "EQ\t" + y;
		} else if (y.equalsIgnoreCase("&")) {
			return "LA\t" + y;
		} else if (y.equalsIgnoreCase("<=")) {
			return "LE\t" + y;
		} else if (y.equalsIgnoreCase(">=")) {
			return "RE\t" + y;
		} else if (y.equalsIgnoreCase("{")) {
			return "LC\t" + y;
		} else if (y.equalsIgnoreCase("}")) {
			return "RC\t" + y;
		} else if (y.equalsIgnoreCase("(")) {
			return "LB\t" + y;
		} else if (y.equalsIgnoreCase(")")) {
			return "RB\t" + y;
		} else if (y.equalsIgnoreCase("=")) {
			return "AO\t" + y;
		} else if (y.equalsIgnoreCase("-")) {
			return "MO\t" + y;
		} else if (y.equalsIgnoreCase("+")) {
			return "PO\t" + y;
		} else if (y.equalsIgnoreCase("*")) {
			return "MB\t" + y;
		} else if (y.equalsIgnoreCase("/")) {
			return "DB\t" + y;
		} else if (y.equalsIgnoreCase(";")) {
			return "SM\t" + y;
		} else if (y.equalsIgnoreCase(".")) {
			return "DO\t" + y;
		} else if (y.equalsIgnoreCase("\'")
				|| y.equalsIgnoreCase(",")) {
			return "FA\t" + y;
		} else if (y.equalsIgnoreCase("[]")) {
			return "AA\t" + y;
		} else if (y.equalsIgnoreCase("]")) {
			return "RS\t" + y;
		} else if (y.equalsIgnoreCase("[")) {
			return "LS\t" + y;
		} else if (y.equalsIgnoreCase("++")) {
			return "PP\t" + y;
		} else if (y.equalsIgnoreCase("<")) {
			return  "LT\t" + y;
		} else if (y.equalsIgnoreCase(">")) {
			return "RT\t" + y;
		}else if(y.startsWith("\"")){
			return "ST\t" + y;
		}else if(y.startsWith("'")){
			return "CH\t" + y;
		}else if(y.matches(id_pattern)){
			return "ID\t" + y;
		}else if(y.matches(num_pattern)){
			return "NM\t" + y;
		}else if(y.matches(neg_num_pattern)){
			return "MO\t-\nNM\t" + y.substring(1);
		}else
			return "/";
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader bf = new BufferedReader(new FileReader(new File(
				"MCode.java")));
		BufferedWriter writer = new BufferedWriter(new FileWriter("1MCodeOutt.txt"));
		String outt = "\n";
		boolean dblQ = false, sngQ = false;
		String seperators = ";[].()=-+*/%{}:?!,";
		String currentString = "";
		boolean inString = false;
		boolean fSlash = false, sSlash = false, inNum = false, inMl = false;
		char lastC = ' ';
		do {
			char c = (char) bf.read();		
			String sp = "";
			if (!(outt.charAt(outt.length() - 1) + "").equals("\n")) {
				sp = "\n";
			}
			if(c == '/' && !inString && !inMl && !sngQ && !dblQ){
				char cc = (char) bf.read();
				if(cc == '*'){
					inMl = true;
					continue;
				}else{
					writer.write("DB\t/\n");
					c = cc;
				}
			}
			
			else if(c == '*' && !inString && inMl){
				char cc = (char) bf.read();
				if(cc == '/'){
					inMl = false;
					continue;
				}else{
					continue;
				}
			}
			if(inMl)
				continue;
			else if("0123456789".indexOf(c+"") >= 0 && !inString){
				inNum = true;
			}else if(inNum && c != '.'){
				inNum = false;
			}
			else if(c == '"' && lastC !='\\')
				inString = !inString;
			else if(c == '&' && !inString){
				writer.write("LA\t&\n");
				currentString = "";
				lastC = c;
				continue;
			}
			else if(c == '[' && !inString){
				char cc = (char) bf.read();
				if(cc == ']'){
					writer.write("AA\t[]\n");
					continue;
				}else{
					writer.write("LS\t[\n");
					c = cc;
				}
			}
			else if(c == '!' && !inString){
				char cc = (char) bf.read();
				if(cc == '='){
					writer.write("NE\t!=\n");
					continue;
				}else{

				}
			}
			else if(c == '+' && !inString){
				char cc = (char) bf.read();
				if(cc == '+'){
					writer.write("PP\t++\n");
					continue;
				}else{
					writer.write("PO\t+\n");
					c = cc;
				}
			}
			else if(c == '<' && !inString){
				char cc = (char) bf.read();
				if(cc == '='){
					writer.write("LE\t<=\n");
					continue;
				}else{
					writer.write("LT\t<\n");
					c = cc;
				}
			}
			else if(c == '>' && !inString){
				char cc = (char) bf.read();
				if(cc == '='){
					writer.write("GE\t>=\n");
					continue;
				}else{
					writer.write("GT\t>\n");
					c = cc;
				}
			}
			else if("\n\r".indexOf(c) >= 0){
				fSlash = false;
				sSlash = false;
			}
			if(fSlash && sSlash)
				continue;
			else if("/".equals(c+"") && !fSlash)
				fSlash = true;
			else if("/".equals(c+"") && fSlash)
				sSlash = true;
			else if ("\"".equals(c + "") && !sngQ) {
				dblQ = !dblQ;
				//currentString += c + "";
			}
			else if ("'".equals(c + "") && !dblQ) {
				sngQ = !sngQ;
				//currentString += c + "";
			}
			else if (" \t\n\r\b\f".indexOf(c) >= 0 && !inString) {
				//System.out.println(currentString);
				String word = checkWord(currentString);
				if(word.length() > 0){
					writer.write(word);
					writer.write("\n");
				}
				currentString = "";
			}else if(seperators.indexOf(c) >= 0 && !dblQ && !sngQ && !isNum(currentString)){
				String word = checkWord(currentString);
				if(word.length() > 0){
					writer.write(word);
					writer.write("\n");
				}
				word = checkWord(c+"");
				if(word.length() > 0){
					writer.write(word);
					writer.write("\n");
				}
				currentString = "";
				continue;
			}else{
				outt = outt + c;
			}
			currentString += c + "";
			lastC = c;
		} while (bf.ready());
		outt = outt.replace("\n\n", "\n");
		outt = outt.replace("}\n", "}");
		outt = outt.replace("};", "}\n;");
		//System.out.println(outt);
		writer.close();
	}
}
