package at.technikum.wien.mse.swe.dslconnector;

import at.technikum.wien.mse.swe.dslconnector.annotations.*;
import at.technikum.wien.mse.swe.model.SecurityAccountOverview;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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


        System.out.println("SecurityAccountOverview ");
        showAnnotations(SecurityAccountOverview.class);

        System.out.println("\n\n\n\nSecurityConfiguration ");
        showAnnotations(SecurityAccountOverview.class);


        //   parserTest();

    }

    private void showAnnotations(final Class c) {
        final Field[] allFields = c.getDeclaredFields();

        Arrays.asList(allFields).forEach(f -> {

            System.out.println("field name " + f.getName());
            Annotation[] annotations = f.getAnnotations();

            Arrays.asList(annotations).forEach(a -> {
                System.out.println("      annotation name " + a.annotationType().getSimpleName());
                if (a instanceof StringField) {
                    StringField sf = (StringField) a;
                    System.out.println("            align  " + sf.align());
                    System.out.println("            pos  " + sf.position());
                    System.out.println("            len  " + sf.length());
                    System.out.println("            padding  '" + sf.padding() + "'");
                    System.out.println("            paddingcharacter  '" + sf.paddingCharacter() + "'");
                }

                if (a instanceof BigDecimalField) {
                    BigDecimalField bd = (BigDecimalField) a;
                    System.out.println("            align  " + bd.align());
                    System.out.println("            pos  " + bd.position());
                    System.out.println("            len  " + bd.length());
                    System.out.println("            padding  '" + bd.padding() + "'");
                }

                if (a instanceof RiskCategoryField) {
                    RiskCategoryField field = (RiskCategoryField) a;
                    System.out.println("            align  " + field.align());
                    System.out.println("            pos  " + field.position());
                    System.out.println("            len  " + field.length());
                }

                if (a instanceof DepotOwnerField) {
                    DepotOwnerField field = (DepotOwnerField) a;
                    System.out.println("            align  " + field.align());
                    System.out.println("            pos  " + field.position());
                    System.out.println("            lengthFirstName  " + field.lengthFirstName());
                    System.out.println("            lengthLastName  " + field.lengthLastName());
                    System.out.println("            padding  '" + field.padding() + "'");
                }
            });
        });
    }

    private void parserTest() throws FieldParserException {
        final String source = "SecurityAccountOverview                 012345678900                    MUSTERMANN                 MAX UND MARIAEUR          1692.45";

        int pos = 0;
        int len = 40;
        StringParser transactionNameParser = new StringParser(pos, len, AlignmentEnum.LEFT, ' ');
        String transactionName = transactionNameParser.parseValue(source);

        pos += len;
        len = 10;

        NumLongParser securityAccountNumberParser = new NumLongParser(pos, len, AlignmentEnum.RIGHT, '0');
        Long securtityAcountNumber = securityAccountNumberParser.parseValue(source);

        pos += len;
        len = 2;
        NumLongParser riskCatergoryParser = new NumLongParser(pos, len, AlignmentEnum.RIGHT, '0');
        Long riskCategory = riskCatergoryParser.parseValue(source);

        pos += len;
        len = 30;
        StringParser lastnameParser = new StringParser(pos, len, AlignmentEnum.RIGHT, '0');
        String lastName = lastnameParser.parseValue(source);

        pos += len;
        len = 30;
        StringParser firstnameParser = new StringParser(pos, len, AlignmentEnum.RIGHT, '0');
        String firstName = firstnameParser.parseValue(source);

        pos += len;
        len = 3;
        StringParser currencyParser = new StringParser(pos, len, AlignmentEnum.RIGHT, '0');
        String currency = currencyParser.parseValue(source);

        pos += len;
        len = 17;
        BigDecimalParser balanceParser = new BigDecimalParser(pos, len, AlignmentEnum.RIGHT, '0');
        BigDecimal balance = balanceParser.parseValue(source);

        pos += len;

        System.out.println("transactionName   '" + transactionName + "'");
        System.out.println("securtityAcountNumber   '" + securtityAcountNumber + "'");
        System.out.println("riskCategory   '" + riskCategory + "'");
        System.out.println("lastName   '" + lastName + "'");
        System.out.println("firstName   '" + firstName + "'");
        System.out.println("currency   '" + currency + "'");
        System.out.println("balance   '" + balance + "'");

        System.out.println("pos   '" + pos + "'");
        System.out.println("len(source)   '" + source.length() + "'");
    }
}
