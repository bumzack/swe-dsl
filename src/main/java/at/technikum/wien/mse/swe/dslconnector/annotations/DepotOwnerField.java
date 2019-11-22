package at.technikum.wien.mse.swe.dslconnector.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DepotOwnerField {
    int position();
    int lengthFirstName();
    int lengthLastName();

    char paddingChar() default ' ';

    boolean padding() default true;
    AlignmentEnum align() default AlignmentEnum.LEFT;
}
