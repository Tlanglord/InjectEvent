package com.event.inject.writer;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.VariableElement;

import com.event.inject.anno.EventInject;

public class JavaFileAdaterWriter extends AbsWriter {

	public JavaFileAdaterWriter(ProcessingEnvironment processingEnvironment) {
		// TODO Auto-generated constructor stub
		super(processingEnvironment);
	}

	@Override
	public void generate(Map<String, List<VariableElement>> typeMap) {
		// TODO Auto-generated method stub
		super.generate(typeMap);
	}

	@Override
	protected void generateImport(Writer writer, InjectorInfo jInfo) {
		// TODO Auto-generated method stub
		try {
			writer.write("package  " + jInfo.pkgName + "; \n");
			writer.write("\n\n");
			writer.write("import " + jInfo.pkgName + "." + jInfo.clzName + ";");
			writer.write("import com.event.inject.adapter.InjectAdapter;");

			writer.write("public class " + jInfo.newClzName + " implements InjectAdapter<"+jInfo.clzName+">{");
			writer.write("\n\n");
			writer.write("public void injects( " + jInfo.clzName + " target" + ") {");
			writer.write("\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void writeField(Writer writer, VariableElement element, InjectorInfo jInfo) {
		// TODO Auto-generated method stub
		EventInject eventInject = element.getAnnotation(EventInject.class);
		String filed = element.getSimpleName().toString();
		try {
			writer.write("target." + filed + "=" + eventInject.value() + ";");
			writer.write("\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void writeEnd(Writer writer) {
		// TODO Auto-generated method stub
		try {
			writer.write("  }");
			writer.write("\n\n");
			writer.write(" } ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
