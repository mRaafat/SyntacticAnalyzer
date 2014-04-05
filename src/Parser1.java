import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Parser1 {
	public static void main(String[] args) throws IOException {
		BufferedReader bf = new BufferedReader(new FileReader(new File(
				"Code.txt")));
		BufferedWriter writer = new BufferedWriter(new FileWriter("1SCodeOut.txt"));
		String outt = "\n";
		String seperator = ";[]()=-+*/%{}:?!,";
		boolean dblQ = false, sngQ = false;
		char temp;
		boolean foundSlash = false, foundStar = false;
		// Flag for single comment line and for block line
		boolean sComment = false, bComment = false;
		String currentString = "";
		do {
			char c = (char) bf.read();
			// char nextCharacter = (char) bf.read();
			// System.out.println(c);
			// System.out.println((char)bf.read());
			String sp = "";

			if (bComment && foundStar && c != '/') {
				System.out.println("Inside block comment");
			} else {
				if (bComment && foundStar && c == '/') {
					bComment = false;
					foundStar = false;
					System.out.println("Block comment endded");
				} else {
					if (c == '/') {
						foundSlash = true;
						if ((temp = (char) bf.read()) == '*') {
							bComment = true;
							foundStar = true;
							System.out.println("found block comment");

						}else{
							
						}

					}
					// System.out.print((char)bf.read());
				}
			}

			if ((sComment == true && c != '\n')) {
				System.out.println("Inside Single line Comment");

			} else {
				if ((sComment && c == '\n')) {
					sComment = false;
					System.out.println("Comment ended");
				} else {
					if (c == '/') {
						temp = (char) bf.read();
						if (temp == '/') {
							System.out.println("Found comment");
							sComment = true;
						}
					}
				}
			}

			if (c == '\"') {
				dblQ = !dblQ;
			}
			if (c == '\'') {
				sngQ = !sngQ;
			}
			if (c == '\n')
				outt += '\n';
			// if()

		} while (bf.ready());

		outt = outt.replace("\n\n", "\n");
		outt = outt.replace("}\n", "}");
		outt = outt.replace("};", "}\n;");

	}
}
