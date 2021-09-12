package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.*;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Industry;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Product;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.TextColor;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.AccountRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.LeadObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;

import static com.ironhack.FabFour_ForceOfSalesHomework3.service.AccountService.*;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.DataValidatorService.*;

@Service
public class InputOutputService {

    private static LeadObjectRepository leadObjectRepository;
    private static AccountRepository accountRepository;

    @Autowired
    public InputOutputService(LeadObjectRepository leadObjectRepository,
                              AccountRepository accountRepository) {
        this.leadObjectRepository = leadObjectRepository;
        this.accountRepository = accountRepository;
    }

    // Export Lead Information to CSV
    public static void exportLeadInformation() {
        Scanner aScanner = new Scanner(System.in);
        System.out.println("What would you like to name your file?");
        String fileName = aScanner.nextLine().replaceAll("\\s+", "_");
        BufferedWriter csvWriter = null;
        try {
            csvWriter = new BufferedWriter(new FileWriter(fileName + ".txt"));
            csvWriter.write("Id,Contact Name,Phone Number,Email,Company Name \n");
            for(LeadObject leadObject : leadObjectRepository.findAll()) {
                csvWriter.write(leadObject.getId() + "," + leadObject.getContactName() + "," + leadObject.getPhoneNumber() + "," + leadObject.getEmail() + "," + leadObject.getCompanyName() + "\n");
            }
        }
        catch (Exception e) { System.out.println("Error: " + e); }
        finally {
            try { csvWriter.flush(); }
            catch (Exception e) { System.out.println("Error: " + e); }
        }
    }

    // Export Account Information to CSV
    public static void exportAccountInformation() {
        Scanner aScanner = new Scanner(System.in);
        System.out.println("What would you like to name your file?");
        String fileName = aScanner.nextLine().replaceAll("\\s+", "_");
        BufferedWriter csvWriter;
        try {
            csvWriter = new BufferedWriter(new FileWriter(fileName + ".txt"));
            csvWriter.write("Id,Industry,Employee Count,City,Country,Number of Opportunities \n");
            for(Account account : accountRepository.findAll()) {
                csvWriter.write(account.getId() + "," + account.getIndustry() + "," + account.getEmployeeCount() + "," + account.getCity() + "," + account.getCountry() + "," + account.getOpportunityList().size() + "\n");
            }
            csvWriter.close();
        }
        catch (Exception e) { System.out.println("Error: " + e); }
    }

    // Export Opportunities Information to CSV
    public static void exportOppInformation() {
        Scanner aScanner = new Scanner(System.in);
        System.out.println("What would you like to name your file?");
        String fileName = aScanner.nextLine().replaceAll("\\s+", "_");
        BufferedWriter csvWriter;
        try {
            csvWriter = new BufferedWriter(new FileWriter(fileName + ".txt"));
            csvWriter.write("Company ID,Company Name,Id,Product,Quantity,Decision Maker,Status \n");
            for(Account account : accountRepository.findAll()) {
                for(Opportunity opp : account.getOpportunityList()) {
                    csvWriter.write(account.getId() + "," + account.getContactList().get(0).getCompanyName() + "," + opp.getId() + "," + opp.getProduct() + "," + opp.getQuantity() + "," + opp.getDecisionMaker().getContactName() + "," + opp.getStatus() + "\n");
                }
            }
            csvWriter.close();
        }
        catch (Exception e) { System.out.println("Error: " + e); }
    }

    //Obtain and process user input
    public static Object getUserInput(String inputType) {
        Scanner aScanner = new Scanner(System.in);
        String userInput;
        Object result;
        while (aScanner.hasNextLine()) {
            try {
                userInput = aScanner.nextLine();
                /* user input will be validated based on the input type
                *  getUserInput() method will keep asking for the proper input
                */
                result = validateInput(userInput, inputType);
                if (result != null) {
                    return result;
                } else {
                    colorMessage("Please provide the correct value.", TextColor.RED);
                }
            } catch(Exception e){ System.out.println("Exception is: " + e); }
            }
        return null;
    }

    //Validate user input
    public static Object validateInput(String userInput, String inputType) {
        try {
            if (inputType.equals("product") && !isEmpty(userInput) && Product.getProduct(userInput) != null) {
                return Product.getProduct(userInput);
            } else if ("industry".equals(inputType) && !isEmpty(userInput) && Industry.getIndustry(userInput) != null) {
                return Industry.getIndustry(userInput);
            } else if ("quantity".equals(inputType) && !isEmpty(userInput) && isInteger(userInput) && Integer.parseInt(userInput) > 0 && Integer.parseInt(userInput) <= 300) {
                return Integer.parseInt(userInput);
            } else if ("employees".equals(inputType) && !isEmpty(userInput) && isInteger(userInput) && Integer.parseInt(userInput) > 0 && Integer.parseInt(userInput) <= 30000000) {
                return Integer.parseInt(userInput);
            } else if ("city".equals(inputType) && !isEmpty(userInput) && containsOnlyLetters(userInput)) {
                return userInput;
            } else if ("country".equals(inputType) && !isEmpty(userInput) && validateCountryName(userInput) && containsOnlyLetters(userInput)) {
                return userInput;
            } else if ("country".equals(inputType) && !isEmpty(userInput) && "show countries".equals(userInput)) {
                return showCountryList();
            } else if ("account".equals(inputType) && ("y".equals(userInput) || "Y".equals(userInput))) {
                return getAccountData();
            } else if ("account".equals(inputType) && ("n".equals(userInput) || "N".equals(userInput))) {
                return getAccountId();
            } else if ("accountId".equals(inputType) && !isLong(userInput) && !"go back".equals(userInput)) {
                colorMessage("Incorrect id format provided.", TextColor.RED);
                return "bad input";
            } else if ("accountId".equals(inputType) && !isEmpty(userInput) && "go back".equals(userInput)) {
                return userInput;
            } else if ("accountId".equals(inputType) && !isEmpty(userInput) && isLong(userInput) && accountExists(userInput)) {
                return userInput;
            } else if ("accountId".equals(inputType) && !isEmpty(userInput) && isLong(userInput) && !accountExists(userInput)) {
                colorMessage("Account with ID " + userInput + " doesn't exist.", TextColor.RED);
                return "no account";
            }
        } catch (Exception e) { System.out.println("Something went wrong."); }
        return null;
    }

    // Checks if the input String can be parsed into Integer
    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Checks if the input String can be parsed into Long
    public static boolean isLong(String input) {
        try {
            Long.parseLong(input);
            return true;
        }
        catch(NumberFormatException e ) {
            return false;
        }
    }

    // Change the color of System.output messages
    public static String colorMessage(String message, TextColor textColor) {
        String resetCode = "\033[0m";   //resets color to the primary one
        System.out.println(textColor.color + message + resetCode);
        return message;
    }
}

