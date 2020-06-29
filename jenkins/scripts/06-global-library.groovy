import org.jenkinsci.plugins.workflow.libs.GlobalLibraries
import org.jenkinsci.plugins.workflow.libs.LibraryConfiguration
import org.jenkinsci.plugins.workflow.libs.SCMSourceRetriever
import jenkins.plugins.git.GitSCMSource
import jenkins.model.*

def env = System.getenv()

def sharedLibProperties = [
        name: 'jenkins-shared-pipeline',
        repository: env.SHARED_PIPELINE_URL,
        branch: 'master',
        credentialId: env.CREDENTIAL_ID,
        implicit: true,
        allowVersionOverride: false,
]

def scmRetriever = new SCMSourceRetriever(new GitSCMSource(
        "global-shared-library",
        sharedLibProperties.repository,
        sharedLibProperties.credentialId,
        "*",
        "",
        false
))

def globalLibraries = GlobalLibraries.get()
boolean exists = false
for (LibraryConfiguration lib : globalLibraries.getLibraries()) {
    if (lib.getName().equals(sharedLibProperties.name)) {
        exists = true
    }
}

if (exists) {
    println("Global Library with name ${sharedLibProperties.name} already exists")
} else {
    def jenkins = Jenkins.get()
    def newSharedLib = new LibraryConfiguration(sharedLibProperties.name, scmRetriever)
    newSharedLib.setDefaultVersion(sharedLibProperties.branch)
    newSharedLib.setImplicit(sharedLibProperties.implicit)
    newSharedLib.setAllowVersionOverride(sharedLibProperties.allowVersionOverride)
    List<LibraryConfiguration> allSharedLibs = []
    def currentLibs = globalLibraries.getLibraries()
    if (currentLibs.size()) {
        allSharedLibs = currentLibs
    }
    allSharedLibs.add(newSharedLib)
    globalLibraries.setLibraries(allSharedLibs)
    jenkins.save()
}


println("Configuring the Global Shared Libraries End")