package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Command;
import org.springframework.stereotype.Service;

import static com.ironhack.FabFour_ForceOfSalesHomework3.service.AccountService.lookUpAccount;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.AccountService.showAccounts;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.InputOutputService.*;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.LeadObjectService.*;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.OpportunityService.*;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.SalesRepService.*;

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
                case SHOW_ACCOUNTS:
                    showAccounts();
                    break;
                case EXPORT_LEADS:
                    exportLeadInformation();
                    break;
                case HELP:
                    System.out.println(
                            "Type one of below statement to execute:\n\n" +
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

                                    "\nEXPORT SOME DATA\n" +
                                    " > export leads - to export all current leads\n" +
                                    " > export opportunities {id} - to export all current opportunities\n" +
                                    " > export accounts {id} - to export all accounts\n" +

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
                case EXPORT_OPPORTUNITIES:
                    exportOppInformation();
                    break;
                case EXPORT_ACCOUNTS:
                    exportAccountInformation();
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
        }
        else {
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

