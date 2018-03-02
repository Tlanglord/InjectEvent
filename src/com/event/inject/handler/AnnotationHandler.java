package com.event.inject.handler;

import java.util.List;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.VariableElement;

public interface AnnotationHandler {
       
	void attach(ProcessingEnvironment processingEnvironment);
	
	Map<String, List<VariableElement>> handleAnnotation(RoundEnvironment roundEnvironment);
}
