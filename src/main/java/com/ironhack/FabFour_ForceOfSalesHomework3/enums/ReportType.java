package com.ironhack.FabFour_ForceOfSalesHomework3.enums;

public enum ReportType {
    LEAD("Lead"),
    OPPORTUNITY("Opportunity"),
    CLOSED_WON("Closed won"),
    CLOSED_LOST("Closed lost"),
    OPEN("Open");

    public final String value;

    ReportType(String value) {
        this.value = value;
    }

    public static ReportType getReportType(String reportType) {
        for (ReportType data : values()) {
            if (data.value.equals(reportType.toUpperCase()))
                return data;
        }
        return null;
    }
}
