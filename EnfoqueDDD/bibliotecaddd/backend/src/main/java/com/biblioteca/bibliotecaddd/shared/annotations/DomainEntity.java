package com.biblioteca.bibliotecaddd.shared.annotations;

import java.lang.annotation.*;

/**
 * Marca una clase como entidad de dominio para propósitos de documentación y validación.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface DomainEntity {
    String value() default "";
}