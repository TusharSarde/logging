package org.apache.logging.log4j.samples.app;

import org.apache.logging.log4j.EventLogger;
import org.apache.logging.log4j.message.StructuredDataMessage;
import org.apache.logging.log4j.samples.dto.AuditEvent;
import org.apache.logging.log4j.samples.dto.Constraint;
import org.apache.logging.log4j.samples.util.NamingUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class LogEventFactory {

    public static <T> T getEvent(final Class<T> intrface) {

        final Class<?>[] interfaces = new Class<?>[]{intrface};

        final String eventId = NamingUtils.lowerFirst(intrface.getSimpleName());
        final StructuredDataMessage msg = new StructuredDataMessage(eventId, null, "Audit");
        final AuditEvent audit = (AuditEvent) Proxy.newProxyInstance(intrface.getClassLoader(),
                                                                     interfaces,
                                                                     new AuditProxy(msg, intrface));

        return (T) audit;
    }

    private static class AuditProxy implements InvocationHandler {

        private final StructuredDataMessage msg;
        private final Class intrface;

        public AuditProxy(final StructuredDataMessage msg, final Class intrface) {
            this.msg = msg;
            this.intrface = intrface;
        }

        public Object invoke(final Object o, final Method method, final Object[] objects)
                throws Throwable {
            if (method.getName().equals("logEvent")) {

                final StringBuilder missing = new StringBuilder();

                final Method[] methods = intrface.getMethods();

                for (final Method _method : methods) {
                    final String name = NamingUtils.lowerFirst(NamingUtils.getMethodShortName(_method.getName()));

                    final Annotation[] annotations = _method.getDeclaredAnnotations();
                    for (final Annotation annotation : annotations) {
                        final Constraint constraint = (Constraint) annotation;

                        if (constraint.required() && msg.get(name) == null) {
                            if (missing.length() > 0) {
                                missing.append(", ");
                            }
                            missing.append(name);
                        }
                    }
                }

                if (missing.length() > 0) {
                    throw new IllegalStateException("Event " + msg.getId().getName()
                                                             + " is missing required attributes " + missing);
                }
                EventLogger.logEvent(msg);
            }
            if (method.getName().equals("setCompletionStatus")) {
                final String name = NamingUtils.lowerFirst(NamingUtils.getMethodShortName(method.getName()));
                msg.put(name, objects[0].toString());
            }
            if (method.getName().startsWith("set")) {
                final String name = NamingUtils.lowerFirst(NamingUtils.getMethodShortName(method.getName()));

                /*
                 * Perform any validation here. Currently the catalog doesn't
                 * contain any information on validation rules.
                 */
                msg.put(name, objects[0].toString());
            }

            return null;
        }
    }
}
