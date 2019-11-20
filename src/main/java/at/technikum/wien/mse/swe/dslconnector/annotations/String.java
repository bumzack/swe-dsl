package at.technikum.wien.mse.swe.dslconnector.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


// @String(position = 3, length = 4, padding =' ', align = LEFT
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface String {
    int position();
    int length();
    char padding();
    AlignmentEnum align();
}
