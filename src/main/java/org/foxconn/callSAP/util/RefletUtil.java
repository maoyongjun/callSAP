package org.foxconn.callSAP.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


public class RefletUtil {

	public static void setFieldValue(Object target, String fname, Class ftype, Object fvalue) { // 设置字段值
		// 如：username
		// 字段,setUsername(String
		// username)
		if (target == null || fname == null || "".equals(fname) || (fvalue != null && !ftype.isAssignableFrom(fvalue.getClass()))) {// 如果类型不匹配，直接退出
			return;
		}
		Class clazz = target.getClass();
		try { // 先通过setXxx()方法设置类属性值
			String methodname = "set" + Character.toUpperCase(fname.charAt(0)) + fname.substring(1);
			//System.out.println(methodname);
			Method method = clazz.getDeclaredMethod(methodname, ftype); // 获取定义的方法
			if (!Modifier.isPublic(method.getModifiers())) { // 设置非共有方法权限
				method.setAccessible(true);
			}
			method.invoke(target, fvalue); // 执行方法回调
		} catch (Exception me) {// 如果set方法不存在，则直接设置类属性值
			try {
				Field field = clazz.getDeclaredField(fname); // 获取定义的类属性
				if (!Modifier.isPublic(field.getModifiers())) { // 设置非共有类属性权限
					field.setAccessible(true);
				}
				field.set(target, fvalue); // 设置类属性值

			} catch (Exception fe) {

			}
		}
	}

	public static Object getFieldValue(Object target, String fname, Class ftype) {// 获取字段值
		if (target == null || fname == null || "".equals(fname)) {
			return null;
		}
		Class clazz = target.getClass();
		try { // 先通过getXxx()方法获取类属性值
			String methodname = "get" + Character.toUpperCase(fname.charAt(0)) + fname.substring(1);
			//System.out.println(methodname);
			Method method = clazz.getDeclaredMethod(methodname); // 获取定义的方法
			if (!Modifier.isPublic(method.getModifiers())) { // 设置非共有方法权限
				method.setAccessible(true);
			}
			return method.invoke(target); // 方法回调，返回值
		} catch (Exception me) {// 如果get方法不存在，则直接获取类属性值
			me.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		Object testm;
		try {
			testm = Class.forName("org.foxconn.util.TestMethod").newInstance();
			RefletUtil.setFieldValue(testm, "mtd", String.class,"abc");
			Object getResult = RefletUtil.getFieldValue(testm, "mtd", String.class);
			System.out.println(getResult.toString());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

class TestMethod {

	public String mtd;

	public String getMtd() {
		return "abc";
	}

	 private void setMtd(String mtd) {
	 this.mtd = mtd;
	 }

}