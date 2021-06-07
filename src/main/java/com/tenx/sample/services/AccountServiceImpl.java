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

import com.tenx.sample.dto.AccountDTO;
import com.tenx.sample.model.Account;
import com.tenx.sample.repositories.AccountsRepository;

/**
 * AccountServiceImpl for account CRUD operations.
 *
 * @author Partha Mondal
 */

@Service
public class AccountServiceImpl implements AccountService {

	private final AccountsRepository repository;

	@Autowired
	public AccountServiceImpl(final AccountsRepository repository) {
		this.repository = repository;
	}

	@Override
	public Optional<AccountDTO> saveAccount(AccountDTO dto) throws Exception {
		Optional<Account> account = repository.saveAccount(dto);
		return backToDto(account);
	}

	@Override
	public Optional<AccountDTO> getAccountById(Long id) throws Exception {
		Optional<Account> account = repository.getAccountById(id);
		return backToDto(account);
	}

	@Override
	public Optional<AccountDTO> deleteAccountById(Long id) throws Exception {
		Optional<Account> account = repository.deleteAccountById(id);
		return backToDto(account);
	}

	@Override
	public List<AccountDTO> getAllAccounts() throws Exception {
		List<Account> accounts = repository.getAllAccounts();

		List<AccountDTO> accountDtos = new ArrayList<>();
		for (Account acc : accounts) {
			accountDtos.add(new AccountDTO(acc.getId(), acc.getBalance(), acc.getCurrency().getCurrencyCode(), acc.getCreatedAt()));
		}
		
		Collections.reverse(accountDtos);
		return accountDtos;
	}

	// Internal private methods
	private Optional<AccountDTO> backToDto(Optional<Account> account) {
		if (account.isPresent()) {
			return Optional.of(new AccountDTO(account.get().getId(), account.get().getBalance(), 
					account.get().getCurrency().getCurrencyCode(), account.get().getCreatedAt())); 
		} else {
			return Optional.of(null);
		}
	}
}
