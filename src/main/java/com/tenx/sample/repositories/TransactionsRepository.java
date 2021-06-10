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
import com.tenx.sample.dto.PainDTO;
import com.tenx.sample.exceptions.ServiceException;
import com.tenx.sample.exceptions.TransactionNotFoundException;
import com.tenx.sample.model.Transaction;
import com.tenx.sample.utility.TransactionRowMapper;

/**
 * TransactionsRepository for credit transfer and direct debit operations.
 *
 * @author Partha Mondal
 */

@Repository
public class TransactionsRepository {

	private static final Logger log = LoggerFactory.getLogger(TransactionsRepository.class);

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public TransactionsRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional // More details is here https://spring.io/guides/gs/managing-transactions/
	public Optional<Transaction> saveTransaction(PainDTO dto) {

		// Finally insert into transactions table
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = con.prepareStatement(SQLStatements.SQL_INSERT_TRANSACTION,
						Statement.RETURN_GENERATED_KEYS);
				statement.setLong(1, dto.getSourceAccountId());
				statement.setLong(2, dto.getTargetAccountId());
				statement.setDouble(3, dto.getAmount());
				statement.setString(4, dto.getCurrency());
				return statement;
			}
		}, holder);

		long key = holder.getKey().longValue();

		Optional<Transaction> transaction = getTransactionById(new Long(key));

		if (transaction.isPresent()) {
			// log an individual transaction by ID
			log.info("transaction was recorded with directDebit(PainDTO)");
			log.info("--------------------------------");
			log.info(transaction.get().toString());
			log.info("");
		} else {
			log.info("transaction could not be recorded with directDebit(PainDTO)");
			log.info("--------------------------------");
			log.info("");
			throw new ServiceException("transaction could not be recorded.");
		}

		return transaction;

	}

	@Transactional
	public Optional<Transaction> getTransactionById(Long id) {

		Transaction transaction = jdbcTemplate.queryForObject(SQLStatements.SQL_FIND_TRANSACTION,
				new TransactionRowMapper(), new Object[] { id });

		if (null != transaction) {
			// fetch an individual account by ID
			log.info("transaction found with getTransactionById(id)");
			log.info("--------------------------------");
			log.info(transaction.toString());
			log.info("");
			return Optional.of(transaction);
		} else {
			log.info("transaction could not be found getTransactionById(id)");
			log.info("--------------------------------");
			log.info("");
			throw new TransactionNotFoundException("transaction could not be retrieved.");
		}
	}
	
	@Transactional
	public List<Transaction> getAllTransactions() {

		List<Transaction> transactions = jdbcTemplate.query(SQLStatements.SQL_GET_ALL_TRANSACTION, new TransactionRowMapper());

		if ((null != transactions) && (transactions.size() > 0)) {
			// fetch all accounts
			log.info("transactions found with getAllTransactions()");
			log.info("-------------------------------");
			for (Transaction trs : transactions) {
				log.info(trs.toString());
			}
			log.info("");
		} else {
			log.info("transactions could not be found with getAllTransactions()");
			log.info("--------------------------------");
			log.info("");
		}

		return transactions;
	}
}
