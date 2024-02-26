# DevOps training

## Docker Images Build

1. single stage build Docker image
   1. go to directory `java-simple-single-stage-build`
   2. try to run `docker build -t java-simple-single-stage:latest .`
   3. check image size `docker images java-simple-single-stage`
   4. try to run container `docker run java-simple-single-stage`
2. multi stage build Docker image
   1. go to directory `java-simple-multi-stage-build`
   2. try to run `docker build -f Dockerfile.multi -t java-simple-multi-stage:latest .`
   3. check image size `docker images java-simple-multi-stage`
   4. try to run container `docker run java-simple-multi-stage`

## Docker-compose test sandbox

1. go to directory `devops-go-example`
2. explore application
3. to run test `docker-compose -f docker-compose.test.yml up --build --abort-on-container-exit --exit-code-from it_test`
4. to tear down `docker-compose -f docker-compose.test.yml down`


## Install SonarQube

1. use AWS AMI from Bitnami
2. make sure to assign public IPv4
3. get password by `sudo cat /home/bitnami/bitnami_credentials`
4. login to web console via EC2 public IPv4

## IaC Terraform

1. go to `terraform-iac`
2. do `brew install awscli`
3. run `aws configure`
4. run `terraform plan`
5. run `terraform apply`
6. let explore AWS Web Console

## IaC Terraform Cloud

1. go to cloud <https://www.terraform.io> get token to config `TF_API_TOKEN` environment on Github Action.
2. go to AWS Web Console > `Security credentials`
3. config secret `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY` as sensitive environment variable
4. do provisioning
5. Install Kubectl command (`brew install kubectl`)
6. Install AWS CLI
7. run `aws configure`, if not setting yet.
8. run `aws eks update-kubeconfig --region ap-southeast-1 --name eks-devops-cluster`
9. try with `kubectl get node`

## Install ArgoCD

* make sure Kubernetes Cluster is ready to use.

1. run `kubectl create namespace argocd`
2. run `kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml`
3. forward port `kubectl port-forward svc/argocd-server -n argocd 8080:443`
4. get password `kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | base64 -d; echo`

## Explore CI

1. `devops-javaa-example`
2. check `.github/cicd.yml`
3. add remote repository
4. push

## Explore CD

1. Config Github Action Secret `PAT` (Personel Access Token) and make sure `SONAR_TOKEN` and `SONAR_HOST_URL` was config.
2. `devops-java-example`
3. check `.github/cicd.yml`
4. uncomment CD block
5. add remote repository
6. push
