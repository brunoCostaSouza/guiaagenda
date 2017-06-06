package anotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Atributo {
	String nome();
	String nomeColuna();
	boolean pk() default false;
}
