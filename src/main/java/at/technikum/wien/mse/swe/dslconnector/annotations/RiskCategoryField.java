package at.technikum.wien.mse.swe.dslconnector.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RiskCategoryField {
    int position();

    int length() default 2;

    AlignmentEnum align() default AlignmentEnum.LEFT;

    boolean padding() default false;

    char paddingChar() default ' ';
}
