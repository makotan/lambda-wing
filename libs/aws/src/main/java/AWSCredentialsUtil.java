import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProviderChain;

/**
 * User: makotan
 * Date: 2015/11/07
 */
public class AWSCredentialsUtil {
    AWSCredentialsProviderChain providerChain;
    long refresh = 0;
    final int refreshTime;
    public AWSCredentialsUtil() {
        this(1000);
    }
    public AWSCredentialsUtil(int refreshTime) {
        providerChain = new AWSCredentialsProviderChain();
        this.refreshTime = refreshTime;
    }

    public AWSCredentials get() {
        if (refresh < System.currentTimeMillis()) {
            providerChain.refresh();
            refresh = System.currentTimeMillis() + refreshTime;
        }
        return providerChain.getCredentials();
    }
}
