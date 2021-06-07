/*
 * Copyright to the original author.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tenx.sample.services;

import java.util.List;
import java.util.Optional;

import com.tenx.sample.dto.AccountDTO;

/**
 * AccountService for account CRUD operations.
 *
 * @author Partha Mondal
 */
public interface AccountService {
	
	Optional<AccountDTO> saveAccount(AccountDTO account) throws Exception;

	Optional<AccountDTO> getAccountById(Long id) throws Exception;

	Optional<AccountDTO> deleteAccountById(Long id) throws Exception;
    
    List<AccountDTO> getAllAccounts() throws Exception;

}
