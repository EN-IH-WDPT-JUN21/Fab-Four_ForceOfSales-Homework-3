package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Command;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Status;
import org.springframework.stereotype.Service;

import static com.ironhack.FabFour_ForceOfSalesHomework3.service.AccountService.lookUpAccount;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.AccountService.showAccounts;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.InputOutputService.*;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.LeadObjectService.*;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.OpportunityService.*;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.ReportService.*;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.SalesRepService.*;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.ContactService.*;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.StatesService.*;

@Service
public class CommandHandlerService {
    
    public static void handleCommand(String command) {
        String upperCommand = command.trim().toUpperCase(); // delete all spaces around command
        String defaultInfo = "Command not recognised. Try again or type: help"; //default info message
        long id = getIdFromInput(upperCommand); // get id from the end of command - if there is no long value then method returns 0;

        if (id < 1) { // case when command hasn't any id at the end
            switch (Command.getCommandType(upperCommand)) {
                case NEW_LEAD:
                    createLead();
                    break;
                case NEW_SALESREP:
                    newSalesRep();
                    break;
                case SHOW_SALESREPS:
                    showSalesReps();
                    break;
                case SHOW_LEADS:
                    showLeads();
                    break;
                case SHOW_CONTACTS:
                    showAllContacts();
                    break;
                case SHOW_OPPORTUNITIES:
                    showAllOpportunities();
                    break;
                case MEDIAN_EMPLOYEE_COUNT:
                    printMedianValues(Command.MEDIAN_EMPLOYEE_COUNT.value);
                    break;
                case MAX_EMPLOYEE_COUNT:
                    printMaxValues(Command.MAX_EMPLOYEE_COUNT.value);
                    break;
                case MIN_EMPLOYEE_COUNT:
                    printMinValues(Command.MIN_EMPLOYEE_COUNT.value);
                    break;
                case MEAN_EMPLOYEE_COUNT:
                    printMeanValues(Command.MEAN_EMPLOYEE_COUNT.value);
                    break;
                case MEDIAN_QUANTITY:
                    printMedianValues(Command.MEDIAN_QUANTITY.value);
                    break;
                case MAX_QUANTITY:
                    printMaxValues(Command.MAX_QUANTITY.value);
                    break;
                case MIN_QUANTITY:
                    printMinValues(Command.MIN_QUANTITY.value);
                    break;
                case MEAN_QUANTITY:
                    printMeanValues(Command.MEAN_QUANTITY.value);
                    break;
                case MEDIAN_OPPS_PER_ACCOUNT:
                    printMedianValues(Command.MEDIAN_OPPS_PER_ACCOUNT.value);
                    break;
                case MAX_OPPS_PER_ACCOUNT:
                    printMaxValues(Command.MAX_OPPS_PER_ACCOUNT.value);
                    break;
                case MIN_OPPS_PER_ACCOUNT:
                    printMinValues(Command.MIN_OPPS_PER_ACCOUNT.value);
                    break;
                case MEAN_OPPS_PER_ACCOUNT:
                    printMeanValues(Command.MEAN_OPPS_PER_ACCOUNT.value);
                case SHOW_ACCOUNTS:
                    showAccounts();
                    break;
                case EXPORT_LEADS:
                    exportLeadInformation();
                    break;
                case EXPORT_OPPORTUNITIES:
                    exportOppInformation();
                    break;
                case EXPORT_ACCOUNTS:
                    exportAccountInformation();
                    break;
                case LEADBYSALESREP:
                    reportLeadsBySalesRep();
                    break;
                case OPPBYSALESREP:
                    reportOpportunitiesBySalesRep();
                    break;
                case OPPSBYPRODUCT:
                    reportOpportunitiesByProduct();
                    break;
                case OPENBYPRODUCT:
                    reportOpportunitiesByProductStatus(Status.OPEN);
                    break;
                case WONBYPRODUCT:
                    reportOpportunitiesByProductStatus(Status.CLOSED_WON);
                    break;
                case LOSTBYPRODUCT:
                    reportOpportunitiesByProductStatus(Status.CLOSED_LOST);
                    break;
                case OPPBYCOUNTRY:
                    reportOpportunitiesByCountry();
                    break;
                case WONBYCOUNTRY:
                    reportOpportunitiesByCountryStatus(Status.CLOSED_WON);
                    break;
                case LOSTBYCOUNTRY:
                    reportOpportunitiesByCountryStatus(Status.CLOSED_LOST);
                    break;
                case OPENBYCOUNTRY:
                    reportOpportunitiesByCountryStatus(Status.OPEN);
                    break;
                case OPPBYCITY:
                    reportOpportunitiesByCity();
                    break;
                case WONBYCITY:
                    reportOpportunitiesByCityStatus(Status.CLOSED_WON);
                    break;
                case LOSTBYCITY:
                    reportOpportunitiesByCityStatus(Status.CLOSED_LOST);
                    break;
                case OPENBYCITY:
                    reportOpportunitiesByCityStatus(Status.OPEN);
                    break;
                case OPPBYINDUSTRY:
                    reportOpportunitiesByIndustry();
                    break;
                case WONBYINDUSTRY:
                    reportOpportunitiesByIndustryStatus(Status.CLOSED_WON);
                    break;
                case LOSTBYINDUSTRY:
                    reportOpportunitiesByIndustryStatus(Status.CLOSED_LOST);
                    break;
                case OPENBYINDUSTRY:
                    reportOpportunitiesByIndustryStatus(Status.OPEN);
                    break;
                case WONBYSALESREP:
                    reportOpportunitiesBySalesRepAndStatus(Status.CLOSED_WON);
                    break;
                case LOSTBYSALESREP:
                    reportOpportunitiesBySalesRepAndStatus(Status.CLOSED_LOST);
                    break;
                case OPENBYSALESREP:
                    reportOpportunitiesBySalesRepAndStatus(Status.OPEN);
                    break;
                case HELP:
                    System.out.println(
                            "Type one of the below statements to execute:\n\n" +
                                    "CREATE A NEW DATASET\n" +
                                    " > new lead - " + "to create a new lead\n" +
                                    " > new salesRep - " + "to create a new salesRep\n" +
                                    " > convert {id} - to convert a lead to an opportunity\n" +

                                    "\nCLOSE AN OPPORTUNITY\n" +
                                    " > close-won {id} - to close case after sale\n" +
                                    " > close-lost {id} - to close case without sale\n" +

                                    "\nLOOKUP A SPECIFIC DATASET\n" +
                                    " > lookup lead {id} - to show specific lead\n" +
                                    " > lookup opportunity {id} - to show specific opportunity\n" +
                                    " > lookup account {id} - to show specific account\n" +
                                    " > lookup salesrep {id} - to show specific salesRep\n" +

                                    "\nSHOW ALL DATA PER TYPE\n" +
                                    " > show leads - to show all of leads\n" +
                                    " > show salesReps - to show all the salesReps\n" +
                                    " > show accounts - to show all the accounts\n" +

                                    "\nEXPORT SOME DATA\n" +
                                    " > export leads - to export all current leads\n" +
                                    " > export opportunities - to export all current opportunities\n" +
                                    " > export accounts - to export all accounts\n" +

                                    "\nGENERATE REPORTS\n" +

                                    " > Report Lead By SalesRep \n" +
                                    " > Report Opportunity By <reportType> : 'SalesRep', 'the product', 'Country', 'City' or 'Industry' \n" +
                                    " > Report CLOSED-WON by <reportType> : 'SalesRep', 'the product', 'Country', 'City' or 'Industry' \n" +
                                    " > Report CLOSED-LOST by <reportType> : 'SalesRep', 'the product', 'Country', 'City' or 'Industry' \n" +
                                    " > Report OPEN by <reportType> : 'SalesRep', 'the product', 'Country', 'City' or 'Industry' \n" +
                                    " > Mean <reportType>: 'EmployeeCount', 'Quantity', 'Opps per Account' \n" +
                                    " > Median <reportType>: 'EmployeeCount', 'Quantity', 'Opps per Account' \n" +
                                    " > Max <reportType>: 'EmployeeCount', 'Quantity', 'Opps per Account' \n" +
                                    " > Min <reportType>: 'EmployeeCount', 'Quantity', 'Opps per Account' \n" +

                                    "\nTO QUIT\n" +
                                    " > quit - to leave the app"
                    );
                    break;
                default:
                    System.out.println(defaultInfo);
            }
        } else { // case when command has an id at the end

            upperCommand = getTextWithoutId(upperCommand);

            switch (Command.getCommandType(upperCommand)) {
                case CONVERT:
                    convertLead(id);
                    break;
                case LOOKUP_SALESREP:
                    lookUpSalesRep(id);
                    break;
                case LOOKUP_LEAD:
                    voidChecker(lookupLead(id));
                    break;
                case LOOKUP_OPPORTUNITY:
                    lookUpOpportunity(id);
                    break;
                case LOOKUP_ACCOUNT:
                    lookUpAccount(id);
                    break;
                case LOOKUP_CONTACT:
                    lookUpContact(id);
                    break;
                case CLOSE_WON:
                    updateOpportunityStatusClosedWin(id);
                    break;
                case CLOSE_LOST:
                    updateOpportunityStatusClosedLost(id);
                    break;
                default:
                    System.out.println(defaultInfo);
            }
        }
        System.out.println("--------------------------------------------------");
    }

    public static void voidChecker(Object o) {
        if (o == null) {
            System.out.println("Please try again.");
        } else {
            System.out.println(o);
        }
    }

    public static long getIdFromInput(String command) {
        String[] words = command.trim().split("\\s");

        try {
            return Long.parseLong(words[words.length - 1]);
        } catch (NumberFormatException ignored) {
        }
        return 0; // It returns 0 because you cannot create id less than 0
    }

    public static String getTextWithoutId(String text) {
        return text.substring(0, text.length() - String.valueOf(getIdFromInput(text)).length()).trim();
    }
}

