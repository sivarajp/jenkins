import jenkins.model.Jenkins
import org.csanchez.jenkins.plugins.kubernetes.KubernetesCloud
import org.csanchez.jenkins.plugins.kubernetes.PodTemplate
import org.csanchez.jenkins.plugins.kubernetes.ContainerEnvVar
import org.csanchez.jenkins.plugins.kubernetes.ContainerTemplate

def env = System.getenv()

def jenkins = Jenkins.getInstance()

def cloud = new KubernetesCloud('kubernetes')
cloud.setMaxRequestsPerHost(KubernetesCloud.DEFAULT_MAX_REQUESTS_PER_HOST)
cloud.serverUrl = 'https://kubernetes.default.svc.cluster.local'
cloud.jenkinsUrl = "http://jenkins:8080"
cloud.jenkinsTunnel = "jenkins:50000"
jenkins.clouds.clear()
jenkins.clouds.add(cloud)