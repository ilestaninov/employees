package thymealeaf.demo.enums;

public enum AbsenceType {
    AnnualLeave("AnnualLeave"), CompanyHolidays("CompanyHolidays");

    private final String name;

    AbsenceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
