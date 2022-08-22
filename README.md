# Virtual smart home plus



### Building image

In order to build image for the tests you need 
following commands.

```console
$ podman build . --tag "${IMAGE_TAG}"
```

This will produce image with tag specified by variable 
`IMAGE_TAG`

### Running image

Once you have image built you are ready to run it.
In order to have access to the opened port `8080` 
with REST API and you must pass the `-p` parameter.

```console
$ podman run -p 8080:8080 ${IMAGE_TAG}
```

Then the applications REST API will be available at 
`http://localhost:8080`


### Surefire HTML reports

If you wish to generate HTML reports containing easier to read test results
and overview of project dependencies. Keep in mind it will take a few minutes.
You can do so with:

```console
$ mvn clean site
```

This command will generate HTML files containing report in
<code>/target/site</code> folder.
