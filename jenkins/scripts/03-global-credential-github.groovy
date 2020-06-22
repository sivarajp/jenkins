import com.cloudbees.plugins.credentials.impl.*;
import com.cloudbees.plugins.credentials.*;
import com.cloudbees.plugins.credentials.domains.*;

def env = System.getenv()
Credentials c = (Credentials) new UsernamePasswordCredentialsImpl(CredentialsScope.GLOBAL, env.CREDENTIAL_ID, "Credential for github", env.GIT_USER, env.GIT_TOKEN)
SystemCredentialsProvider.getInstance().getStore().addCredentials(Domain.global(), c)