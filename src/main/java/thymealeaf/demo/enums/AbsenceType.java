package thymealeaf.demo.enums;

public enum AbsenceType {
    ANNUAL_LEAVE("Annual Leave"), COMPANY_HOLIDAYS("Company Holidays"),
    BLOOD_DONATION("Blood Donation"), HOURS_OFF("Hours off"), MARRIAGE("Marriage"),
    MATERNITY_LEAVE("Maternity Leave"), REMOTE_WORK("Remote Work"), SICK_LEAVE("Sick Leave"),
    UNIVERSITY("University");

    private final String name;

    AbsenceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
