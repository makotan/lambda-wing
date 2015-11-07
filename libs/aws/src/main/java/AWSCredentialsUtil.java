import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.makotan.libs.lambda.wing.core.util.ConfigLoader;

/**
 * User: makotan
 * Date: 2015/11/07
 */
public class AWSCredentialsUtil {
    static AWSCredentialsUtil credentialsUtil = new AWSCredentialsUtil();
    
    AWSCredentialsProviderChain providerChain;
    long refresh = 0;
    
    final int refreshTime;
    ConfigLoader configLoader = new ConfigLoader();

    public AWSCredentialsUtil() {
        this(10000);
    }

    public AWSCredentialsUtil(int refreshTime) {
        providerChain = new AWSCredentialsProviderChain();
        this.refreshTime = refreshTime;
    }

    AWSCredentials get_() {
        if (refresh < System.currentTimeMillis() || configLoader.isUpdateVersion()) {
            providerChain.refresh();
            refresh = System.currentTimeMillis() + refreshTime;
        }
        return providerChain.getCredentials();
    }

    public static AWSCredentials get() {
        return credentialsUtil.get_();
    }
}
