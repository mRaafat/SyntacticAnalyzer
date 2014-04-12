import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class Brensawy2 {
	static BufferedReader bf = null;
	static Stack<String> curleyStack = null;
	static ArrayList<String> file = null;
	static int headPtr;
	public static void main(String [] death) throws IOException{
		bf = new BufferedReader(new FileReader(new File("Code01out.java")));
		curleyStack = new Stack<String>();
		file = new ArrayList<String>();
		headPtr = 0;
		readFile();
		parseClass();
//		while(bf.ready()){
//			String token = bf.readLine();
//			String [] splitted = token.split("\t");
//			
//		}
	}
	public static void readFile(){
		try {
			while(bf.ready())
				file.add(bf.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static boolean parseClass() throws IOException{
		String [] classDef = {"CS", "ID", "LC"};
		for(int i=0; i < classDef.length; i++){
			String token = file.get(headPtr++);
			if(!token.equals(classDef[i]))
				return false;
		}
		curleyHandle(1);
		return parseClassBlock();
	}
	
	public static boolean parseClassBlock(){
		while(true){
			matchMethodHead();
			matchBlock();
		}
		
		if(curleyStack.empty())
			return true;
	}
	public static String getToken(int n) {
		return file.get(n).split("\t")[0];
	}
	public static boolean curleyHandle(int x){
		// 1 for { LC
		// 2 for } RC
		if(x == 1){
			curleyStack.push("LC");
		}else{
			if(curleyStack.peek() == "LC")
				curleyStack.pop();
			else if(curleyStack.empty())
				return false;
			else
				curleyStack.push("RC");
		}
		return true;
	}

}
