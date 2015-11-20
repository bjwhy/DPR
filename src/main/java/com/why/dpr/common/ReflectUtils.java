package com.why.dpr.common;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 反射工具类
 * 
 * @author Haiyang.Wu
 * 
 */
public class ReflectUtils {

	/**
	 * 创建类的实例，调用类的无参构造方法
	 * 
	 * @param className
	 * @return
	 * @throws Exception
	 */
	public static Object newInstance(String className) throws Exception {

		Object instance = null;

		Class<?> clazz = Class.forName(className);
		instance = clazz.newInstance();

		return instance;

	}

	/**
	 * 是否是公有方法
	 * 
	 * @param member
	 * @return
	 */
	private static boolean isPrivate(Member member) {
		int mod = member.getModifiers();
		return Modifier.isPrivate(mod);
	}

	/**
	 * 是否是私有静态方法
	 * 
	 * @param member
	 * @return
	 */
	private static boolean isPrivateStatic(Member member) {
		int mod = member.getModifiers();
		return Modifier.isPrivate(mod) && Modifier.isStatic(mod);
	}

	/**
	 * 调用静态方法
	 * 
	 * @param className
	 * @param methodName
	 * @param paramTypes
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static Object invokeStaticMethod(String className,
			String methodName, Class<?>[] paramTypes, Object[] params)
			throws Exception {

		Object value = null;
		Class<?> cls = Class.forName(className);

		Method method = cls.getDeclaredMethod(methodName, paramTypes);

		if (isPrivateStatic(method)) {
			method.setAccessible(true);
		}

		value = method.invoke(null, params);
		
		return value;
	}

	/**
	 * 调用public普通方法非static
	 * 
	 * @param className
	 * @param methodName
	 * @param paramTypes
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static Object invokeMethod(String className, String methodName,
			Class<?>[] paramTypes, Object[] params) throws Exception {

		Object value = null;
		Class<?> cls = Class.forName(className);

		Method method = cls.getMethod(methodName, paramTypes);

		if (isPrivate(method)) {
			method.setAccessible(true);
		}
		
		value = method.invoke(cls.newInstance(), params);

		return value;
	}
}