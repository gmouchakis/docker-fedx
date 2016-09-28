# docker-fedx

FedX deployed on a Tomcat 8 server.

To build
```
docker build -t fexd .
```

To run
```
docker run -v /path/to/fedx/conf:/etc/fedx fedx
```

Where /path/to/fedx/conf the directory that contains the fedx federation configuration file.
