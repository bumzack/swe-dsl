package at.technikum.wien.mse.swe.dslconnector.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AmountField {
    int positionBalance();

    int lengthBalance();

    AlignmentEnum alignBalance() default AlignmentEnum.LEFT;

    boolean paddingBalance() default true;

    char paddingCharacterBalance() default ' ';

    int positionCurrency();

    int lengthCurrency();

    AlignmentEnum alignCurrency() default AlignmentEnum.LEFT;

    boolean paddingCurrency() default true;

    char paddingCharacterCurrency() default ' ';
}
