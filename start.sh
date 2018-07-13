#!/bin/bash

# Setup docker to use minikube
eval $(minikube docker-env)


# Build service container image
docker build -t ws:00.01 .

kubectl create configmap key-value-service-config --from-file=src/main/resources/application.properties

cd kubernetes

# Create services first in order to have proper envs stup for the deployments
kubectl create -f ./mongodb-service.yaml
kubectl create -f ./key-val-service.yaml

kubectl create -f ./mongodb-deployment.yaml
kubectl create -f ./key-val-deployment.yaml
