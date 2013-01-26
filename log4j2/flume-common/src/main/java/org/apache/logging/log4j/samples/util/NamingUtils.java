package org.apache.logging.log4j.samples.util;

public class NamingUtils {

    public static String getPackageName(final String className) {
        return className.substring(0, className.lastIndexOf("."));
    }

    public static String getSimpleName(final String className) {
        return className.substring(className.lastIndexOf(".") + 1);
    }

    public static String getMethodShortName(final String name) {
        return name.replaceFirst("(get|set|is|has)", "");
    }

    public static String upperFirst(final String name) {
        return String.valueOf(name.charAt(0)).toUpperCase() + name.substring(1);
    }

    public static String lowerFirst(final String name) {
        return String.valueOf(name.charAt(0)).toLowerCase() + name.substring(1);
    }

    public static String getSetterName(final String fieldName) {
        return "set" + upperFirst(fieldName);
    }

    public static String getGetterName(final String fieldName, final String type) {
        if ("boolean".equals(type)) {
            return "is" + upperFirst(fieldName);
        } else {
            return "get" + upperFirst(fieldName);
        }
    }

    public static void main(final String[] args) {
        final String blah = "com.test.generator.Classname";
        System.out.println(getSimpleName(blah));
        System.out.println(lowerFirst(getSimpleName(blah)));

        System.out.println(getPackageName(blah));

        System.out.println(getMethodShortName("getName"));
        System.out.println(getMethodShortName("setName"));
    }

    public static String getClassName(final String className) {
        return upperFirst(className.replaceAll("[^a-zA-Z0-9_]+", ""));
    }

    public static String getFieldName(final String fieldName) {
        return fieldName.replaceAll("[^a-zA-Z0-9_]+", "");
    }

    public static String methodCaseName(final String variable) {
        return variable.substring(0, 1).toUpperCase() + variable.substring(1);
    }

    public static String getAccessorName(final String type, final String methodName) {
        String prefix = "get";
        if (type.equals("boolean")) {
            prefix = "is";
        }
        return prefix + methodCaseName(methodName);
    }

    public static String getMutatorName(final String methodName) {
        return "set" + methodCaseName(methodName);
    }
}
