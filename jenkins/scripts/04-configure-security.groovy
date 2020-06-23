import jenkins.*
import jenkins.model.*
import hudson.model.*
import jenkins.model.Jenkins
import org.jenkinsci.plugins.authorizeproject.*
import org.jenkinsci.plugins.authorizeproject.strategy.*
import jenkins.security.QueueItemAuthenticatorConfiguration

def jenkins = Jenkins.getInstance()

// Define which strategies you want to allow to be set per project
def strategyMap = [
  (jenkins.getDescriptor(AnonymousAuthorizationStrategy.class).getId()): false, 
  (jenkins.getDescriptor(TriggeringUsersAuthorizationStrategy.class).getId()): true,
  (jenkins.getDescriptor(SpecificUsersAuthorizationStrategy.class).getId()): false,
  (jenkins.getDescriptor(SystemAuthorizationStrategy.class).getId()): false
]

def authenticators = QueueItemAuthenticatorConfiguration.get().getAuthenticators()
authenticators.add(new GlobalQueueItemAuthenticator(new TriggeringUsersAuthorizationStrategy()))
authenticators.add(new ProjectQueueItemAuthenticator(strategyMap))
jenkins.save()