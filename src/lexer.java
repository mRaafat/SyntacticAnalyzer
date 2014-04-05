import java.util.*;
import java.io.*;
/* semantic value of token returned by scanner */
public class lexer
{
	public static void main (String[] args) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader("XLCode.java"));
		BufferedWriter writer = new BufferedWriter(new FileWriter("1XLCodeOut.txt"));
		Yylex yy = new Yylex (reader);
		while(true)
		{
			String x =yy.next_token();
			if(x==null)
				break;
			writer.write(x);
			writer.write('\n');
		}
		reader.close();
		writer.close();
	}
}


class Yylex {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

  public String sourceFilename;
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	Yylex (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Yylex (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Yylex () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int STS = 2;
	private final int COMMENTS = 1;
	private final int yy_state_dtrans[] = {
		0,
		53,
		55
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NO_ANCHOR,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NOT_ACCEPT,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NOT_ACCEPT,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NOT_ACCEPT,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NOT_ACCEPT,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NOT_ACCEPT,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NOT_ACCEPT,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NOT_ACCEPT,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NOT_ACCEPT,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NO_ANCHOR,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"2:8,32:2,3,2,32,4,2:18,32,46,30,2:3,48,31,41,42,34,45,36,51,35,1,29:10,2,43" +
",49,44,50,2:2,28:18,21,28:7,37,33,38,2,28,2,10,14,8,19,17,20,22,23,5,28,25," +
"9,27,6,18,12,28,15,11,7,13,16,24,28,26,28,39,47,40,2:2,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,131,
"0,1,2,1,3,4,1:3,5,1:6,6,7,8,1:2,9,10,1,11,12,13,1:7,11,1:4,14,15,16:2,17,18" +
",15,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42" +
",43,44,45,46,47,48,49,29,50,51,52,53,54,55,56,57,30,58,59,36,60,61,62,63,64" +
",65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,13" +
",89,90,91,92,93,94,95,96,97,98,40")[0];

	private int yy_nxt[][] = unpackFromString(99,52,
"1,2,3,4:2,39,43,97,109,117,119,121,122,119,123,124,125,119,126,46,127,128,1" +
"19:2,129,119:4,5,40,44,4,3,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,2" +
"3,-1:53,24,-1:32,25,-1:20,4:2,-1:27,4,-1:20,42:2,-1:2,42:24,5,42:22,-1:38,2" +
"8,-1:57,29,-1:52,30,-1:50,31,-1:51,32,-1:51,33,-1:8,34:2,-1,34:48,-1,25:33," +
"51,25:17,-1:5,119:24,130,-1:27,119,48,119:13,26,119:8,130,-1:23,45:29,27,45" +
":21,-1:29,41,-1:27,119:12,50,119:11,130,-1:23,47:2,-1:2,47:28,49,47:18,-1:5" +
",119:13,96,119:10,130,-1:53,35,-1:25,119:2,26,119:21,130,-1:52,47,35,-1,47," +
"-1:23,119:19,26,119:4,130,-1:23,36,25:32,51,25:17,-1:5,119:8,68,119:15,130," +
"-1:22,1,37:2,38,37:48,-1:5,119:5,111,119:18,130,-1:22,1,-1:56,119:6,68,119:" +
"17,130,-1:27,119,69,119:22,130,-1:27,119:13,70,119:10,130,-1:27,118,119:23," +
"130,-1:27,119:9,104,119:14,130,-1:27,71,119:12,120,119:10,130,-1:27,119:12," +
"72,119:11,130,-1:27,119:2,68,119:21,130,-1:27,119:4,77,119:19,130,-1:27,119" +
":10,26,119:13,130,-1:27,119:10,78,119:13,130,-1:27,67:24,130,-1:27,119:12,2" +
"6,119:11,130,-1:27,119:17,26,119:6,130,-1:27,119:10,48,119:13,130,-1:27,119" +
",98,119:9,105,119:12,130,-1:27,119:5,84,119:18,130,-1:27,119:8,85,119:15,13" +
"0,-1:27,119:14,26,119:9,130,-1:27,119:9,80,119:14,130,-1:27,119:5,48,119:18" +
",130,-1:27,86,119:23,130,-1:27,119:2,116,119:21,130,-1:27,119:4,68,119:19,1" +
"30,-1:27,119:6,26,119:17,130,-1:27,119:3,88,119:20,130,-1:27,119:12,90,119:" +
"11,130,-1:27,119:20,26,119:3,130,-1:27,119:10,92,119:13,130,-1:27,119:3,26," +
"119:20,130,-1:27,119:18,26,119:5,130,-1:27,119:3,94,119:20,130,-1:27,119:5," +
"92,119:18,130,-1:27,119,26,119:22,130,-1:27,119:22,26,119,130,-1:27,119:2,9" +
"5,119:21,130,-1:27,119:12,74,119:11,130,-1:27,119:8,75,119:15,130,-1:27,119" +
":10,52,119:13,130,-1:27,119:2,108,119:21,130,-1:27,119:5,65,119:18,130,-1:2" +
"7,119:6,79,119:17,130,-1:27,119:13,115,119:10,130,-1:27,74,119:23,130,-1:27" +
",119:2,73,119:21,130,-1:27,119:4,106,119:19,130,-1:27,119:5,89,119:18,130,-" +
"1:27,87,119:23,130,-1:27,119:12,91,119:11,130,-1:27,119:4,92,119:19,130,-1:" +
"27,119:4,54,56,119:12,99,119:5,130,-1:27,119:5,114,119:18,130,-1:27,119:6,8" +
"1,119:17,130,-1:27,119:13,76,119:10,130,-1:27,80,119:23,130,-1:27,119:2,106" +
",119:21,130,-1:27,119:4,107,119:19,130,-1:27,119:12,93,119:11,130,-1:27,119" +
":13,57,119:10,130,-1:27,119:2,82,119:21,130,-1:27,119:2,83,119:21,130,-1:27" +
",119:2,110,119:15,58,59,119:4,130,-1:27,119:8,60,119,61,119:13,130,-1:27,11" +
"9:10,62,119:2,101,119:7,63,119:2,130,-1:27,119:12,103,119:11,130,-1:27,119:" +
"13,102,119:10,130,-1:27,119:8,48,119:15,130,-1:27,119:4,112,64,119:7,65,119" +
":10,130,-1:27,119:2,66,119:18,100,119:2,130,-1:27,119:18,113,119:5,130,-1:2" +
"2");

	public String next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

  return null;
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 0:
						{
  return "ID\t"+yytext();
}
					case -2:
						break;
					case 1:
						
					case -3:
						break;
					case 2:
						{ 
  return "DB\t/";
}
					case -4:
						break;
					case 3:
						{ 
  return "Undefined\t"+yytext(); 
}
					case -5:
						break;
					case 4:
						{ }
					case -6:
						break;
					case 5:
						{
  return "NM\t"+yytext();
}
					case -7:
						break;
					case 6:
						{ 
  return "MB\t*";
}
					case -8:
						break;
					case 7:
						{ 
  return "DO\t."; 
}
					case -9:
						break;
					case 8:
						{ 
  return "FA\t,"; 
}
					case -10:
						break;
					case 9:
						{ 
  return "LS\t[";
}
					case -11:
						break;
					case 10:
						{ 
  return "RS\t]";
}
					case -12:
						break;
					case 11:
						{ 
  return "LC\t{"; 
}
					case -13:
						break;
					case 12:
						{ 
  return "RC\t}"; 
}
					case -14:
						break;
					case 13:
						{ 
  return "LB\t("; 
}
					case -15:
						break;
					case 14:
						{ 
  return "RB\t)"; 
}
					case -16:
						break;
					case 15:
						{ 
  return "SM\t;"; 
}
					case -17:
						break;
					case 16:
						{ 
  return "AO\t="; 
}
					case -18:
						break;
					case 17:
						{ 
  return "PO\t+";
}
					case -19:
						break;
					case 18:
						{ 
  return "LN\t!";
}
					case -20:
						break;
					case 19:
						{ 
  return "LO\t|";
}
					case -21:
						break;
					case 20:
						{ 
  return "LA\t&";
}
					case -22:
						break;
					case 21:
						{ 
  return "LT\t<";
}
					case -23:
						break;
					case 22:
						{ 
  return "GT\t>";
}
					case -24:
						break;
					case 23:
						{ 
  return "MO\t-";
}
					case -25:
						break;
					case 24:
						{
  yybegin(COMMENTS);
}
					case -26:
						break;
					case 26:
						{
  return "KW\t"+yytext();
}
					case -27:
						break;
					case 27:
						{
  return "ST\t"+yytext();
}
					case -28:
						break;
					case 28:
						{ 
  return "AA\t[]"; 
}
					case -29:
						break;
					case 29:
						{ 
  return "EQ\t==";
}
					case -30:
						break;
					case 30:
						{ 
  return "PP\t++";
}
					case -31:
						break;
					case 31:
						{ 
  return "NE\t!=";
}
					case -32:
						break;
					case 32:
						{ 
  return "LE\t<=";
}
					case -33:
						break;
					case 33:
						{ 
  return "GE\t>=";
}
					case -34:
						break;
					case 34:
						{
}
					case -35:
						break;
					case 35:
						{
  return "CH\t"+yytext();
}
					case -36:
						break;
					case 36:
						{
}
					case -37:
						break;
					case 37:
						{
}
					case -38:
						break;
					case 38:
						{
  yybegin(YYINITIAL);
}
					case -39:
						break;
					case 39:
						{
  return "ID\t"+yytext();
}
					case -40:
						break;
					case 40:
						{ 
  return "Undefined\t"+yytext(); 
}
					case -41:
						break;
					case 41:
						{
  return "NM\t"+yytext();
}
					case -42:
						break;
					case 43:
						{
  return "ID\t"+yytext();
}
					case -43:
						break;
					case 44:
						{ 
  return "Undefined\t"+yytext(); 
}
					case -44:
						break;
					case 46:
						{
  return "ID\t"+yytext();
}
					case -45:
						break;
					case 48:
						{
  return "ID\t"+yytext();
}
					case -46:
						break;
					case 50:
						{
  return "ID\t"+yytext();
}
					case -47:
						break;
					case 52:
						{
  return "ID\t"+yytext();
}
					case -48:
						break;
					case 54:
						{
  return "ID\t"+yytext();
}
					case -49:
						break;
					case 56:
						{
  return "ID\t"+yytext();
}
					case -50:
						break;
					case 57:
						{
  return "ID\t"+yytext();
}
					case -51:
						break;
					case 58:
						{
  return "ID\t"+yytext();
}
					case -52:
						break;
					case 59:
						{
  return "ID\t"+yytext();
}
					case -53:
						break;
					case 60:
						{
  return "ID\t"+yytext();
}
					case -54:
						break;
					case 61:
						{
  return "ID\t"+yytext();
}
					case -55:
						break;
					case 62:
						{
  return "ID\t"+yytext();
}
					case -56:
						break;
					case 63:
						{
  return "ID\t"+yytext();
}
					case -57:
						break;
					case 64:
						{
  return "ID\t"+yytext();
}
					case -58:
						break;
					case 65:
						{
  return "ID\t"+yytext();
}
					case -59:
						break;
					case 66:
						{
  return "ID\t"+yytext();
}
					case -60:
						break;
					case 67:
						{
  return "ID\t"+yytext();
}
					case -61:
						break;
					case 68:
						{
  return "ID\t"+yytext();
}
					case -62:
						break;
					case 69:
						{
  return "ID\t"+yytext();
}
					case -63:
						break;
					case 70:
						{
  return "ID\t"+yytext();
}
					case -64:
						break;
					case 71:
						{
  return "ID\t"+yytext();
}
					case -65:
						break;
					case 72:
						{
  return "ID\t"+yytext();
}
					case -66:
						break;
					case 73:
						{
  return "ID\t"+yytext();
}
					case -67:
						break;
					case 74:
						{
  return "ID\t"+yytext();
}
					case -68:
						break;
					case 75:
						{
  return "ID\t"+yytext();
}
					case -69:
						break;
					case 76:
						{
  return "ID\t"+yytext();
}
					case -70:
						break;
					case 77:
						{
  return "ID\t"+yytext();
}
					case -71:
						break;
					case 78:
						{
  return "ID\t"+yytext();
}
					case -72:
						break;
					case 79:
						{
  return "ID\t"+yytext();
}
					case -73:
						break;
					case 80:
						{
  return "ID\t"+yytext();
}
					case -74:
						break;
					case 81:
						{
  return "ID\t"+yytext();
}
					case -75:
						break;
					case 82:
						{
  return "ID\t"+yytext();
}
					case -76:
						break;
					case 83:
						{
  return "ID\t"+yytext();
}
					case -77:
						break;
					case 84:
						{
  return "ID\t"+yytext();
}
					case -78:
						break;
					case 85:
						{
  return "ID\t"+yytext();
}
					case -79:
						break;
					case 86:
						{
  return "ID\t"+yytext();
}
					case -80:
						break;
					case 87:
						{
  return "ID\t"+yytext();
}
					case -81:
						break;
					case 88:
						{
  return "ID\t"+yytext();
}
					case -82:
						break;
					case 89:
						{
  return "ID\t"+yytext();
}
					case -83:
						break;
					case 90:
						{
  return "ID\t"+yytext();
}
					case -84:
						break;
					case 91:
						{
  return "ID\t"+yytext();
}
					case -85:
						break;
					case 92:
						{
  return "ID\t"+yytext();
}
					case -86:
						break;
					case 93:
						{
  return "ID\t"+yytext();
}
					case -87:
						break;
					case 94:
						{
  return "ID\t"+yytext();
}
					case -88:
						break;
					case 95:
						{
  return "ID\t"+yytext();
}
					case -89:
						break;
					case 96:
						{
  return "KW\t"+yytext();
}
					case -90:
						break;
					case 97:
						{
  return "ID\t"+yytext();
}
					case -91:
						break;
					case 98:
						{
  return "ID\t"+yytext();
}
					case -92:
						break;
					case 99:
						{
  return "ID\t"+yytext();
}
					case -93:
						break;
					case 100:
						{
  return "ID\t"+yytext();
}
					case -94:
						break;
					case 101:
						{
  return "ID\t"+yytext();
}
					case -95:
						break;
					case 102:
						{
  return "ID\t"+yytext();
}
					case -96:
						break;
					case 103:
						{
  return "ID\t"+yytext();
}
					case -97:
						break;
					case 104:
						{
  return "ID\t"+yytext();
}
					case -98:
						break;
					case 105:
						{
  return "ID\t"+yytext();
}
					case -99:
						break;
					case 106:
						{
  return "ID\t"+yytext();
}
					case -100:
						break;
					case 107:
						{
  return "ID\t"+yytext();
}
					case -101:
						break;
					case 108:
						{
  return "KW\t"+yytext();
}
					case -102:
						break;
					case 109:
						{
  return "ID\t"+yytext();
}
					case -103:
						break;
					case 110:
						{
  return "ID\t"+yytext();
}
					case -104:
						break;
					case 111:
						{
  return "ID\t"+yytext();
}
					case -105:
						break;
					case 112:
						{
  return "ID\t"+yytext();
}
					case -106:
						break;
					case 113:
						{
  return "ID\t"+yytext();
}
					case -107:
						break;
					case 114:
						{
  return "ID\t"+yytext();
}
					case -108:
						break;
					case 115:
						{
  return "ID\t"+yytext();
}
					case -109:
						break;
					case 116:
						{
  return "ID\t"+yytext();
}
					case -110:
						break;
					case 117:
						{
  return "ID\t"+yytext();
}
					case -111:
						break;
					case 118:
						{
  return "ID\t"+yytext();
}
					case -112:
						break;
					case 119:
						{
  return "ID\t"+yytext();
}
					case -113:
						break;
					case 120:
						{
  return "ID\t"+yytext();
}
					case -114:
						break;
					case 121:
						{
  return "ID\t"+yytext();
}
					case -115:
						break;
					case 122:
						{
  return "ID\t"+yytext();
}
					case -116:
						break;
					case 123:
						{
  return "ID\t"+yytext();
}
					case -117:
						break;
					case 124:
						{
  return "ID\t"+yytext();
}
					case -118:
						break;
					case 125:
						{
  return "ID\t"+yytext();
}
					case -119:
						break;
					case 126:
						{
  return "ID\t"+yytext();
}
					case -120:
						break;
					case 127:
						{
  return "ID\t"+yytext();
}
					case -121:
						break;
					case 128:
						{
  return "ID\t"+yytext();
}
					case -122:
						break;
					case 129:
						{
  return "ID\t"+yytext();
}
					case -123:
						break;
					case 130:
						{
  return "ID\t"+yytext();
}
					case -124:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
