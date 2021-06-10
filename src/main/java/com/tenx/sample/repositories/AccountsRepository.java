/*
 * Copyright to the original author.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tenx.sample.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tenx.sample.constants.SQLStatements;
import com.tenx.sample.dto.AccountDTO;
import com.tenx.sample.exceptions.AccountNotFoundException;
import com.tenx.sample.exceptions.ServiceException;
import com.tenx.sample.model.Account;
import com.tenx.sample.utility.AccountRowMapper;

/**
 * AccountsRepository for account CRUD operations.
 *
 * @author Partha Mondal
 */
@Repository
public class AccountsRepository {

	private static final Logger log = LoggerFactory.getLogger(AccountsRepository.class);

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public AccountsRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional // More details is here https://spring.io/guides/gs/managing-transactions/
	public Optional<Account> saveAccount(AccountDTO dto) {

		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = con.prepareStatement(SQLStatements.SQL_INSERT_ACCOUNTS,
						Statement.RETURN_GENERATED_KEYS);
				statement.setDouble(1, dto.getBalance());
				statement.setString(2, dto.getCurrency());
				statement.setDate(3, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
				return statement;
			}
		}, holder);

		long key = holder.getKey().longValue();

		Optional<Account> account = getAccountById(new Long(key));

		if (account.isPresent()) {
			// fetch an individual account by ID
			log.info("account was created with saveAccount(AccountDTO)");
			log.info("--------------------------------");
			log.info(account.get().toString());
			log.info("");
		} else {
			log.info("account could not be created with saveAccount(AccountDTO)");
			log.info("--------------------------------");
			log.info("");
			throw new ServiceException("account could not be created.");
		}

		return account;

	}

	@Transactional
	public Optional<Account> getAccountById(Long id) {

		Account account = jdbcTemplate.queryForObject(SQLStatements.SQL_FIND_ACCOUNT, new AccountRowMapper(),
				new Object[] { id });

		if (null != account) {
			// log an individual account by ID
			log.info("account found with getAccountById(id)");
			log.info("--------------------------------");
			log.info(account.toString());
			log.info("");
			return Optional.of(account);
		} else {
			log.info("account could not be found getAccountById(id)");
			log.info("--------------------------------");
			log.info("");
			throw new AccountNotFoundException("account could not be found.");
		}
	}
	
	@Transactional
	public boolean updateAccountBalance(Double balance, Long id) {
		
		int result = jdbcTemplate.update(SQLStatements.SQL_UPDATE_AMOUNT, balance, id);

		if (result > 0) {
			log.info("account balance updated with updateAccountBalance(balance, id)");
			log.info("--------------------------------");
			log.info(String.valueOf(id));
			log.info("");
			return true;
		} else {
			log.info("account balance couldn't be updated with updateAccountBalance(balance, id)");
			log.info("--------------------------------");
			log.info(String.valueOf(id));
			return false;
		}
	}

	@Transactional
	public List<Account> getAllAccounts() {

		List<Account> accounts = jdbcTemplate.query(SQLStatements.SQL_GET_ALL, new AccountRowMapper());

		if ((null != accounts) && (accounts.size() > 0)) {
			// fetch all accounts
			log.info("accounts found with getAllAccounts()");
			log.info("-------------------------------");
			for (Account acc : accounts) {
				log.info(acc.toString());
			}
			log.info("");
		} else {
			log.info("accounts could not be found with getAllAccounts()");
			log.info("--------------------------------");
			log.info("");
		}

		return accounts;
	}

	@Transactional
	public Optional<Account> deleteAccountById(Long id) {
		Optional<Account> account = getAccountById(id);
		boolean status = jdbcTemplate.update(SQLStatements.SQL_DELETE_PERSON, id) > 0;

		if (status) {
			// fetch an individual account by ID
			log.info("account was deleted with deleteAccountById(id)");
			log.info("--------------------------------");
			log.info(account.get().toString());
			log.info("");
			return account;
		} else {
			log.info("account could not be deleted with deleteAccountById(id)");
			log.info("--------------------------------");
			log.info("");
			throw new AccountNotFoundException("account could not be found to be deleted.");
		}

	}

}