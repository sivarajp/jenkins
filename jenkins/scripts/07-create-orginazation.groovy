import jenkins.branch.OrganizationFolder
import jenkins.model.*
import org.jenkinsci.plugins.github_branch_source.GitHubSCMNavigator
import org.jenkinsci.plugins.github_branch_source.BranchDiscoveryTrait
import org.jenkinsci.plugins.github_branch_source.GitHubSCMSource
import org.jenkinsci.plugins.workflow.libs.FolderLibraries
import org.jenkinsci.plugins.workflow.libs.LibraryConfiguration
import org.jenkinsci.plugins.workflow.libs.SCMSourceRetriever
import org.jenkinsci.plugins.github_branch_source.OriginPullRequestDiscoveryTrait

def env = System.getenv()

if (!env.ORG_FOLDER_NAME) {
    println("ORG_NAME must be defined, skipping seed job creation!")
    return
}

if (!Jenkins.instance.getItem(env.ORG_FOLDER_NAME)) {
    try {
        def job = Jenkins.instance.createProject(OrganizationFolder.class, env.ORG_FOLDER_NAME)
        def navigator = new GitHubSCMNavigator(env.GIT_ORG)
        navigator.credentialsId =  env.CREDENTIAL_ID 
        navigator.traits = [
            new BranchDiscoveryTrait(1), // Exclude branches that are also filed as PRs.
            new OriginPullRequestDiscoveryTrait(1), // Merging the pull request with the current target branch revision.
            new SubmoduleOptionTrait(new SubmoduleOption(false, true, false, null, null, true)),
        ]
        job.getNavigators().replace(navigator)
        job.scheduleBuild(10)
    } catch (ex) {
        println "ERROR: ${ex}"
    }
}