Please do the following

1. Create Jenkins namespace

```sh
kubectl create ns jenkins
```
2. Create secret for jenkins admin user & github user pass

```sh
2. kubectl create secret generic jenkins-secret -n jenkins --from-literal=JENKINS_USER=admin --from-literal=JENKINS_PASS=admin --from-literal=GIT_USER= --from-literal=GIT_TOKEN=  
```

3. Create Configmap for pipeline workflow library

```sh
kubectl create cm jenkins-cm -n jenkins --from-literal=CREDENTIAL_ID=github-credentials  --from-literal=ORG_FOLDER_NAME=kube-ci  --from-literal=GIT_ORG= --from-literal=SHARED_PIPELINE_URL=https://github.com/sivarajp/jenkins-shared-pipeline.git
```

4. Apply jenkins yaml

```sh
 kubectl apply -f all.yaml
```

5. You can setup ingress of ur choice. If you need local port forward 

```sh
 kubectl port-forward svc/jenkins -n jenkins 8080:8080
```