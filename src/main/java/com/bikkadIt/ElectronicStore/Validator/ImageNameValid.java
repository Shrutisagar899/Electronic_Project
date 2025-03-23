package com.bikkadIt.ElectronicStore.Validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class )
  public @interface ImageNameValid {
        //error message
    String message() default "Invalid Image !!";

    //represent group of constraint
    Class <?> [] groups() default { };

    //additional interface about information
    Class <? extends Payload>[] payload() default { };

   }
