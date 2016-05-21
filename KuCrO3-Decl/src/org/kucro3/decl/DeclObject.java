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
	public void putObject(String name, Object obj)
	{
		throw new UnsupportedOperationException();
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
	
	@SuppressWarnings("unchecked")
	final <T> T get(String name, DeclType type, Class<T> rt)
	{
		DeclElement element = objs.get(name);
		if(element == null)
			return null;
		if(!element.getType().equals(type))
			return null;
		return (T) element.get();
	}
	
	public DeclElement get(String name)
	{
		return objs.get(name);
	}
	
	public boolean getBoolean(String name)
	{
		return get(name, DeclType.BOOLEAN, boolean.class);
	}
	
	public byte getByte(String name)
	{
		return get(name, DeclType.BYTE, byte.class);
	}
	
	public char getChar(String name)
	{
		return get(name, DeclType.CHAR, char.class);
	}
	
	public double getDouble(String name)
	{
		return get(name, DeclType.DOUBLE, double.class);
	}
	
	public float getFloat(String name)
	{
		return get(name, DeclType.FLOAT, float.class);
	}
	
	public int getInt(String name)
	{
		return get(name, DeclType.INT, int.class);
	}
	
	public long getLong(String name)
	{
		return get(name, DeclType.LONG, long.class);
	}
	
	public short getShort(String name)
	{
		return get(name, DeclType.SHORT, short.class);
	}
	
	public String getString(String name)
	{
		return get(name, DeclType.STRING, String.class);
	}
	
	@Override
	public boolean contains(String name) 
	{
		return objs.containsKey(name);
	}
	
	final Map<String, DeclElement> objs = new HashMap<>();
}
