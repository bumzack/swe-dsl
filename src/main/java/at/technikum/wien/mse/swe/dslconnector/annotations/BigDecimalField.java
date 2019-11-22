package at.technikum.wien.mse.swe.dslconnector.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BigDecimalField {
    int position();
    int length();

    boolean padding() default true;

    char paddingCharacter() default '0';
    AlignmentEnum align() default AlignmentEnum.RIGHT;
}
