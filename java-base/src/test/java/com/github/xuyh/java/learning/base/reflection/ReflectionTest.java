package com.github.xuyh.java.learning.base.reflection;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 反射测试
 *
 * @see Taco
 */
class ReflectionTest {

  private final String clazzName = "com.github.xuyh.java.learning.base.reflection.Taco";

  @Test
  void testClass() throws Exception {

    Class<?> clazzA = Taco.class;

    assertThat(clazzA.getName(), equalTo(clazzName));

    Object obj = new Taco();

    Class<?> clazzB = obj.getClass();
    assertThat(clazzB.getName(), equalTo(clazzName));

    Class<?> clazzC = Class.forName(clazzName);
    assertThat(clazzC.getName(), equalTo(clazzName));
  }

  @Test
  void testNewInstance() throws Exception {
    Class<?> tacoClazz = Class.forName(clazzName);
    Object obj1 = tacoClazz.newInstance();
    assertThat(obj1 instanceof Taco, equalTo(true));

    Object obj2 = tacoClazz.getDeclaredConstructor().newInstance();
    assertThat(obj2 instanceof Taco, equalTo(true));
  }

  @Test
  void testClassLoader() throws Exception {
    // openjdk:corretto-11

    ClassLoader classLoader;
    // 系统的类加载器
    classLoader = ClassLoader.getSystemClassLoader();
    assertThat(classLoader.getName(), equalTo("app"));

    // 系统类加载器的父类加载器
    classLoader = classLoader.getParent();
    assertThat(classLoader.getName(), equalTo("platform"));

    // 扩展类加载器的父类加载器 不可获取
    classLoader = classLoader.getParent();
    assertThat(classLoader, nullValue());

    // Taco 类加载器
    classLoader = Class.forName(clazzName).getClassLoader();
    assertThat(classLoader.getName(), equalTo("app"));

    // JDK 提供的 Object 类加载器 不可获取
    classLoader = Class.forName("java.lang.Object").getClassLoader();
    assertThat(classLoader, nullValue());

    InputStream inputStream = Class.forName(clazzName).getClassLoader().getResourceAsStream("test.txt");
    assert inputStream != null;
    assertThat(new BufferedReader(new InputStreamReader(inputStream)).readLine(), equalTo("test001"));
  }

  @Test
  void testMethod() throws Exception {
    Class<?> clazz = Class.forName(clazzName);

    // 获取所有方法,不能获取private方法,且获取从父类继承来的所有方法
    Method[] methods;
    methods = clazz.getMethods();
    for (Method method : methods) {
      System.out.println("method = " + method.getName());
    }

    // 获取所有方法,包括私有方法,且只获取当前类的方法
    methods = clazz.getDeclaredMethods();
    for (Method method : methods) {
      System.out.println("method = " + method.getName());
    }

    // 获取指定的方法
    // 需要参数名称和参数列表，无参则不需要写
    Method method = clazz.getMethod("setName", String.class);
    System.out.println("method = " + method);

    // 执行方法
    Taco taco = (Taco) clazz.getDeclaredConstructor().newInstance();
    // 私有方法的执行
    // method.setAccessible(true);
    method.invoke(taco, "xuyh");
    assertThat(taco.getName(), equalTo("xuyh"));
  }

  @Test
  void testField() throws Exception {
    Class<?> clazz = Class.forName(clazzName);
    // 获取所有字段 可以获取公用和私有的所有字段，但不能获取父类字段
    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      System.out.println("field = " + field);
    }
    // 获取指定字段
    Field field = clazz.getDeclaredField("name");
    field.setAccessible(true);
    assertThat(field.getName(), equalTo("name"));

    Taco taco = new Taco();
    taco.setName("xuyh");

    Object objVal = field.get(taco);
    assertThat(objVal, equalTo("xuyh"));
  }

  @Test
  void testConstructor() throws Exception {
    Class<?> clazz = Class.forName(clazzName);
    // 获取全部
    Constructor<?>[] constructors = clazz.getConstructors();
    for (Constructor<?> constructor : constructors) {
      System.out.println("constructor = " + constructor);
    }

    Constructor<?> constructor = clazz.getConstructor(Long.class, String.class, Date.class);
    System.out.println("constructor = " + constructor);

    Object obj = constructor.newInstance(1L, "xuyh", new Date());
    assertThat(((Taco) obj).getName(), equalTo("xuyh"));
  }

  @Test
  void testAnnotation() throws Exception {
    Taco taco = new Taco();
    taco.setName("xuyh");

    Class<?> clazz = Class.forName(clazzName);
    Object obj = clazz.getDeclaredConstructor().newInstance();

    Method setNameMth = clazz.getDeclaredMethod("setName", String.class);
    setNameMth.invoke(obj, "xuyh");

    Field nameField = clazz.getDeclaredField("name");
    nameField.setAccessible(true);
    Annotation annotation = nameField.getAnnotation(NameValidator.class);
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
      if (annotation != null) {
        NameValidator nameValidator = (NameValidator) annotation;
        String val = (String) nameField.get(obj);
        if (val.length() > nameValidator.min() && val.length() < nameValidator.max()) {
          throw new IllegalArgumentException("length is illegal.");
        }
      }
    });
    assertTrue(thrown.getMessage().contains("length is illegal."));
  }
}
