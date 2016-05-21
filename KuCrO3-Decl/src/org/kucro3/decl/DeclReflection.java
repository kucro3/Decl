package org.kucro3.decl;

import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.Field;

class DeclReflection implements DeclContainer {
	DeclReflection(Object obj)
	{
		this.obj = obj;
		this.clazz = obj.getClass();
	}
	
	@Override
	public void putObject(String name, Object obj)
	{
		put(name, obj);
	}

	@Override
	public void putString(String name, String str) 
	{
		put(name, str);
	}

	@Override
	public void putBoolean(String name, boolean b) 
	{
		put(name, b);
	}

	@Override
	public void putByte(String name, byte b) 
	{
		put(name, b);
	}

	@Override
	public void putChar(String name, char c) 
	{
		put(name, c);
	}

	@Override
	public void putDouble(String name, double d) 
	{
		put(name, d);
	}

	@Override
	public void putFloat(String name, float f) 
	{
		put(name, f);
	}

	@Override
	public void putInt(String name, int i) 
	{
		put(name, i);
	}

	@Override
	public void putLong(String name, long l) 
	{
		put(name, l);
	}

	@Override
	public void putShort(String name, short s) 
	{
		put(name, s);
	}

	@Override
	public boolean contains(String name) 
	{
		return declared.contains(name);
	}
	
	final void put(String name, Object obj)
	{
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			try {
				field.set(this.obj, obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new IllegalArgumentException(e);
			}
			declared.add(name);
		} catch (NoSuchFieldException e) {
			throw new IllegalArgumentException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}
	
	private final Set<String> declared = new HashSet<>();
	
	private final Class<?> clazz;
	
	private final Object obj;
}
