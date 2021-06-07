/**
 * 
 */
package com.tenx.sample.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tenx.sample.dto.AccountDTO;
import com.tenx.sample.model.Account;
import com.tenx.sample.repositories.AccountsRepository;

/**
 * @author Partha
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionRestAPIControllerTest {
	
	@Autowired
	WebApplicationContext context;

	@Autowired
	AccountsRepository repository;
	
	private MockMvc mvc;

	AccountDTO dto1 = null;
	AccountDTO dto2 = null;
	AccountDTO dto3 = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
		
		dto1 = new AccountDTO(new Long(1), new Double(10.0), "USD", new Date());
		dto2 = new AccountDTO(new Long(2), new Double(20.0), "USD", new Date());
		dto3 = new AccountDTO(new Long(3), new Double(30.0), "INR", new Date());
		
		repository.saveAccount(dto1);
		repository.saveAccount(dto2);
		repository.saveAccount(dto3);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		repository = null;
	}

	/**
	 * Test method for {@link com.tenx.sample.controllers.TransactionRestAPIController#createAccount(com.tenx.sample.dto.PainDTO)}.
	 * @throws Exception 
	 */
	@Test
	public void testDirectDebit_AC1_HappyPath() throws Exception {
		String painJson = "{\"sourceAccountId\":\"1\",\"targetAccountId\":\"2\",\"amount\":\"5.0\",\"currency\":\"USD\"}";

		mvc.perform(post("/v1/accounts/directDebitPaymentInitiationService").content(painJson).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().string(containsString("5.0")))
				.andExpect(content().string(containsString("USD")));
		
		Optional<Account> account1 = repository.getAccountById(new Long(1));
		Optional<Account> account2 = repository.getAccountById(new Long(2));
		
		Assert.assertEquals(account1.get().getBalance(), new Double(5.0));
		Assert.assertEquals(account2.get().getBalance(), new Double(25.0));		
		
	}
	
	/**
	 * Test method for {@link com.tenx.sample.controllers.TransactionRestAPIController#createAccount(com.tenx.sample.dto.PainDTO)}.
	 * @throws Exception 
	 */
	@Test
	public void testDirectDebit_AC2_InsufficientBalance() throws Exception {
		String painJson = "{\"sourceAccountId\":\"1\",\"targetAccountId\":\"2\",\"amount\":\"50.0\",\"currency\":\"USD\"}";

		mvc.perform(post("/v1/accounts/directDebitPaymentInitiationService").content(painJson).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("Sending account does not have sufficient balance for this transfer!"));
	}
	
	/**
	 * Test method for {@link com.tenx.sample.controllers.TransactionRestAPIController#createAccount(com.tenx.sample.dto.PainDTO)}.
	 * @throws Exception 
	 */
	@Test
	public void testDirectDebit_AC3_SameAccount() throws Exception {
		String painJson = "{\"sourceAccountId\":\"1\",\"targetAccountId\":\"1\",\"amount\":\"5.0\",\"currency\":\"USD\"}";

		mvc.perform(post("/v1/accounts/directDebitPaymentInitiationService").content(painJson).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("Both the sending and receiving accounts are same."));
	}
	
	/**
	 * Test method for {@link com.tenx.sample.controllers.TransactionRestAPIController#createAccount(com.tenx.sample.dto.PainDTO)}.
	 * @throws Exception 
	 */
	@Test
	public void testDirectDebit_AC4_AccountDoesNotExists() throws Exception {
		String painJson = "{\"sourceAccountId\":\"500\",\"targetAccountId\":\"1\",\"amount\":\"5.0\",\"currency\":\"USD\"}";

		mvc.perform(post("/v1/accounts/directDebitPaymentInitiationService").content(painJson).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("One or more accounts do not exist!"));
	}

}
