/*
 * Copyright to the original author.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.tenx.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * InterviewApplication is the application runner for the Rest services.
 *
 * @author Partha Mondal
 */
@SpringBootApplication
public class TransactionBankApplication {

	private static final Logger log = LoggerFactory.getLogger(TransactionBankApplication.class);

	public static void main(String[] args) {
		log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		log.info("Transaction Banking Application is Starting .........");
		log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		SpringApplication.run(TransactionBankApplication.class, args);
	}
}
