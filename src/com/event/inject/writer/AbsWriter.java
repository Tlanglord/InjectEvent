package com.event.inject.writer;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;

import com.event.inject.Inject;
import com.event.inject.utils.AnnotationUtil;
import com.event.inject.utils.IOUtil;

public abstract class AbsWriter implements AdapterWriter {

	ProcessingEnvironment mProcessingEnv;
	Filer mFiler;

	public AbsWriter(ProcessingEnvironment processingEnv) {
		mProcessingEnv = processingEnv;
		mFiler = processingEnv.getFiler();
	}

	@Override
	public void generate(Map<String, List<VariableElement>> typeMap) {
		// TODO Auto-generated method stub
		Iterator<Entry<String, List<VariableElement>>> iterator = typeMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, List<VariableElement>> entry = iterator.next();
            List<VariableElement> cacheElements = entry.getValue();
            if (cacheElements == null || cacheElements.size() == 0) {
                continue;
            }

            // ȡ��һ��Ԫ��������ע����Ϣ
            InjectorInfo info = createInjectorInfo(cacheElements.get(0));
            Writer writer = null;
            JavaFileObject javaFileObject;
            try {
                javaFileObject = mFiler.createSourceFile(info.getClzFullPath());
                writer = javaFileObject.openWriter();
                // д��package, import, class�Լ�findViews�����ȴ����
                generateImport(writer, info);
                // д������е������ֶε�findViews������
                for (VariableElement variableElement : entry.getValue()) {
                    writeField(writer, variableElement, info);
                }
                // д��findViews�����Ĵ������Լ���Ĵ�����
                writeEnd(writer);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtil.closeQuitly(writer);
            }
        }
	}
	
	
	InjectorInfo createInjectorInfo(VariableElement element) {
		    TypeElement typeElement = (TypeElement) element.getEnclosingElement();
	        String packageName = AnnotationUtil.getPackageName(mProcessingEnv, typeElement);
	        String className = typeElement.getSimpleName().toString();
	        return new InjectorInfo(packageName, className);
	}

	
    public static class InjectorInfo{
    	
    	public String pkgName;
    	public String clzName;
    	public String newClzName;
    	
    	public InjectorInfo(String pkgName, String clzName) {
    		this.pkgName = pkgName;
    		this.clzName = clzName;
    		newClzName = clzName + Inject.suffix;
    	}
    	
    	public String getClzFullPath() {
    		return pkgName  +"." +  newClzName;
    	}
    }
	
	abstract protected void generateImport(Writer writer, InjectorInfo jInfo);

	abstract protected void writeField(Writer writer, VariableElement element, InjectorInfo jInfo);

	abstract protected void writeEnd(Writer writer);

}
