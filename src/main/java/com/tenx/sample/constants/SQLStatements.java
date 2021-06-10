/*
 * Copyright to the original author.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tenx.sample.constants;

public class SQLStatements {

	public static final String SQL_FIND_ACCOUNT = "select * from accounts where id = ?";
	public static final String SQL_DELETE_PERSON = "delete from accounts where id = ?";
	public static final String SQL_GET_ALL = "select * from accounts";
	public static final String SQL_INSERT_ACCOUNTS = "insert into accounts(balance, currency, created_At) values(?,?,?)";
	
	public static final String SQL_FIND_TRANSACTION = "select * from transactions where id = ?";
	
	public static final String SQL_UPDATE_AMOUNT = "update accounts set balance = ? where id = ?";
	
	public static final String SQL_INSERT_TRANSACTION = "insert into transactions(source_account_id, target_account_id, amount, currency) values(?,?,?,?)";
	public static final String SQL_GET_ALL_TRANSACTION = "select * from transactions";

}
