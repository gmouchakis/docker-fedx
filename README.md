# docker-fedx

FedX deployed on a Tomcat 8 server.

To build
```
docker build -t fedx .
```

To run
```
docker run -v /path/to/fedx/conf:/etc/fedx fedx
```

Where /path/to/fedx/conf the directory that contains the fedx federation configuration file.

Then you can run queries at
```
http://<CONTAINER_IP>:8080/fedx-3.1/repositories/sparql?query=<QUERY>
```
