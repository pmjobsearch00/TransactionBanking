/*
 * Copyright to the original author.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tenx.sample.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenx.sample.dto.PainDTO;
import com.tenx.sample.exceptions.AccountNotFoundException;
import com.tenx.sample.exceptions.ServiceException;
import com.tenx.sample.model.Account;
import com.tenx.sample.model.Transaction;
import com.tenx.sample.repositories.AccountsRepository;
import com.tenx.sample.repositories.TransactionsRepository;

/**
 * TransactionServiceImpl for credit transfer and direct debit operations.
 *
 * @author Partha Mondal
 */

@Service
public class TransactionServiceImpl implements TransactionService {

	private final TransactionsRepository transactionsRepository;
	private final AccountsRepository accountsRepository;

	@Autowired
	public TransactionServiceImpl(final AccountsRepository accountsRepository,
			final TransactionsRepository transactionsRepository) {
		this.accountsRepository = accountsRepository;
		this.transactionsRepository = transactionsRepository;
	}

	@Override
	public Optional<PainDTO> directDebit(PainDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional 
	public Optional<PainDTO> creditTransfer(PainDTO dto) throws Exception {

		try {
			// Source account validation

			Optional<Account> sourceAccount = accountsRepository.getAccountById(dto.getSourceAccountId());

			if (!sourceAccount.isPresent()) {
				throw new AccountNotFoundException("Sending account could not be found.");
			} else if (!sourceAccount.get().getCurrency().getCurrencyCode().equalsIgnoreCase(dto.getCurrency())) {
				throw new ServiceException("Selected currency does not match with existing sending account currency.");
			} else if ((sourceAccount.get().getBalance() - dto.getAmount()) < 0) {
				throw new ServiceException("Sending account does not have sufficient balance for this transfer!");
			}

			// Target account validation
			Optional<Account> targetAccount = accountsRepository.getAccountById(dto.getTargetAccountId());

			if (!targetAccount.isPresent()) {
				throw new AccountNotFoundException("Receiving account could not be found.");
			} else if (!targetAccount.get().getCurrency().getCurrencyCode().equalsIgnoreCase(dto.getCurrency())) {
				throw new ServiceException(
						"Selected account currency does not match with existing Receiving account currency.");
			} else if (targetAccount.get().getId().equals(sourceAccount.get().getId())) {
				throw new ServiceException("Both the sending and receiving accounts are same.");
			}

			// Update balance for the source account
			accountsRepository.updateAccountBalance((sourceAccount.get().getBalance() - dto.getAmount()),
					sourceAccount.get().getId());

			// Update balance for the target account
			accountsRepository.updateAccountBalance((targetAccount.get().getBalance() + dto.getAmount()),
					targetAccount.get().getId());

		} catch (EmptyResultDataAccessException e) {
			throw new ServiceException("One or more accounts do not exist!");
		}

		// Create the transaction record.
		Optional<Transaction> transaction = transactionsRepository.saveTransaction(dto);
		return backToDto(transaction);
	}

	@Override
	public List<PainDTO> getAllTransactions() throws Exception {
		List<Transaction> transactions = transactionsRepository.getAllTransactions();

		List<PainDTO> transactionsDtos = new ArrayList<>();
		for (Transaction trs : transactions) {
			transactionsDtos.add(new PainDTO(trs.getId(), trs.getSourceAccountId(), trs.getTargetAccountId(),
					trs.getAmount(), trs.getCurrency().getCurrencyCode()));
		}

		Collections.reverse(transactionsDtos);
		return transactionsDtos;
	}

	// Internal private methods
	private Optional<PainDTO> backToDto(Optional<Transaction> transaction) {
		if (transaction.isPresent()) {
			return Optional.of(new PainDTO(transaction.get().getId(), transaction.get().getSourceAccountId(),
					transaction.get().getTargetAccountId(), transaction.get().getAmount(),
					transaction.get().getCurrency().getCurrencyCode()));
		} else {
			return Optional.of(null);
		}
	}
}
