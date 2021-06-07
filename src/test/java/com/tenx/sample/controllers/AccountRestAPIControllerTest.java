/**
 * 
 */
package com.tenx.sample.controllers;

import java.util.Date;

import org.junit.After;
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
import com.tenx.sample.repositories.AccountsRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * @author Partha
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountRestAPIControllerTest {
	
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
	 * Test method for {@link com.tenx.sample.controllers.AccountRestAPIController#createAccount(com.tenx.sample.dto.AccountDTO)}.
	 */
	@Test
	public void testCreateAccountWithValidDto() throws Exception {
		String accountJson = "{\"currency\":\"USD\",\"balance\":\"40.0\"}";

		mvc.perform(post("/v1/accounts/createAccount").content(accountJson).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().string(containsString("40.0")))
				.andExpect(content().string(containsString("USD")));
	}

	/**
	 * Test method for {@link com.tenx.sample.controllers.AccountRestAPIController#createAccount(com.tenx.sample.dto.AccountDTO)}.
	 */
	@Test
	public void testCreateAccountWithInValidDto() throws Exception {
		String accountJson = "{\"currency\":\"INVALID\",\"balance\":\"40.0\"}";

		mvc.perform(post("/v1/accounts/createAccount").content(accountJson).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
	
	/**
	 * Test method for {@link com.tenx.sample.controllers.AccountRestAPIController#deleteAccountById(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public void testDeleteAccountByIdWithValidId() throws Exception {
		mvc.perform(get("/v1/accounts/deleteAccountByIdService/3")
			.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
		   ;
	}
	
	/**
	 * Test method for {@link com.tenx.sample.controllers.AccountRestAPIController#deleteAccountById(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public void testDeleteAccountByIdWithInValidId() throws Exception {
		mvc.perform(get("/v1/accounts/deleteAccountByIdService/500")
			.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
		   ;
	}

	/**
	 * Test method for {@link com.tenx.sample.controllers.AccountRestAPIController#getAccountById(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public void testGetAccountByValidId() throws Exception {
		
        mvc.perform(get("/v1/accounts/acountLookupByIdService/1")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().string(containsString("USD")));
	}

	/**
	 * Test method for {@link com.tenx.sample.controllers.AccountRestAPIController#getAccountById(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public void testGetAccountByInvalidId() throws Exception {
		
        mvc.perform(get("/v1/accounts/acountLookupByIdService/500")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}

	
	/**
	 * Test method for {@link com.tenx.sample.controllers.AccountRestAPIController#getAllaccounts()}.
	 * @throws Exception 
	 */
	@Test
	public void testGetAllaccounts() throws Exception {
        mvc.perform(get("/v1/accounts/accountLookupService")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().string(containsString("10.0")))
				.andExpect(content().string(containsString("USD")))
				.andExpect(content().string(containsString("20.0")))
				.andExpect(content().string(containsString("USD")))
				.andExpect(content().string(containsString("30.0")))
				.andExpect(content().string(containsString("INR")));
	}

}
