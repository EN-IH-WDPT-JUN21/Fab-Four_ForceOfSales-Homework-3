package com.ironhack.FabFour_ForceOfSalesHomework3.enums;

public enum ReportType {
    LEADBYSALESREP("Report Lead By SalesRep"),
    OPPBYSALESREP("Report Opportunity By SalesRep"),
    WONBYSALESREP("Report CLOSED_WON by SalesRep"),
    LOSTBYSALESREP("Report CLOSED_LOST by SalesRep"),
    OPENBYSALESREP("Report OPEN by SalesRep"),
    OPPSBYPRODUCT("Report Opportunity by the product"),
    WONBYPRODUCT("Report CLOSED_WON by the product"),
    LOSTBYPRODUCT("Report CLOSED_LOST by the product"),
    OPENBYPRODUCT("Report OPEN by the product"),
    OPPBYCOUNTRY("Report Opportunity by Country"),
    WONBYCOUNTRY("Report CLOSED_WON by Country"),
    LOSTBYCOUNTRY("Report CLOSED_LOST by Country"),
    OPENBYCOUNTRY("Report OPEN by Country"),
    OPPBYCITY("Report Opportunity by City"),
    WONBYCITY("Report CLOSED_WON by City"),
    LOSTBYCITY("Report CLOSED_LOST by City"),
    OPENBYCITY("Report OPEN by City"),
    OPPBYINDUSTRY("Report Opportunity by Industry"),
    WONBYINDUSTRY("Report CLOSED-WON by Industry"),
    LOSTBYINDUSTRY("Report CLOSED-LOST by Industry"),
    OPENBYINDUSTRY("Report OPEN by Industry"),
    MEANEMPLOYEECOUNT("Mean EmployeeCount"),
    MEDIANEMPLOYEECOUNT("Median EmployeeCount"),
    MAXEMPLOYEECOUNT("Max EmployeeCount"),
    MINEMPLOYEECOUNT("Min EmployeeCount"),
    MEANQUANITY("Mean Quantity"),
    MEDIANQUANTITY("Median Quantity"),
    MAXQUANTITY("Max Quantity"),
    MINQUANTITY("Min Quantity"),
    MEANOPPS("Mean Opps per Account"),
    MEDIANOPPS("Median Opps per Account"),
    MAXOPPS("Max Opps per Account"),
    MINOPPS("Min Opps per Account"),
    QUIT("Quit");

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
