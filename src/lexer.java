import java.util.*;
import java.io.*;
/* semantic value of token returned by scanner */
public class lexer
{
	public static void main (String[] args) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader("MCode.java"));
		BufferedWriter writer = new BufferedWriter(new FileWriter("1MCodeOut.txt"));
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
		84,
		86
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
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NOT_ACCEPT,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NOT_ACCEPT,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NOT_ACCEPT,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NOT_ACCEPT,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NOT_ACCEPT,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NOT_ACCEPT,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NOT_ACCEPT,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NOT_ACCEPT,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NOT_ACCEPT,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NOT_ACCEPT,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NOT_ACCEPT,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NOT_ACCEPT,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NOT_ACCEPT,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NOT_ACCEPT,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NOT_ACCEPT,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NOT_ACCEPT,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NOT_ACCEPT,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NOT_ACCEPT,
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
		/* 130 */ YY_NO_ANCHOR,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NO_ANCHOR,
		/* 134 */ YY_NO_ANCHOR,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NO_ANCHOR,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NO_ANCHOR,
		/* 140 */ YY_NO_ANCHOR,
		/* 141 */ YY_NO_ANCHOR,
		/* 142 */ YY_NO_ANCHOR,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NO_ANCHOR,
		/* 145 */ YY_NO_ANCHOR,
		/* 146 */ YY_NO_ANCHOR,
		/* 147 */ YY_NO_ANCHOR,
		/* 148 */ YY_NO_ANCHOR,
		/* 149 */ YY_NO_ANCHOR,
		/* 150 */ YY_NO_ANCHOR,
		/* 151 */ YY_NO_ANCHOR,
		/* 152 */ YY_NO_ANCHOR,
		/* 153 */ YY_NO_ANCHOR,
		/* 154 */ YY_NO_ANCHOR,
		/* 155 */ YY_NO_ANCHOR,
		/* 156 */ YY_NO_ANCHOR,
		/* 157 */ YY_NO_ANCHOR,
		/* 158 */ YY_NO_ANCHOR,
		/* 159 */ YY_NO_ANCHOR,
		/* 160 */ YY_NO_ANCHOR,
		/* 161 */ YY_NO_ANCHOR,
		/* 162 */ YY_NO_ANCHOR,
		/* 163 */ YY_NO_ANCHOR,
		/* 164 */ YY_NO_ANCHOR,
		/* 165 */ YY_NO_ANCHOR,
		/* 166 */ YY_NO_ANCHOR,
		/* 167 */ YY_NO_ANCHOR,
		/* 168 */ YY_NO_ANCHOR,
		/* 169 */ YY_NO_ANCHOR,
		/* 170 */ YY_NO_ANCHOR,
		/* 171 */ YY_NO_ANCHOR,
		/* 172 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"2:8,37:2,3,2,37,4,2:18,37,53,38,2:3,55,39,48,49,41,52,43,26,42,1,22:10,2,50" +
",56,51,57,2:2,28,21:2,27,32,21:10,31,21:2,33,29,21:4,30,21,44,40,45,2,21,2," +
"9,19,8,25,6,17,34,14,12,21,20,11,36,5,18,23,21,15,10,13,16,24,7,21,35,21,46" +
",54,47,2:2,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,173,
"0,1,2,1,3,4,5,1:3,6,1:6,7,8,9,1:2,10,11,12,13,1:8,12,14:2,1:2,14:6,1:3,15,1" +
"6,17:2,18,19,16,20,21,22,23,24,25,26,27,28,29,30,31,32,33,14,34,35,36,37,38" +
",39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63" +
",64,65,32,66,67,68,69,70,71,30,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86" +
",87,88,89,90,91,92,93,94,95,96,97,14,98,99,100,101,102,103,104,105,106,107," +
"108,109,110,111,112,113,75,114,115,116,117,118,119,120,121,122,123,124,125," +
"126,127,128,129,130,131,132")[0];

	private int yy_nxt[][] = unpackFromString(133,58,
"1,2,3,4:2,48,136,150,156,136,158,160,161,162,136,163,136,164,136,165,136:2," +
"5,166,136,167,6,168,136:5,169,136:3,4,49,53,3,7,8,9,10,11,12,13,14,15,16,17" +
",18,19,20,21,22,23,-1:59,24,-1:39,25,-1:19,4:2,-1:32,4,-1:21,51:2,-1:2,51:1" +
"7,5,51:35,-1:26,26,-1:76,28,-1:63,29,-1:58,30,-1:56,31,-1:57,32,-1:57,33,-1" +
":7,34:2,-1,34:54,-1,25:40,60,25:16,-1:5,136:17,55,136:3,-1,136:10,-1:26,136" +
",52,136:15,55,136:3,-1,136:10,-1:22,54:37,27,54:19,-1:22,50,-1:40,136:2,35," +
"136:14,55,136:3,-1,136:10,-1:22,56:2,-1:2,56:35,58,56:17,-1:5,55:21,-1,55:1" +
"0,-1:60,37,-1:23,136:7,85,136:9,55,136:3,-1,136:10,-1:59,56,37,56,-1:22,136" +
":5,87,136:11,55,136:3,-1,136:10,-1:22,38,25:39,60,25:16,-1:5,136:4,88,136:1" +
"2,55,136:3,-1,136:10,-1:39,64,-1:44,136:13,144,136:3,55,136:3,-1,136:10,-1:" +
"37,66,-1:46,90,136:16,55,136:3,-1,136:10,-1:34,68,-1:49,136:8,69,136:8,55,1" +
"36:3,-1,136:10,-1:22,70:2,-1:2,70:53,-1:23,72,-1:39,136:11,91,136:5,55,136:" +
"3,-1,136:10,-1:36,74,-1:47,136:10,36,136:6,55,136:3,-1,136:10,-1:33,76,-1:5" +
"0,136,146,136:15,55,136:3,-1,136:10,-1:26,78,-1:57,136:7,94,136:5,95,136:3," +
"55,136:3,-1,136:10,-1:34,80,-1:49,136:14,154,136:2,55,136:3,-1,136:10,-1:32" +
",82,-1:51,136:17,55,136:3,-1,136:2,97,136:7,-1:26,45,-1:57,136:10,98,136:6," +
"55,136:3,-1,136:10,-1:21,1,46:2,47,46:54,-1:5,136:6,99,136:10,55,136:3,-1,1" +
"36:10,-1:21,1,-1:62,136,39,136:15,55,136:3,-1,136:10,-1:26,136:5,100,136:11" +
",55,136:3,-1,136:10,-1:26,136:10,69,136:6,55,136:3,-1,136:10,-1:26,136:17,5" +
"5,136:3,-1,136:7,69,136:2,-1:26,136,69,136:15,55,136:3,-1,136:10,-1:26,136:" +
"11,103,136:5,55,136:3,-1,136:10,-1:26,136:4,102,136:12,55,136:3,-1,136:10,-" +
"1:26,136:17,55,136,106,136,-1,136:10,-1:26,136:8,148,136:8,55,136:3,-1,136:" +
"10,-1:26,136:14,107,136:2,55,136:3,-1,136:10,-1:26,136:17,55,136:3,-1,136,1" +
"08,136:8,-1:26,136:7,109,136:9,55,136:3,-1,136:10,-1:26,136,40,136:15,55,13" +
"6:3,-1,136:10,-1:26,136:5,41,136:11,55,136:3,-1,136:10,-1:26,136:3,110,136:" +
"13,55,136:3,-1,136:10,-1:26,136:10,112,136:6,55,136:3,-1,136:10,-1:26,136:1" +
"5,42,136,55,136:3,-1,136:10,-1:26,136,149,136:15,55,136:3,-1,136:10,-1:26,1" +
"36:4,113,136:12,55,136:3,-1,136:10,-1:26,136:6,91,136:10,55,136:3,-1,136:10" +
",-1:26,136:17,55,136:3,-1,136:2,115,136:7,-1:26,136:9,43,136:7,55,136:3,-1," +
"136:10,-1:26,136:3,69,136:13,55,136:3,-1,136:10,-1:26,44,136:16,55,136:3,-1" +
",136:10,-1:26,136:8,91,136:8,55,136:3,-1,136:10,-1:26,136:3,118,136:13,55,1" +
"36:3,-1,136:10,-1:26,136:17,55,136:3,-1,136:3,119,136:6,-1:26,136:17,55,136" +
":3,-1,136:9,120,-1:26,69,136:16,55,136:3,-1,136:10,-1:26,136:8,121,136:8,55" +
",136:3,-1,136:10,-1:26,136:17,55,136:3,-1,136:4,122,136:5,-1:22,62:2,-1:2,1" +
"23:17,172,123:3,62,123:10,62:21,-1:5,136,124,136:15,55,136:3,-1,136:10,-1:2" +
"6,136:17,55,136:3,-1,136:5,125,136:4,-1:26,136:13,126,136:3,55,136:3,-1,136" +
":10,-1:26,136:17,55,136:2,69,-1,136:10,-1:26,136:17,55,136:3,-1,136:6,69,13" +
"6:3,-1:26,136:11,127,136:5,55,136:3,-1,136:10,-1:26,136:8,128,136:8,55,136:" +
"3,-1,136:10,-1:22,70:2,-1:2,130:17,55,130:3,70,130:10,70:21,-1,70:2,-1:2,55" +
":21,70,55:10,70:21,-1:5,136:17,55,131,136:2,-1,136:10,-1:26,136:10,132,136:" +
"6,55,136:3,-1,136:10,-1:26,136:7,133,136:9,55,136:3,-1,136:10,-1:26,134,136" +
":16,55,136:3,-1,136:10,-1:26,136:8,135,136:8,55,136:3,-1,136:10,-1:26,136:6" +
",117,136:10,55,136:3,-1,136:10,-1:26,55:8,129,55:12,-1,55:10,-1:26,136:7,15" +
"7,136:9,55,136:3,-1,136:10,-1:26,136:5,171,136:11,55,136:3,-1,136:10,-1:26," +
"136:4,89,136:12,55,136:3,-1,136:10,-1:26,136:13,93,136:3,55,136:3,-1,136:10" +
",-1:26,136:8,92,136:8,55,136:3,-1,136:10,-1:26,136:11,96,136:5,55,136:3,-1," +
"136:10,-1:26,136:10,102,136:6,55,136:3,-1,136:10,-1:26,136:6,105,136:10,55," +
"136:3,-1,136:10,-1:26,136:4,104,136:12,55,136:3,-1,136:10,-1:26,136:7,111,1" +
"36:9,55,136:3,-1,136:10,-1:26,136,114,136:15,55,136:3,-1,136:10,-1:26,136:4" +
",117,136:12,55,136:3,-1,136:10,-1:26,136:9,57,136:7,55,136:3,-1,136:10,-1:2" +
"6,136:4,159,136:12,55,136:3,-1,136:10,-1:26,136:13,145,136:3,55,136:3,-1,13" +
"6:10,-1:26,136:6,147,136:10,55,136:3,-1,136:10,-1:26,136,116,136:15,55,136:" +
"3,-1,136:10,-1:26,136:4,59,136,61,136:2,140,136:7,55,136:3,-1,136:10,-1:26," +
"136:8,101,136:8,55,136:3,-1,136:10,-1:26,136:2,138,136:5,151,63,136:7,55,13" +
"6:3,-1,136:10,-1:26,136:8,147,136:8,55,136:3,-1,136:10,-1:26,136:13,65,136:" +
"3,55,136:3,-1,136:10,-1:26,67,136:11,69,136:4,55,136:3,-1,136:10,-1:26,136:" +
"10,71,136:6,55,136:3,-1,136:10,-1:26,136,142,136:15,55,136:3,-1,136:10,-1:2" +
"6,136:6,141,136:6,73,136:3,55,136:3,-1,136:10,-1:26,136:10,75,136:2,152,136" +
":3,55,136:3,-1,136:8,153,136,-1:26,136:10,77,79,136:5,55,136:3,-1,136:10,-1" +
":26,136:13,143,136:3,55,136:3,-1,136:10,-1:26,136:17,55,136:3,-1,136,81,136" +
":8,-1:26,136:8,83,136:8,55,136:3,-1,136:8,139,136,-1:26,55:11,137,55:9,-1,5" +
"5:10,-1:26,136:8,155,136:8,55,136:3,-1,136:10,-1:26,55:13,170,55:7,-1,55:10" +
",-1:21");

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
  return "CND\t"+yytext();
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
  return "OPP\t"+yytext();
}
					case -8:
						break;
					case 7:
						{ 
  return "MB\t*";
}
					case -9:
						break;
					case 8:
						{ 
  return "DO\t."; 
}
					case -10:
						break;
					case 9:
						{ 
  return "FA\t,"; 
}
					case -11:
						break;
					case 10:
						{ 
  return "LS\t[";
}
					case -12:
						break;
					case 11:
						{ 
  return "RS\t]";
}
					case -13:
						break;
					case 12:
						{ 
  return "LC\t{"; 
}
					case -14:
						break;
					case 13:
						{ 
  return "RC\t}"; 
}
					case -15:
						break;
					case 14:
						{ 
  return "LB\t("; 
}
					case -16:
						break;
					case 15:
						{ 
  return "RB\t)"; 
}
					case -17:
						break;
					case 16:
						{ 
  return "SM\t;"; 
}
					case -18:
						break;
					case 17:
						{ 
  return "AO\t="; 
}
					case -19:
						break;
					case 18:
						{ 
  return "PO\t+";
}
					case -20:
						break;
					case 19:
						{ 
  return "LN\t!";
}
					case -21:
						break;
					case 20:
						{ 
  return "LO\t|";
}
					case -22:
						break;
					case 21:
						{ 
  return "LA\t&";
}
					case -23:
						break;
					case 22:
						{ 
  return "LT\t<";
}
					case -24:
						break;
					case 23:
						{ 
  return "GT\t>";
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
  return "MM\t--";
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
  return "NW\t"+yytext();
}
					case -36:
						break;
					case 36:
						{
  return "FR\t"+yytext();
}
					case -37:
						break;
					case 37:
						{
  return "CH\t"+yytext();
}
					case -38:
						break;
					case 38:
						{
}
					case -39:
						break;
					case 39:
						{
  return "CS\t"+yytext();
}
					case -40:
						break;
					case 40:
						{
  return "WL\t"+yytext();
}
					case -41:
						break;
					case 41:
						{
  return "CL\t"+yytext();
}
					case -42:
						break;
					case 42:
						{
  return "BR\t"+yytext();
}
					case -43:
						break;
					case 43:
						{
  return "SW\t"+yytext();
}
					case -44:
						break;
					case 44:
						{
  return "RE\t"+yytext();
}
					case -45:
						break;
					case 45:
						{
  return "PN\t"+yytext();
}
					case -46:
						break;
					case 46:
						{
}
					case -47:
						break;
					case 47:
						{
  yybegin(YYINITIAL);
}
					case -48:
						break;
					case 48:
						{
  return "CND\t"+yytext();
}
					case -49:
						break;
					case 49:
						{ 
  return "Undefined\t"+yytext(); 
}
					case -50:
						break;
					case 50:
						{
  return "NM\t"+yytext();
}
					case -51:
						break;
					case 52:
						{
  return "CND\t"+yytext();
}
					case -52:
						break;
					case 53:
						{ 
  return "Undefined\t"+yytext(); 
}
					case -53:
						break;
					case 55:
						{
  return "CND\t"+yytext();
}
					case -54:
						break;
					case 57:
						{
  return "CND\t"+yytext();
}
					case -55:
						break;
					case 59:
						{
  return "CND\t"+yytext();
}
					case -56:
						break;
					case 61:
						{
  return "CND\t"+yytext();
}
					case -57:
						break;
					case 63:
						{
  return "CND\t"+yytext();
}
					case -58:
						break;
					case 65:
						{
  return "CND\t"+yytext();
}
					case -59:
						break;
					case 67:
						{
  return "CND\t"+yytext();
}
					case -60:
						break;
					case 69:
						{
  return "CND\t"+yytext();
}
					case -61:
						break;
					case 71:
						{
  return "CND\t"+yytext();
}
					case -62:
						break;
					case 73:
						{
  return "CND\t"+yytext();
}
					case -63:
						break;
					case 75:
						{
  return "CND\t"+yytext();
}
					case -64:
						break;
					case 77:
						{
  return "CND\t"+yytext();
}
					case -65:
						break;
					case 79:
						{
  return "CND\t"+yytext();
}
					case -66:
						break;
					case 81:
						{
  return "CND\t"+yytext();
}
					case -67:
						break;
					case 83:
						{
  return "CND\t"+yytext();
}
					case -68:
						break;
					case 85:
						{
  return "CND\t"+yytext();
}
					case -69:
						break;
					case 87:
						{
  return "CND\t"+yytext();
}
					case -70:
						break;
					case 88:
						{
  return "CND\t"+yytext();
}
					case -71:
						break;
					case 89:
						{
  return "CND\t"+yytext();
}
					case -72:
						break;
					case 90:
						{
  return "CND\t"+yytext();
}
					case -73:
						break;
					case 91:
						{
  return "CND\t"+yytext();
}
					case -74:
						break;
					case 92:
						{
  return "CND\t"+yytext();
}
					case -75:
						break;
					case 93:
						{
  return "CND\t"+yytext();
}
					case -76:
						break;
					case 94:
						{
  return "CND\t"+yytext();
}
					case -77:
						break;
					case 95:
						{
  return "CND\t"+yytext();
}
					case -78:
						break;
					case 96:
						{
  return "CND\t"+yytext();
}
					case -79:
						break;
					case 97:
						{
  return "CND\t"+yytext();
}
					case -80:
						break;
					case 98:
						{
  return "CND\t"+yytext();
}
					case -81:
						break;
					case 99:
						{
  return "CND\t"+yytext();
}
					case -82:
						break;
					case 100:
						{
  return "CND\t"+yytext();
}
					case -83:
						break;
					case 101:
						{
  return "CND\t"+yytext();
}
					case -84:
						break;
					case 102:
						{
  return "CND\t"+yytext();
}
					case -85:
						break;
					case 103:
						{
  return "CND\t"+yytext();
}
					case -86:
						break;
					case 104:
						{
  return "CND\t"+yytext();
}
					case -87:
						break;
					case 105:
						{
  return "CND\t"+yytext();
}
					case -88:
						break;
					case 106:
						{
  return "CND\t"+yytext();
}
					case -89:
						break;
					case 107:
						{
  return "CND\t"+yytext();
}
					case -90:
						break;
					case 108:
						{
  return "CND\t"+yytext();
}
					case -91:
						break;
					case 109:
						{
  return "CND\t"+yytext();
}
					case -92:
						break;
					case 110:
						{
  return "CND\t"+yytext();
}
					case -93:
						break;
					case 111:
						{
  return "CND\t"+yytext();
}
					case -94:
						break;
					case 112:
						{
  return "CND\t"+yytext();
}
					case -95:
						break;
					case 113:
						{
  return "CND\t"+yytext();
}
					case -96:
						break;
					case 114:
						{
  return "CND\t"+yytext();
}
					case -97:
						break;
					case 115:
						{
  return "CND\t"+yytext();
}
					case -98:
						break;
					case 116:
						{
  return "CND\t"+yytext();
}
					case -99:
						break;
					case 117:
						{
  return "CND\t"+yytext();
}
					case -100:
						break;
					case 118:
						{
  return "CND\t"+yytext();
}
					case -101:
						break;
					case 119:
						{
  return "CND\t"+yytext();
}
					case -102:
						break;
					case 120:
						{
  return "CND\t"+yytext();
}
					case -103:
						break;
					case 121:
						{
  return "CND\t"+yytext();
}
					case -104:
						break;
					case 122:
						{
  return "CND\t"+yytext();
}
					case -105:
						break;
					case 123:
						{
  return "CND\t"+yytext();
}
					case -106:
						break;
					case 124:
						{
  return "CND\t"+yytext();
}
					case -107:
						break;
					case 125:
						{
  return "CND\t"+yytext();
}
					case -108:
						break;
					case 126:
						{
  return "CND\t"+yytext();
}
					case -109:
						break;
					case 127:
						{
  return "CND\t"+yytext();
}
					case -110:
						break;
					case 128:
						{
  return "CND\t"+yytext();
}
					case -111:
						break;
					case 129:
						{
  return "CND\t"+yytext();
}
					case -112:
						break;
					case 130:
						{
  return "CND\t"+yytext();
}
					case -113:
						break;
					case 131:
						{
  return "CND\t"+yytext();
}
					case -114:
						break;
					case 132:
						{
  return "CND\t"+yytext();
}
					case -115:
						break;
					case 133:
						{
  return "CND\t"+yytext();
}
					case -116:
						break;
					case 134:
						{
  return "CND\t"+yytext();
}
					case -117:
						break;
					case 135:
						{
  return "CND\t"+yytext();
}
					case -118:
						break;
					case 136:
						{
  return "CND\t"+yytext();
}
					case -119:
						break;
					case 137:
						{
  return "CND\t"+yytext();
}
					case -120:
						break;
					case 138:
						{
  return "CND\t"+yytext();
}
					case -121:
						break;
					case 139:
						{
  return "CND\t"+yytext();
}
					case -122:
						break;
					case 140:
						{
  return "CND\t"+yytext();
}
					case -123:
						break;
					case 141:
						{
  return "CND\t"+yytext();
}
					case -124:
						break;
					case 142:
						{
  return "CND\t"+yytext();
}
					case -125:
						break;
					case 143:
						{
  return "CND\t"+yytext();
}
					case -126:
						break;
					case 144:
						{
  return "CND\t"+yytext();
}
					case -127:
						break;
					case 145:
						{
  return "CND\t"+yytext();
}
					case -128:
						break;
					case 146:
						{
  return "CND\t"+yytext();
}
					case -129:
						break;
					case 147:
						{
  return "CND\t"+yytext();
}
					case -130:
						break;
					case 148:
						{
  return "CND\t"+yytext();
}
					case -131:
						break;
					case 149:
						{
  return "CND\t"+yytext();
}
					case -132:
						break;
					case 150:
						{
  return "CND\t"+yytext();
}
					case -133:
						break;
					case 151:
						{
  return "CND\t"+yytext();
}
					case -134:
						break;
					case 152:
						{
  return "CND\t"+yytext();
}
					case -135:
						break;
					case 153:
						{
  return "CND\t"+yytext();
}
					case -136:
						break;
					case 154:
						{
  return "CND\t"+yytext();
}
					case -137:
						break;
					case 155:
						{
  return "CND\t"+yytext();
}
					case -138:
						break;
					case 156:
						{
  return "CND\t"+yytext();
}
					case -139:
						break;
					case 157:
						{
  return "CND\t"+yytext();
}
					case -140:
						break;
					case 158:
						{
  return "CND\t"+yytext();
}
					case -141:
						break;
					case 159:
						{
  return "CND\t"+yytext();
}
					case -142:
						break;
					case 160:
						{
  return "CND\t"+yytext();
}
					case -143:
						break;
					case 161:
						{
  return "CND\t"+yytext();
}
					case -144:
						break;
					case 162:
						{
  return "CND\t"+yytext();
}
					case -145:
						break;
					case 163:
						{
  return "CND\t"+yytext();
}
					case -146:
						break;
					case 164:
						{
  return "CND\t"+yytext();
}
					case -147:
						break;
					case 165:
						{
  return "CND\t"+yytext();
}
					case -148:
						break;
					case 166:
						{
  return "CND\t"+yytext();
}
					case -149:
						break;
					case 167:
						{
  return "CND\t"+yytext();
}
					case -150:
						break;
					case 168:
						{
  return "CND\t"+yytext();
}
					case -151:
						break;
					case 169:
						{
  return "CND\t"+yytext();
}
					case -152:
						break;
					case 170:
						{
  return "CND\t"+yytext();
}
					case -153:
						break;
					case 171:
						{
  return "CND\t"+yytext();
}
					case -154:
						break;
					case 172:
						{
  return "CND\t"+yytext();
}
					case -155:
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
