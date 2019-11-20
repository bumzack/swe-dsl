package at.technikum.wien.mse.swe.dslconnector;

import at.technikum.wien.mse.swe.dslconnector.annotations.BigDecimalField;
import at.technikum.wien.mse.swe.dslconnector.annotations.DepotOwnerField;
import at.technikum.wien.mse.swe.dslconnector.annotations.RiskCategoryField;
import at.technikum.wien.mse.swe.dslconnector.annotations.StringField;
import at.technikum.wien.mse.swe.dslconnector.exception.FieldParserException;
import at.technikum.wien.mse.swe.dslconnector.parser.*;
import at.technikum.wien.mse.swe.model.SecurityAccountOverview;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Parser {

    private final static String PACKAGE_NAME = "at.technikum.wien.mse.swe";

    private Map<String, Class> mapping = new HashMap<>();

    void initMapping() {
        mapping.put("StringField", StringParser.class);
        mapping.put("BigDecimalField", BigDecimalParser.class);
    }

    void parse() throws FieldParserException {
//        try {
//            final List<Class<?>> classesList = getClassesForPackage(PACKAGE_NAME);
//            final List<Class> dbEntities = getDbEntities(classesList);
//            dbEntitesFields = getClassListMap(dbEntities);
//        } catch (Exception e) {
//            LOG.error("Exception while reading class reflection information " + e.getMessage());
//            throw new GrgDbException("Exception " + e.getMessage());
//        }


        parserTest();

    }

    private List<FieldParser> getParserList(final Class c) {
        final Field[] allFields = c.getDeclaredFields();

        final List<FieldParser> parser = Arrays.asList(allFields).stream().map(f -> {

            System.out.println("field name " + f.getName());
            Annotation[] annotations = f.getAnnotations();

            return Arrays.asList(annotations).stream()
                    .map(a -> {
                        System.out.println("      annotation name " + a.annotationType().getSimpleName());
                        if (a instanceof StringField) {
                            printStringField((StringField) a);
                            StringField field = (StringField) a;
                            return new StringParser(field.position(), field.length(), field.align(), field.padding(), field.paddingCharacter());
                        }

                        if (a instanceof BigDecimalField) {
                            printBigDecimal(a);
                            BigDecimalField field = (BigDecimalField) a;
                            return new BigDecimalParser(field.position(), field.length(), field.align(), field.padding());
                        }

                        if (a instanceof RiskCategoryField) {
                            printRiskCategory(a);
                            RiskCategoryField field = (RiskCategoryField) a;
                            return new RiskCategoryParser(field.position(), field.length(), field.align(), field.padding());
                        }

                        if (a instanceof DepotOwnerField) {
                            printDepotOwner(a);
                            DepotOwnerField field = (DepotOwnerField) a;
                            return new DeptOwnerParser(field.position(), field.lengthFirstName(), field.lengthLastName(), field.align(), field.padding());
                        }
                        // TODO ...
                        return null;
                    })
                    .findFirst()
                    // TODO
                    .get();
        }).collect(Collectors.toList());
        return parser;
    }

    private void printDepotOwner(Annotation a) {
        DepotOwnerField field = (DepotOwnerField) a;
        System.out.println("            align  " + field.align());
        System.out.println("            pos  " + field.position());
        System.out.println("            lengthFirstName  " + field.lengthFirstName());
        System.out.println("            lengthLastName  " + field.lengthLastName());
        System.out.println("            padding  '" + field.padding() + "'");
    }

    private void printRiskCategory(Annotation a) {
        RiskCategoryField field = (RiskCategoryField) a;
        System.out.println("            align  " + field.align());
        System.out.println("            pos  " + field.position());
        System.out.println("            len  " + field.length());
    }

    private void printBigDecimal(Annotation a) {
        BigDecimalField bd = (BigDecimalField) a;
        System.out.println("            align  " + bd.align());
        System.out.println("            pos  " + bd.position());
        System.out.println("            len  " + bd.length());
        System.out.println("            padding  '" + bd.padding() + "'");
    }

    private void printStringField(Annotation a) {
        StringField sf = (StringField) a;
        System.out.println("            align  " + sf.align());
        System.out.println("            pos  " + sf.position());
        System.out.println("            len  " + sf.length());
        System.out.println("            padding  '" + sf.padding() + "'");
        System.out.println("            paddingcharacter  '" + sf.paddingCharacter() + "'");
    }

    private void parserTest() throws FieldParserException {
        final String source = "SecurityAccountOverview                 012345678900                    MUSTERMANN                 MAX UND MARIAEUR          1692.45";


        List<FieldParser> parserList = getParserList(SecurityAccountOverview.class);

        parserList.forEach(p -> {
            try {
                System.out.println("parsed value  " + p.parseValue(source));
            } catch (FieldParserException e) {
                e.printStackTrace();
            }
        });
    }
}
