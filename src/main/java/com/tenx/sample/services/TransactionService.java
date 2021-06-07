/*
 * Copyright to the original author.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tenx.sample.services;

import java.util.List;
import java.util.Optional;

import com.tenx.sample.dto.PainDTO;

public interface TransactionService {
	
	Optional<PainDTO> creditTransfer (PainDTO dto) throws Exception;
	Optional<PainDTO> directDebit (PainDTO dto) throws Exception;
	
	List<PainDTO> getAllTransactions() throws Exception;

}
