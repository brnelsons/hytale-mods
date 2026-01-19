package com.unnatural.codegen;

import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

@SuppressWarnings("unused")
@SupportedAnnotationTypes({
        "com.unnatural.codegen.CustomTemplate",
})
@SupportedSourceVersion(SourceVersion.RELEASE_25)
@AutoService(Processor.class)
public class UiTemplateAnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(CustomTemplate.class)) {
            // Code generation logic here
            CustomTemplate customTemplateDef = element.getAnnotation(CustomTemplate.class);
            String templateValue = customTemplateDef.value();
            if (templateValue == null) {
                return true;
            }
            String targetFileName = customTemplateDef.target();
            if (targetFileName == null) {
                return true;
            }
            // Use processingEnv.getFiler() to create new source files
            try {
                FileObject resource = processingEnv.getFiler().createResource(
                        StandardLocation.CLASS_OUTPUT,
                        "Common.UI.Custom",
                        targetFileName,
                        element
                );
                try (OutputStream os = resource.openOutputStream()) {
                    os.write(templateValue.getBytes(StandardCharsets.UTF_8));
                }
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Generated: " + targetFileName);
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(javax.tools.Diagnostic.Kind.ERROR, "Failed to create resource file: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return true;
    }
}
