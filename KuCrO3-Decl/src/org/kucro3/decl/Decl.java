package org.kucro3.decl;

import java.util.*;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Decl {
	Decl()
	{
	}
	
	public static DeclObject toDecl(File file) throws IOException
	{
		DeclObject obj = new DeclObject();
		to(obj, file);
		return obj;
	}
	
	public static DeclObject toDecl(String... lines)
	{
		DeclObject obj = new DeclObject();
		to(obj, lines);
		return obj;
	}
	
	public static DeclObject toDecl(InputStream is) throws IOException
	{
		DeclObject obj = new DeclObject();
		to(obj, is);
		return obj;
	}
	
	public static Object toObject(Object obj, String... lines)
	{
		DeclReflection reflect = new DeclReflection(obj);
		to(reflect, lines);
		return obj;
	}
	
	public static Object toObject(Object obj, DeclObject decl)
	{
		DeclReflection reflect = new DeclReflection(obj);
		for(Map.Entry<String, DeclElement> entry : decl.objs.entrySet())
			reflect.put(entry.getKey(), entry.getValue().get());
		return obj;
	}
	
	public static Object toObject(Object obj, File file) throws IOException
	{
		DeclReflection reflect = new DeclReflection(obj);
		to(reflect, file);
		return obj;
	}
	
	public static Object toObject(Object obj, InputStream is) throws IOException
	{
		DeclReflection reflect = new DeclReflection(obj);
		to(reflect, is);
		return obj;
	}
	
	private static void to(DeclContainer container, String[] lines)
	{
		for(int i = 0; i < lines.length;)
			if(!lines[i].startsWith(";"))
				i += DeclParser.parse(lines, i, container);
			else
				i++;
	}
	
	private static void to(DeclContainer container, File file) throws IOException
	{
		to(container, new BufferedReader(new FileReader(file)));
	}
	
	private static void to(DeclContainer container, InputStream is) throws IOException
	{
		to(container, new BufferedReader(new InputStreamReader(is)));
	}
	
	private static void to(DeclContainer container, BufferedReader r) throws IOException
	{
		String line;
		BufferedReader reader = r;
		List<String> lines = new ArrayList<>();
		while((line = reader.readLine()) != null)
			lines.add(line);
		reader.close();
		to(container, lines.toArray(new String[0]));
	}
	
	public static String[] fromDecl(DeclObject obj)
	{
		String[] lines = new String[obj.objs.size()];
		int i = 0;
		DeclElement element;
		for(Map.Entry<String, DeclElement> entry : obj.objs.entrySet())
			lines[i++] = new StringBuilder((element = entry.getValue()).getType().getName())
				.append(" ").append(entry.getKey()).append(" = ").append(element.get().toString()).toString();
		return lines;
	}
	
	public static String[] fromObject(Object obj, boolean ignoreStatic, boolean ignoreTransient)
	{
		Class<?> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		List<String> lines = new ArrayList<>();
		String t, n, v;
		for(Field field : fields)
		{
			if(ignoreStatic && Modifier.isStatic(field.getModifiers()))
				continue;
			if(ignoreTransient && Modifier.isTransient(field.getModifiers()))
				continue;
			Class<?> type = field.getType();
			if(type.isPrimitive())
				t = type.getCanonicalName();
			else if(type == String.class)
				t = DeclType.STRING.getName();
			else
				continue;
			field.setAccessible(true);
			n = field.getName();
			try {
				v = field.get(obj).toString();
			} catch (IllegalArgumentException | IllegalAccessException e) {
				assert false;
				throw new IllegalArgumentException(e);
			}
			lines.add(new StringBuilder(t).append(" ").append(n).append(" = ").append(v).toString());
		}
		return lines.toArray(new String[0]);
	}
	
	public static void pour(DeclObject obj, File file) throws IOException
	{
		pour(fromDecl(obj), file);
	}
	
	public static void pour(Object obj, File file, boolean ignoreStatic, boolean ignoreTransient) 
			throws IOException
	{
		pour(fromObject(obj, ignoreStatic, ignoreTransient), file);
	}
	
	private static void pour(String[] lines, File file) throws IOException
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		for(String line : lines)
		{
			writer.write(line);
			writer.newLine();
		}
		writer.flush();
		writer.close();
	}
}