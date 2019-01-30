package ohjelmointi.mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Defines annotation for JSONMap.
 *
 * @author  Samu Koivulahti
 * @version 2018.1215
 * @since   1.8
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JSONMap {}