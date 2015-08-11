package com.why.dpr.common;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 反射工具类
 * 
 * @author Haiyang.Wu
 * 
 */
public class ReflectUtils {
	private static final Logger logger = LogManager
			.getLogger(ReflectUtils.class);

	/**
	 * 创建类的实例，调用类的无参构造方法
	 * 
	 * @param className
	 * @return
	 */
	public static Object newInstance(String className) {

		Object instance = null;

		try {
			Class<?> clazz = Class.forName(className);
			instance = clazz.newInstance();
		} catch (ClassNotFoundException e) {
			logger.warn(e.getMessage());
		} catch (InstantiationException e) {
			// if this Class represents an abstract class, an interface, an
			// array class, a primitive type, or void; or if the class has no
			// nullary constructor; or if the instantiation fails for some other
			// reason.
			logger.warn(e.getMessage());
		} catch (IllegalAccessException e) {
			// if the class or its nullary constructor is not accessible
			logger.warn(e.getMessage());
		}

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
	 */
	public static Object invokePublicStaticMethod(String className,
			String methodName, Class<?>[] paramTypes, Object[] params) {

		Object value = null;
		try {
			Class<?> cls = Class.forName(className);

			Method method = cls.getMethod(methodName, paramTypes);

			if (isPublicStatic(method)) {
				value = method.invoke(null, params);
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
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
	 */
	public static Object invokePublicMethod(String className,
			String methodName, Class<?>[] paramTypes, Object[] params) {

		Object value = null;
		try {
			Class<?> cls = Class.forName(className);

			Method method = cls.getMethod(methodName, paramTypes);

			if (!isPublicStatic(method)) {
				value = method.invoke(cls.newInstance(), params);
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}

		return value;
	}
}