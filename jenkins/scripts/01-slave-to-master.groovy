import jenkins.security.s2m.AdminWhitelistRule
import jenkins.model.Jenkins

def jenkins = Jenkins.getInstance()

jenkins.getInjector().getInstance(AdminWhitelistRule.class).setMasterKillSwitch(false)