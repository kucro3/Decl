package org.kucro3.decl;

import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;

final class DeclParser {
	private DeclParser()
	{
	}
	
	static int parse(String[] lines, int off, DeclContainer container)
	{
		String line = lines[off];
		String word, name;
		int[] index = new int[1];
		boolean delegated = false;
		Delegate delegate = null;
			
		word = nextSpace(line, index);
		DeclType type = DeclType.get(word);
		if(type == null)
			throw unknownType(word);
		
		word = nextSpace(line, index);
		if(delegated = word.startsWith("("))
			if(word.endsWith(")")) 
			{ // delegate modifier
				word = word.substring(1, word.length() - 1);
				if(word.length() != 2)
					throw syntaxError();
				char delegatingPrefix = word.charAt(0);
				char delegatingIndex = word.charAt(1);
				int dIndex = Integer.parseInt("" + delegatingIndex);
				Delegate[] collection = delegates.get(delegatingPrefix);
				if(collection == null)
					throw invalidModifier(word);
				assert dIndex < 10;
				if((delegate = collection[dIndex]) == null)
					throw invalidModifier(word);
				name = nextSpace(line, index);
			}
			else
				throw syntaxError();
		else
			name = word;
		if(container.contains(name))
			throw redeclaration(name);
		checkName(name);
		if(delegated)
		{
			assert delegate != null;
			int[] used = new int[1];
			delegate.parse(name, type, lines, off, index[0], container, used);
			return used[0];
		}
		else
		{
			String operator = nextSpace(line, index);
			LineParser parser = operators.get(operator);
			if(parser == null)
				throw invalidOperator(operator);
			parser.parse(name, type, lines, off, index[0], container, null);
			return 1;
		}
	}
	
	private static void checkName(String name)
	{
		// TODO
	}
	
	private static String nextSpace(String line, int[] index)
	{
		for(int i = index[0]; i < line.length(); i++)
			if(line.charAt(i) == ' ')
			{
				String r = line.substring(index[0], i);
				index[0] = ++i;
				return r;
			}
		throw new IllegalArgumentException("End of line: " + line);
	}
	
	private static RuntimeException syntaxError()
	{
		return new IllegalArgumentException("syntax error");
	}
	
	private static RuntimeException unknownType(String t)
	{
		return new IllegalArgumentException("unknown type: " + t);
	}
	
	private static RuntimeException redeclaration(String n)
	{
		return new IllegalArgumentException("redeclaration of field: " + n);
	}
	
	private static RuntimeException invalidModifier(String m)
	{
		return new IllegalArgumentException("invalid modifier: " + m);
	}
	
	private static RuntimeException invalidOperator(String o)
	{
		return new IllegalArgumentException("invalid operator: " + o);
	}
	
	static interface TypeParser
	{
		void parse(String name, String value, DeclContainer container);
	}
	
	public static interface Handler
	{
		void parse(String name, DeclType type, String[] lines, int off, int index, DeclContainer container
				, int[] reserved);
	}
	
	static interface LineParser extends Handler
	{
	}
	
	static interface Delegate extends Handler
	{
	}
	
	private static final EnumMap<DeclType, TypeParser> parsers 
		= new EnumMap<DeclType, TypeParser>(DeclType.class)
			{
				/**
				 * 
				 */
				private static final long serialVersionUID = -8689029024324078856L;

				{
					put(DeclType.BOOLEAN, (n, v, c) -> {
						boolean b;
						if(v.equalsIgnoreCase("true"))
							b = true;
						else if(v.equalsIgnoreCase("false"))
							b = false;
						else
							throw syntaxError();
						c.putBoolean(n, b);
					});
					put(DeclType.BYTE, (n, v, c) -> c.putByte(n, Byte.parseByte(v)));
					put(DeclType.CHAR, (n, v, c) -> {
						if(v.length() != 1)
							throw syntaxError();
						c.putChar(n, v.charAt(0));
					});
					put(DeclType.DOUBLE, (n, v, c) -> c.putDouble(n, Double.parseDouble(v)));
					put(DeclType.FLOAT, (n, v, c) -> c.putFloat(n, Float.parseFloat(v)));
					put(DeclType.INT, (n, v, c) -> c.putInt(n, Integer.parseInt(v)));
					put(DeclType.LONG, (n, v, c) -> c.putLong(n, Long.parseLong(v)));
					put(DeclType.SHORT, (n, v, c) -> c.putShort(n, Short.parseShort(v)));
					put(DeclType.STRING, (n, v, c) -> c.putString(n, v));
				}
			};
			
	private static final Map<Character, Delegate[]> delegates
		= new HashMap<Character, Delegate[]>() 
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 7270956173030937781L;

			{
				
			}
		};
		
	private static final Map<String, LineParser> operators
		= new HashMap<String, LineParser>()
		{ 
			/**
			 * 
			 */
			private static final long serialVersionUID = 6123877124438124816L;

			{
				put("=", (name, type, lines, off, index, c, unused) -> {
					String cv = lines[off];
					String v = cv.substring(index, cv.length());
					TypeParser parser = parsers.get(type);
					assert parser != null;
					parser.parse(name, v, c);
				});
			}
		};
}
