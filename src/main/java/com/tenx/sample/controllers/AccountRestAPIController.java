/*
 * Copyright to the original author.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tenx.sample.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tenx.sample.dto.AccountDTO;
import com.tenx.sample.exceptions.AccountNotFoundException;
import com.tenx.sample.exceptions.ServiceException;
import com.tenx.sample.services.AccountService;

import io.swagger.annotations.Api;

/**
 * AccountRestAPIController for account CRUD operations.
 *
 * @author Partha Mondal
 */

@RestController
@Api(value = "account service", description = "This service offers account crud fucntions.")
public class AccountRestAPIController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountRestAPIController.class);

	private final AccountService service;

	@Autowired
	public AccountRestAPIController(final AccountService service) {
		this.service = service;
	}

	/**
	 * Create an account
	 */
	@RequestMapping(value = "/v1/accounts/createAccount", method = RequestMethod.POST)
	public ResponseEntity<?> createAccount(@RequestBody final AccountDTO dto) {

		try {
			return new ResponseEntity<>(service.saveAccount(dto), HttpStatus.OK);

		} catch (ServiceException e) {
			LOGGER.debug("++++++++++++++++++++++++++++: " + e.getMessage());
			return new ResponseEntity<>("Service unavailable! Account could not be created!", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			LOGGER.debug("++++++++++++++++++++++++++++: " + e.getMessage());
			return new ResponseEntity<>("Account could not be created!", HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * Delete an account corresponding to an ID
	 */
	@RequestMapping(value = "/v1/accounts/deleteAccountByIdService/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> deleteAccountById(@PathVariable final Long id) {

		LOGGER.debug("++++++++++++++++++++++++++++: Delete account with ID: " + id);

		try {
			return new ResponseEntity<>(service.deleteAccountById(id), HttpStatus.OK);

		} catch (AccountNotFoundException e) {
			LOGGER.debug("++++++++++++++++++++++++++++: " + e.getMessage());
			return new ResponseEntity<>("No matching account found to delete!", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			LOGGER.debug("++++++++++++++++++++++++++++: " + e.getMessage());
			return new ResponseEntity<>("Service not available!", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Retrieve an account corresponding to an ID
	 */
	@RequestMapping(value = "/v1/accounts/acountLookupByIdService/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getAccountById(@PathVariable final Long id) {

		LOGGER.debug("++++++++++++++++++++++++++++: Retrieve account with ID: " + id);

		try {
			return new ResponseEntity<>(service.getAccountById(id), HttpStatus.OK);

		} catch (AccountNotFoundException e) {
			LOGGER.debug("++++++++++++++++++++++++++++: " + e.getMessage());
			return new ResponseEntity<>("No matching account found!", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			LOGGER.debug("++++++++++++++++++++++++++++: " + e.getMessage());
			return new ResponseEntity<>("Service not available!", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Retrieve all accounts
	 */
	@RequestMapping(value = "/v1/accounts/accountLookupService", method = RequestMethod.GET)
	public ResponseEntity<?> getAllaccounts() {

		LOGGER.debug("++++++++++++++++++++++++++++: Retrieve all accounts");

		try {
			return new ResponseEntity<>(service.getAllAccounts(), HttpStatus.OK);

		} catch (AccountNotFoundException e) {
			LOGGER.debug("++++++++++++++++++++++++++++: " + e.getMessage());
			return new ResponseEntity<>("accounts not found!", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			LOGGER.debug("++++++++++++++++++++++++++++: " + e.getMessage());
			return new ResponseEntity<>("Service not available!", HttpStatus.BAD_REQUEST);
		}
	}

}
