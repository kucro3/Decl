package org.kucro3.decl;

public class DeclElement {
	DeclElement(Object obj, DeclType type)
	{
		this.type = type;
		this.obj = obj;
	}
	
	public DeclType getType()
	{
		return type;
	}
	
	public Object get()
	{
		return obj;
	}
	
	public boolean getBoolean()
	{
		return (boolean) obj;
	}
	
	public byte getByte()
	{
		return (byte) obj;
	}
	
	public char getChar()
	{
		return (char) obj;
	}
	
	public double getDouble()
	{
		return (double) obj;
	}
	
	public float getFloat()
	{
		return (float) obj;
	}
	
	public int getInt()
	{
		return (int) obj;
	}
	
	public long getLong()
	{
		return (long) obj;
	}
	
	public short getShort()
	{
		return (short) obj;
	}
	
	public String getString()
	{
		return (String) obj;
	}
	
	@Override
	public String toString()
	{
		return new StringBuilder(type.getName()).append(" ").append(obj.toString()).toString();
	}
	
	final DeclType type;
	
	private Object obj;
}
