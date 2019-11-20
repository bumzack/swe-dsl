package at.technikum.wien.mse.swe.dslconnector;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;

public class Parser {

    private final static String PACKAGE_NAME = "at.technikum.wien.mse.swe";

    void parse() throws FieldParserException {
//        try {
//            final List<Class<?>> classesList = getClassesForPackage(PACKAGE_NAME);
//            final List<Class> dbEntities = getDbEntities(classesList);
//            dbEntitesFields = getClassListMap(dbEntities);
//        } catch (Exception e) {
//            LOG.error("Exception while reading class reflection information " + e.getMessage());
//            throw new GrgDbException("Exception " + e.getMessage());
//        }


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
        NumDoubleParser balanceParser = new NumDoubleParser(pos, len, AlignmentEnum.RIGHT, '0');
        Double balance = balanceParser.parseValue(source);

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
