#!/usr/bin/env sh

TOKEN=$DIGITALOCEAN_TOKEN
CLUSTER_ID=$DEV_CLUSTER_ID
KUBECONFIG_FILE="/tmp/kubeconfig"

apk update && apk add curl 

install_kubectl(){
    curl -LO https://storage.googleapis.com/kubernetes-release/release/v1.18.0/bin/linux/amd64/kubectl
    chmod +x ./kubectl
    mv ./kubectl /usr/local/bin/kubectl
    kubectl version --client
    if [[ $? != 0 ]]
    then
      echo "kubectl failed to be installed"
      exit 1
    else
      echo "kubectl succesfully installed"
    fi
}

load_cluster(){
    curl -X GET   -H "Content-Type: application/json"   -H "Authorization: Bearer $TOKEN" "https://api.digitalocean.com/v2/kubernetes/clusters/$CLUSTER_ID/kubeconfig" > $KUBECONFIG_FILE 
    export KUBECONFIG=$KUBECONFIG_FILE
    kubectl get nodes
    if [[ $? != 0 ]]
    then
      echo "DEV cluster failed to be loaded"
      exit 1
    else
      echo "DEV Cluster loaded with success!!"
    fi
}

install_kubectl
load_cluster