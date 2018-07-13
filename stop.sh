#!/bin/bash

kubectl delete deployment key-val-deployment
kubectl delete deployment mongodb-deployment
kubectl delete service mongodb-service
kubectl delete service key-val-service
kubectl delete configmap key-value-service-config
