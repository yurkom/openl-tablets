package org.openl.rules.datatype.gen;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.core.ReflectUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.objectweb.asm.ClassWriter;
import org.openl.rules.datatype.gen.bean.writers.BeanByteCodeWriter;
import org.openl.util.RuntimeExceptionWrapper;
import org.openl.util.generation.JavaClassGeneratorHelper;

public abstract class BeanByteCodeGenerator {
    
    private final Log log = LogFactory.getLog(BeanByteCodeGenerator.class);
    
    /**
     * list of writers to generate necessary byte code class representation.
     */
    private List<BeanByteCodeWriter> writers = new ArrayList<BeanByteCodeWriter>();
    
    private byte[] generatedByteCode;
    
    private String beanName;
    
    private String beanNameWithPackage;
    
    public BeanByteCodeGenerator(String beanName) {
        this.beanName = beanName;
        this.beanNameWithPackage = JavaClassGeneratorHelper.replaceDots(beanName);
    }
    
    public byte[] generateClassByteCode() {
        if (generatedByteCode == null) {
            ClassWriter classWriter = new ClassWriter(0);

            for (BeanByteCodeWriter writer : writers) {
                writer.write(classWriter);
            }        

            generatedByteCode = classWriter.toByteArray();

//            writeBytesToFile(generatedByteCode);
        }
        return generatedByteCode;
    }
    
    /**
     * Return loaded to classpath class object
     * 
     * @param byteCode generated byteCode
     * 
     * @return <code>Class<?></code> descriptor for given byteCode
     */
    public Class<?> generateAndLoadBeanClass() {
        Class<?> result = null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            /** try to load bean class from current classloader.
                check if it already presents*/
            if (isClassLoaderContainsClass(classLoader, beanName)) {
                result = classLoader.loadClass(beanName);
                log.debug(String.format("Bean %s is using previously loaded", beanName));
                return result;
            } else {
                /** generate byte code*/
                generateClassByteCode();
                 
                /** add the generated byte code to the classloader*/
                result = ReflectUtils.defineClass(beanName, generatedByteCode, classLoader);
                log.debug(String.format("bean %s is using generated at runtime", beanName));
                return result;
            }
        } catch (Exception ex) {
            log.error(this, ex);
            throw RuntimeExceptionWrapper.wrap(ex);
        } 
    }
    
    protected boolean addWriter(BeanByteCodeWriter writer) {
        if (writer != null) {
            writers.add(writer);
            return true;
        }
        return false;
    }
    
    protected String getBeanNameWithPackage() {
        return beanNameWithPackage;
    }
    
    @SuppressWarnings("unused")
    private void writeBytesToFile(byte[] byteArray) {
        String strFilePath = String.format("D://gen//%s.class", JavaClassGeneratorHelper.getShortClassName(beanName));
        try {
            FileOutputStream fos = new FileOutputStream(strFilePath);
            fos.write(byteArray);
            fos.close();
        } catch(FileNotFoundException ex) {
            log.error(this, ex);
        } catch(IOException ioe){
            log.error(this, ioe);
        }
    }
    
    //TODO move to some utility class
    public static boolean isClassLoaderContainsClass(ClassLoader classLoader, String className){
        try {
            return classLoader.loadClass(className) != null;
        } catch (ClassNotFoundException e) {
            return false;
        }
    } 
    
    public String toString() {
        if (StringUtils.isNotBlank(beanName)) {
            return String.format("Bean with name: %s", beanName);
        } 
        return super.toString();
    }
}
