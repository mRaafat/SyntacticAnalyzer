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
	public static void main(String [] death) throws IOException{
		bf = new BufferedReader(new FileReader(new File("Code01out.java")));
		curleyStack = new Stack<String>();
		currentBlock = new ArrayList<String>();
		parseClass();
//		while(bf.ready()){
//			String token = bf.readLine();
//			String [] splitted = token.split("\t");
//			
//		}
	}
	public static boolean parseClass() throws IOException{
		String [] classDef = {"CS", "ID", "LC"};
		for(int i=0; i < classDef.length; i++){
			String token = getToken();
			if(!token.equals(classDef[i]))
				return false;
		}
		curleyHandle(1);
		if(!parseClassBlock()){
			return false;
		}
		if(curleyStack.empty())
			return true;
		return false;
	}
	
	public static boolean parseClassBlock(){
		while(true){
			String token = getToken();
			if(token.equals("LC"))
				curleyHandle(1);
			else if(token.equals("RC"))
				curleyHandle(2);
			if(curleyStack.empty())
				if(!getToken().equals("")){
					return false;
				}else{				
					return handleClassBlock();
					break;
				}
			currentBlock.add(token);
		}
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
	public static String getToken(){
		try {
			return bf.readLine().split("\t")[0];
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
