package cn.vv.gray.agent.core.util;

import cn.vv.gray.agent.core.logging.api.ILog;
import cn.vv.gray.agent.core.logging.api.LogManager;

import java.lang.reflect.Field;

/**
 * TODO
 *
 * @author Shane Fang
 * @since 1.0
 */
public class ReflectionUtils {
    private static final ILog logger = LogManager.getLogger(ReflectionUtils.class);

    public static final String CGLIB_CLASS_SEPARATOR = "$$";
    /**
     * 获取对象Class如果被cglib AOP过的对象或对象为CGLIB的Class，将获取真正的Class类型
     *
     * @param target  对象
     *
     * @return Class
     */
    public static Class<?> getTargetClass(Object target) {
        return getTargetClass(target.getClass());

    }

    /**
     * 获取Class如果被cglib AOP过的对象或对象为CGLIB的Class，将获取真正的Class类型
     *
     * @param targetClass  目标对象class
     *
     * @return Class
     */
    public static Class<?> getTargetClass(Class<?> targetClass) {

        Class clazz = targetClass;
        if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null && !Object.class.equals(superClass)) {
                return superClass;
            }
        }
        return clazz;
    }

    /**
     * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
     *
     * @param target
     *            目标对象Object
     * @param fieldName
     *            字段名称
     *
     * @return Object
     */
    public static <T> T getFieldValue(final Object target,
                                      final String fieldName) {

        Field field = getAccessibleField(target, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("找不到字段 [" + fieldName
                    + "] 在对象  [" + target + "] 里");
        }

        Object result = null;
        try {
            result = field.get(target);
        } catch (IllegalAccessException e) {
            logger.error(e,"不可能抛出的异常{}", e.getMessage());
        }
        return (T) result;
    }

    /**
     * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
     *
     * @param target
     *            目标对象Object
     * @param fieldName
     *            字段名称
     * @param value
     *            值
     */
    public static void setFieldValue(final Object target,
                                     final String fieldName, final Object value) {
        Field field = getAccessibleField(target, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("找不到字段 [" + fieldName
                    + "] 在对象 [" + target + "] 里");
        }

        try {
            field.set(target, value);
        } catch (IllegalAccessException e) {
            logger.error(e, "不可能抛出的异常:{}", e.getMessage());
        }
    }

    /**
     * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.如向上转型到Object仍无法找到, 返回null.
     *
     * @param target
     *            目标对象Object
     * @param fieldName
     *            字段名称
     *
     * @return {@link Field}
     */
    public static Field getAccessibleField(final Object target,
                                           final String fieldName) {
        return getAccessibleField(getTargetClass(target), fieldName);
    }

    /**
     * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.
     *
     * 如向上转型到Object仍无法找到, 返回null.
     *
     * @param targetClass
     *            目标对象Class
     * @param fieldName
     *            class中的字段名
     *
     * @return {@link Field}
     */
    public static Field getAccessibleField(final Class targetClass,
                                           final String fieldName) {
        for (Class<?> superClass = targetClass; superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
            }
        }
        return null;
    }
}
