package com.event.inject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import com.event.inject.handler.AnnotationHandler;
import com.event.inject.handler.EventAnnotationHandler;
import com.event.inject.utils.Elog;
import com.event.inject.writer.AdapterWriter;
import com.event.inject.writer.JavaFileAdaterWriter;

@SupportedAnnotationTypes({"com.event.inject.anno.EventInject"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class InjectProcessor extends AbstractProcessor {

	List<AnnotationHandler> list = new ArrayList<>();
	Map<String, List<VariableElement>> map = new HashMap<>();
	ProcessingEnvironment processingEnvironment;
	AdapterWriter writer;
	
	
	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		// TODO Auto-generated method stub
		super.init(processingEnv);
		Elog.log("process init ..............");
		this.processingEnvironment = processingEnv;
		registerHandler();
		writer = new JavaFileAdaterWriter(processingEnv);
		Elog.log("process init ..............");
	}
	
	private void registerHandler() {
		list.add(new EventAnnotationHandler());
	}
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		Elog.log("process start ..............");
		
		
		// TODO Auto-generated method stub
		for(AnnotationHandler handler : list) {
			handler.attach(processingEnvironment);
			map.putAll(handler.handleAnnotation(roundEnv));
		}
		writer.generate(map);
		Elog.log("process end ..............");
		return false;
	}

}
