#!/usr/bin/env bash

#Set Environment variables with defaults before we set euo pipefail
GIT_REV=$(git log -n 1 --pretty=format:%h)
export VERSION="${GIT_REV}"
HARBOR_URL="repo.nexclipper.io"
HARBOR_PROJECT_NAME="dev-env"
export DOCKER_IMAGE_REGISTRY=$HARBOR_URL/$HARBOR_PROJECT_NAME
DEPLOYMENT_VERSION="v1"


if [ $# -lt 1 ]; then
    echo "Usage is deploy.sh [ENVIRONMENT] e.g. deploy.sh dev"
    exit 1
fi

export ENV=$1
export K8S_APP_NAMESPACE="${ENV}"
echo "K8S_APP_NAMESPACE = [ $K8S_APP_NAMESPACE ]"
# DOCKER_IMAGE_REGISTRY="repo.nexclipper.io/nexcloud"
K8S_DEPLOYMENT_FILE="kube/k8s-deploy.yaml"
set -euo pipefail


#############################################################
# Deploys an Alpha Microservice
#############################################################

export IMG_TAG_TO_DEPLOY=$DOCKER_IMAGE_REGISTRY/$APP_IMAGE_NAME:$VERSION

echo "$IMG_TAG_TO_DEPLOY will be deployed..."


echo "Substituting env vars in K8s manifest (if available)"
envsubst < ${K8S_DEPLOYMENT_FILE} > $K8S_DEPLOYMENT_FILE.generated

cat $K8S_DEPLOYMENT_FILE.generated


echo "Applying manifest..."
kubectl apply -f $K8S_DEPLOYMENT_FILE.generated -n $K8S_APP_NAMESPACE

echo "Wait for deployment to finish..."
kubectl rollout status deployment/$APP_IMAGE_NAME-$DEPLOYMENT_VERSION -n $K8S_APP_NAMESPACE
