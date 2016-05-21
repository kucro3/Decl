package org.kucro3.decl;

import java.util.Map;
import java.util.HashMap;

public class DeclObject implements DeclContainer {
	public DeclObject()
	{
	}
	
	final void put(String name, Object obj, DeclType type)
	{
		objs.put(name, new DeclElement(obj, type));
	}
	
	@Override
	public void putString(String name, String str) 
	{
		put(name, str, DeclType.STRING);
	}

	@Override
	public void putBoolean(String name, boolean b) 
	{
		put(name, b, DeclType.BOOLEAN);
	}

	@Override
	public void putByte(String name, byte b) 
	{
		put(name, b, DeclType.BYTE);
	}

	@Override
	public void putChar(String name, char c) 
	{
		put(name, c, DeclType.CHAR);
	}

	@Override
	public void putDouble(String name, double d) 
	{
		put(name, d, DeclType.DOUBLE);
	}

	@Override
	public void putFloat(String name, float f) 
	{
		put(name, f, DeclType.FLOAT);
	}

	@Override
	public void putInt(String name, int i) 
	{
		put(name, i, DeclType.INT);
	}

	@Override
	public void putLong(String name, long l) 
	{
		put(name, l, DeclType.LONG);
	}

	@Override
	public void putShort(String name, short s) 
	{
		put(name, s, DeclType.SHORT);
	}
	
	public DeclElement get(String name)
	{
		return objs.get(name);
	}

	@Override
	public boolean contains(String name) 
	{
		return objs.containsKey(name);
	}
	
	final Map<String, DeclElement> objs = new HashMap<>();
	
}
