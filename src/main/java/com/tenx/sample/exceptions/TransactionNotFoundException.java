/*
 * Copyright to the original author.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tenx.sample.exceptions;

public class TransactionNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TransactionNotFoundException() {
		super();
	}

	public TransactionNotFoundException(final String message) {
		super(message);
	}

	public TransactionNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
