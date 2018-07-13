## SpringBoot/MongoDB kubernetes showcase

This project is created as an example on now to setup an SpringBoot based REST service with a dependency on MongoDB.

Project is a result of a process that I went trough during my first days of trying to teach myself how to use kubernetes.

Project has a following structure:
 - kubernetes related files (in `kubernetes` directory)
 - java classes located in `src` (simple REST key/value service based on MongoDB)
 - Dockerfile in the root of the project
 - scripts to create/cleanup created resources (`start.sh` and `stop.sh`)
 

### Scope of the project

This project covers few of the key kubernetes concepts:
  - configuration maps and how to use them in containers
  - deployments
  - services
  
There are two service definitions, namely `mongodb-service` and `key-val-service`).

Mongo service is used internally and not exposed to outside world, key-value-service is intended
to be used by the external users and it is exposed via `NodePort`.

Each service has a corresponding deployment. Deployments are used to describe states we want (number of pods, rolling upgrade behaviour etc).

Mongo deployment is straightforward, it simply defines a single pod in a replica set (please have in mind that this is not something you would use in production and is simply made as an example of an deployment with inter-service dependencies).

Key-value service is more complex in a way that it defines a replica set of 3 nodes.
It also defines liveness and readiness probes.

Livenes probe is used in order to monitor pods and in case it fails to respond, pod is destroyed and replaced with the new one.

Readiness probe is important for rolling upgrades (to avoid requests being routed to the pod that is not ready to accept them, which leads to failed requests).


#### Using the service

Assumption is made that user has `minikube`, `kubectl` and `docker` setup.

In order to start everything up, simply `cd` to the root of the project and execute: 

```
sh start.sh
```

This will execute provided script that performs the following actions:

 - builds a docker image
 - creates configuration map that is used by the key/value service
 - creates mongo and key/value services
 - creates mongo and key/value deployments
 
Convenience script (`stop.sh`) is also provided that performs a complete cleanup.

Key/value service is exposed via `NodePort`, execute following command to get the needed ip/port information in order to be able to use the service:

```
kubectl get service key-val-service
```

It will produce output similar to:

```
NAME              TYPE       CLUSTER-IP       EXTERNAL-IP   PORT(S)          AGE
key-val-service   NodePort   10.104.155.232   <none>        9876:30584/TCP   1h
```

From above output, service can be accessed on `10.104.155.232:30584`.

#### API specification

To create new key/value mapping:

```
HTTP POST: IP:PORT/api/v1/keys

{
"key": "key",
"value": "value"
}
```

To get a value for an existing key:

```
HTTP GET: IP:PORT/api/v1/keys/${key}

{
"key": "key",
"value": "value"
}
```

To delete a key mapping:

```
HTTP DELETE: IP:PORT/api/v1/keys/${key}

```