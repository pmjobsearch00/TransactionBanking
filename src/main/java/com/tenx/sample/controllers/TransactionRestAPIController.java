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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tenx.sample.dto.PainDTO;
import com.tenx.sample.exceptions.ServiceException;
import com.tenx.sample.exceptions.TransactionNotFoundException;
import com.tenx.sample.services.TransactionService;

import io.swagger.annotations.Api;

/**
 * TransactionRestAPIController for for credit transfer and direct debit operations.
 *
 * @author Partha Mondal
 */

@RestController
@Api(value = "transaction service", description = "This service offers direct debit and credit transfer fucntions. "
		+ "e.g. initiation, cancellation etc")
public class TransactionRestAPIController {
	

	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionRestAPIController.class);

	private final TransactionService service;

	@Autowired
	public TransactionRestAPIController(final TransactionService service) {
		this.service = service;
	}


	/**
	 * Direct Debit Payment Initiation - PAIN001
	 */
	@RequestMapping(value = "/v1/accounts/directDebitPaymentInitiationService", method = RequestMethod.POST)
	public ResponseEntity<?> directDebit(@RequestBody final PainDTO dto) {

		try {

			return new ResponseEntity<>(service.directDebit(dto), HttpStatus.OK);

		} catch (TransactionNotFoundException e) {
			LOGGER.debug("++++++++++++++++++++++++++++: " + e.getMessage());
			return new ResponseEntity<>("Direct debit could not be completed!", HttpStatus.BAD_REQUEST);
		} catch (ServiceException e) {
			LOGGER.debug("++++++++++++++++++++++++++++: " + e.getService());
			return new ResponseEntity<>(e.getService(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			LOGGER.debug("++++++++++++++++++++++++++++: " + e.getMessage());
			return new ResponseEntity<>("Direct debit could not be initiated!", HttpStatus.BAD_REQUEST);
		}

	}
}
