/*
 * Copyright to the original author.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tenx.sample.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tenx.sample.dto.AccountDTO;
import com.tenx.sample.dto.PainDTO;
import com.tenx.sample.exceptions.AccountNotFoundException;
import com.tenx.sample.exceptions.ServiceException;
import com.tenx.sample.exceptions.TransactionNotFoundException;
import com.tenx.sample.services.AccountService;
import com.tenx.sample.services.TransactionService;

/**
 * WebMVCController is the controller for the Web GUI
 *
 * @author Partha Mondal
 */
@Controller
public class WebMVCController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebMVCController.class);

	private final AccountService accountService;
	private final TransactionService transactionService;

	@Autowired
	public WebMVCController(final AccountService accountService, final TransactionService transactionService) {
		this.accountService = accountService;
		this.transactionService = transactionService;
	}

	/**
	 * Landing home page
	 */
	@RequestMapping("/")
	public String home(Model model) {
		try {
			model.addAttribute("accountDTO", new AccountDTO());
			model.addAttribute("painDTO", new PainDTO());

			List<AccountDTO> accounts = accountService.getAllAccounts(); 
			List<PainDTO> transactions = transactionService.getAllTransactions(); 
			
			if (!accounts.isEmpty()) {
				model.addAttribute("accountList", accounts);
			} else {
				model.addAttribute("errorMsg0", "No accounts found; open an account first.");
			}
			
			if (!transactions.isEmpty()) {
				model.addAttribute("transactionList", transactions);
			} else {
				model.addAttribute("errorMsg5", "No transaction found.");
			}
			
		} catch (Exception es) {
			LOGGER.debug("++++++++++++++++++++++++++++: " + es.getMessage());
			model.addAttribute("errorMsg0", es.getMessage());
		}

		return "home";
	}

	/**
	 * create a new account
	 */

	@RequestMapping(value = "/accountCreation", method = RequestMethod.POST)
	public String accountCreation(AccountDTO dto, BindingResult result, Model model) {

		try {
			model.addAttribute("accountDTO", new AccountDTO());
			model.addAttribute("painDTO", new PainDTO());

			if ((null == result) || (null == dto) || (result.hasErrors())) {
				model.addAttribute("errorMsg", "Account could not be created!");
				return "home";
			} else if ((null == dto.getCurrency()) || (dto.getCurrency().trim().length() < 3)
					|| (dto.getCurrency().trim().length() > 3)) {
				model.addAttribute("errorMsg", "Valid Currency is mandatory e.g. GBP, USD, EUR, INR!");
				return "home";
			} else if (dto.getBalance() < 0) {
				model.addAttribute("errorMsg", "Account balance must not be negative!");
				return "home";
			}

			accountService.saveAccount(dto);
			model.addAttribute("successMsg", "Account created: ");

		} catch (ServiceException ei) {
			LOGGER.debug("++++++++++++++++++++++++++++: " + ei.getService());
			model.addAttribute("errorMsg", ei.getService());
		} catch (AccountNotFoundException ea) {
			LOGGER.debug("++++++++++++++++++++++++++++: " + ea.getMessage());
			model.addAttribute("errorMsg", ea.getMessage());
		}  catch (Exception es) {
			LOGGER.debug("++++++++++++++++++++++++++++: " + es.getMessage());
			model.addAttribute("errorMsg", es.getMessage());
		}

		return "home";
	}

	/**
	 * direct debit payment
	 */

	@RequestMapping(value = "/directDebitInitiate", method = RequestMethod.POST)
	public String directDebitInitiate(PainDTO dto, BindingResult result, Model model) {

		try {
			model.addAttribute("accountDTO", new AccountDTO());
			model.addAttribute("painDTO", new PainDTO());

			if ((null == result) || (null == dto) || (result.hasErrors())) {
				model.addAttribute("errorMsg1", "Account could not be created!");
				return "home";
			} else if ((null == dto.getCurrency()) || (dto.getCurrency().trim().length() < 3)
					|| (dto.getCurrency().trim().length() > 3)) {
				model.addAttribute("errorMsg1", "Valid Currency is mandatory e.g. GBP, USD, EUR, INR!");
				return "home";
			} else if (dto.getAmount() < 0) {
				model.addAttribute("errorMsg1", "Transfer amount must not be negative!");
				return "home";
			} else if (dto.getSourceAccountId() < 0) {
				model.addAttribute("errorMsg1", "Source account number must not be negative!");
				return "home";
			} else if (dto.getTargetAccountId() < 0) {
				model.addAttribute("errorMsg1", "Target account number must not be negative!");
				return "home";
			}

			transactionService.directDebit(dto);
			model.addAttribute("successMsg1", "Credit transfer request is completed: ");

		} catch (ServiceException ei) {
			LOGGER.debug("++++++++++++++++++++++++++++: " + ei.getService());
			model.addAttribute("errorMsg1", ei.getService());
		} catch (TransactionNotFoundException et) {
			LOGGER.debug("++++++++++++++++++++++++++++: " + et.getMessage());
			model.addAttribute("errorMsg1", et.getMessage());
		} catch (AccountNotFoundException ea) {
			LOGGER.debug("++++++++++++++++++++++++++++: " + ea.getMessage());
			model.addAttribute("errorMsg1", ea.getMessage());
		} catch (Exception es) {
			LOGGER.debug("++++++++++++++++++++++++++++: " + es.getMessage());
			model.addAttribute("errorMsg1", es.getMessage());
		}

		return "home";
	}

}
