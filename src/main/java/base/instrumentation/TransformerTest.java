package base.instrumentation;

import javassist.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class TransformerTest implements ClassFileTransformer {
    public byte[] transform(ClassLoader loader, String className,
                            Class classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println("load class:" + className);
        if ("base/instrumentation/Hello".equals(className)) {
            System.out.println("Instrumenting......");
            try {
                CtClass ctClass = ClassPool.getDefault().get(className.replace('/', '.'));
                CtMethod sayHelloMethod = ctClass.getDeclaredMethod("sayHello");
                sayHelloMethod.insertBefore("System.out.println(\"before sayHello----\");");
                sayHelloMethod.insertAfter("System.out.println(\"after sayHello----\");");
                return ctClass.toBytecode();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return classfileBuffer;
    }
}
