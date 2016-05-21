package org.kucro3.decl;

import java.util.Map;
import java.util.HashMap;

public enum DeclType {
	BOOLEAN("bool"),
	BYTE("byte"),
	CHAR("char"),
	DOUBLE("double"),
	FLOAT("float"),
	INT("int"),
	LONG("long"),
	SHORT("short"),
	STRING("string");
	
	private DeclType(String name)
	{
		this.name = name;
		reg(this);
	}
	
	public final String getName()
	{
		return name;
	}
	
	public static DeclType get(String name)
	{
		return types.get(name);
	}
	
	static void reg(DeclType type)
	{
		if(types == null)
			types = new HashMap<>();
		types.put(type.name, type);
	}
	
	private final String name;
	
	private static Map<String, DeclType> types;
}
