# Virtual smart home plus



## Docker

In order to build docker image you first need to create .jar by building 
project with maven. After you're done with this run docker build.

```console
$ mvn clean package
$ docker build -f Dockerfile -t virtual-smart-home-plus .
```

Once you have image built you are ready to run it.

```console
$ docker run virtual-smart-home-plus -p 8080:8080
```

Our application will now run in docker container at port 8080. 
Usually the docker IP is 172.168.0.2, if it's not the case in your
situation you can find container's IP with.

```console
$ docker container list
$ docker inspect <container_ID> | grep "IPAddress"
```

## Surefire HTML reports

If you wish to generate HTML reports containing easier to read test results
and overview of project dependencies. Keep in mind it will take a few minutes.
You can do so with:

```console
$ mvn clean site
```

This command will generate HTML files containing report in
<code>/target/site</code> folder.