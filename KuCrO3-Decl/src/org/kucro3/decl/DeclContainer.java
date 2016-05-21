package org.kucro3.decl;

public interface DeclContainer {
	void putString(String name, String str);
	
	void putBoolean(String name, boolean b);
	
	void putByte(String name, byte b);
	
	void putChar(String name, char c);
	
	void putDouble(String name, double d);
	
	void putFloat(String name, float f);
	
	void putInt(String name, int i);
	
	void putLong(String name, long l);
	
	void putShort(String name, short s);
	
	void putObject(String name, Object obj);
	
	boolean contains(String name);
}
