import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Parser {
	public static void main(String [] args) throws IOException{
		BufferedReader bf = new BufferedReader(new FileReader(new File("text.txt")));
		String outt = "\n";
		boolean dblQ = false, sngQ = false;
		do{
			char c = (char) bf.read();
			String seperators = ";[]()=-+*/%{}:?!,";
			String sp = "";
			if(!(outt.charAt(outt.length()-1)+"").equals("\n")){
				sp = "\n";
			}
			if("\"".equals(c+"")){
				dblQ = !dblQ;
			}
			if("'".equals(c+"")){
				sngQ = !sngQ;
			}
			if(" \t".indexOf(c) >= 0){
				outt += sp;
			}else if(seperators.indexOf(c) >= 0 && !dblQ && !sngQ){
				outt = outt + sp + c + "\n";
			}else{
				outt = outt + c;
			}
		}while(bf.ready());
		outt = outt.replace("\n\n","\n");
		outt = outt.replace("}\n","}");
		outt = outt.replace("};","}\n;");
		System.out.println(outt);
	}
}
