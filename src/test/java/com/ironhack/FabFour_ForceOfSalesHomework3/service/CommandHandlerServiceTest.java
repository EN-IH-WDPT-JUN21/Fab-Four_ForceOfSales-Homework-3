package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.LeadObject;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.TextColor;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.AccountRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.ContactRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.LeadObjectRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.SalesRepRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static com.ironhack.FabFour_ForceOfSalesHomework3.service.CommandHandlerService.handleCommand;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.InputOutputService.colorMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CommandHandlerServiceTest {

}
