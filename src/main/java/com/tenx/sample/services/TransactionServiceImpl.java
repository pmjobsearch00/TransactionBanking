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
import org.springframework.stereotype.Service;

import com.tenx.sample.dto.PainDTO;
import com.tenx.sample.model.Transaction;
import com.tenx.sample.repositories.TransactionsRepository;

/**
 * TransactionServiceImpl for credit transfer and direct debit operations.
 *
 * @author Partha Mondal
 */

@Service
public class TransactionServiceImpl implements TransactionService {

	private final TransactionsRepository repository;

	@Autowired
	public TransactionServiceImpl(final TransactionsRepository repository) {
		this.repository = repository;
	}

	@Override
	public Optional<PainDTO> creditTransfer(PainDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<PainDTO> directDebit(PainDTO dto) throws Exception {
		Optional<Transaction> transaction = repository.directDebit(dto);
		return backToDto(transaction);
	}

	@Override
	public List<PainDTO> getAllTransactions() throws Exception {
		List<Transaction> transactions = repository.getAllTransactions();

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
