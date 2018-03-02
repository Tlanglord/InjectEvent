package com.event.inject.handler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import com.event.inject.anno.EventInject;
import com.event.inject.utils.AnnotationUtil;

public class EventAnnotationHandler implements AnnotationHandler {

	ProcessingEnvironment processingEnvironment;
	
	@Override
	public void attach(ProcessingEnvironment processingEnvironment) {
		// TODO Auto-generated method stub
		this.processingEnvironment = processingEnvironment;
	}

	@Override
	public Map<String, List<VariableElement>> handleAnnotation(RoundEnvironment roundEnv) {
		// TODO Auto-generated method stub
		Map<String, List<VariableElement>> annotationMap = new HashMap<String, List<VariableElement>>();
        // ��ȡʹ��ViewInjectorע�������Ԫ��
        Set<? extends Element> elementSet = roundEnv.getElementsAnnotatedWith(EventInject.class);
        for (Element element : elementSet) {
            // ע����ֶ�
            VariableElement varElement = (VariableElement) element;
            // ���͵�����·����,����ĳ��Activity������·��
            String className = getParentClassName(varElement);
            // ��ȡ������͵�����ע��,����ĳ��Activity�е�����View��ע�����
            List<VariableElement> cacheElements = annotationMap.get(className);
            if (cacheElements == null) {
                cacheElements = new LinkedList<VariableElement>();
            }
            // ��Ԫ����ӵ������Ͷ�Ӧ���ֶ��б���
            cacheElements.add(varElement);
            // �����·��Ϊkey,�ֶ��б�Ϊvalue,����map.
            // �����ǽ������ֶΰ����������ͽ��з���
            annotationMap.put(className, cacheElements);
        }

        return annotationMap;
	}
	
	  /**
     * ��ȡĳ���ֶ��������������·��
     * 
     * @param varElement �ֶ�Ԫ��
     * @return
     */
    private String getParentClassName(VariableElement varElement) {
        // ��ȡ��Ԫ�����ڵ�����,����ĳ��View��ĳ��Activity���ֶ�,������ǻ�ȡ���Activity������
        TypeElement typeElement = (TypeElement) varElement.getEnclosingElement();
        // ��ȡtypeElement�İ���
        String packageName = AnnotationUtil.getPackageName(processingEnvironment, typeElement);
        // ���͵�����·����,����ĳ��Activity������·��
        return packageName + "." + typeElement.getSimpleName().toString();
    }

}
