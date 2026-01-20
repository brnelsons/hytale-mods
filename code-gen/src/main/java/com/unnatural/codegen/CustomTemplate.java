package com.unnatural.codegen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Not ready to use because development cycle depends specifically on the static resources directory.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CustomTemplate {
    String value() default "";

    String target() default "";

    CustomTemplateType type() default CustomTemplateType.HUD;
}
