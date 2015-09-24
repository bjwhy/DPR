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
	 * 是否是公用静态方法
	 * 
	 * @param member
	 * @return
	 */
	private static boolean isPublicStatic(Member member) {
		boolean isPS = false;
		int mod = member.getModifiers();
		isPS = Modifier.isPublic(mod) && Modifier.isStatic(mod);
		return isPS;
	}

	/**
	 * 调用public静态方法
	 * 
	 * @param className
	 * @param methodName
	 * @param paramTypes
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static Object invokePublicStaticMethod(String className,
			String methodName, Class<?>[] paramTypes, Object[] params)
			throws Exception {

		Object value = null;
		Class<?> cls = Class.forName(className);

		Method method = cls.getMethod(methodName, paramTypes);

		if (isPublicStatic(method)) {
			value = method.invoke(null, params);
		}

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
	public static Object invokePublicMethod(String className,
			String methodName, Class<?>[] paramTypes, Object[] params)
			throws Exception {

		Object value = null;
		Class<?> cls = Class.forName(className);

		Method method = cls.getMethod(methodName, paramTypes);

		if (!isPublicStatic(method)) {
			value = method.invoke(cls.newInstance(), params);
		}

		return value;
	}
}