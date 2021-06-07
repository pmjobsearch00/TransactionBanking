/*
 * Copyright to the original author.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tenx.sample.exceptions;

public class AccountNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 2967357473314163159L;

    public AccountNotFoundException() {
        super();
    }

    public AccountNotFoundException(final String message) {
        super(message);
    }

    public AccountNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
