# Short Description
PCM provides APIs to manage patient provider lists and enables patients to manage their e-consents.

# Full Description

# Supported Source Code Tags and Current `Dockerfile` Link

[`1.22.0 (latest)`](https://github.com/bhits/pcm-api/releases/tag/1.22.0), [`1.18.0`](https://github.com/bhits/pcm-api/releases/tag/1.18.0)

[`Current Dockerfile`](hhttps://github.com/bhits/pcm-api/blob/master/pcm/src/main/docker/Dockerfile)

For more information about this image, the source code, and its history, please see the [GitHub repository](https://github.com/bhits/pcm-api).

# What is PCM?

Patient Consent Management (PCM) API is one of the core components of the Consent2Share (C2S) system. PCM provides APIs for patients to manage their electronic consents including consent creation, consent audit, consent eSignature, consent export, and patient provider list management. An electronic patient consent is a digital agreement created and electronically signed by the patient to select:

1. which *sensitive categories* of health information he or she wishes to share,
2. which *purposes* the medical information may be used,
3. identify the provider *from* whom the information can be disclosed,
4. identify the provider *to* whom the information can be disclosed,
5. record the date when the consent *goes into effect*,
6. identify the *expiration date*.

The value sets, downloaded from [VSAC](https://vsac.nlm.nih.gov/), are a set of concept codes mapped to various *sensitive categories* that are intended for use by organizations exchanging personally identifiable protected health information to perform data segmentation based on the patient’s privacy preferences in his or her consent.


For more information and related downloads for Consent2Share, please visit [Consent2Share](https://bhits.github.io/consent2share/).
# How to use this image


## Start a PCM instance

Be sure to familiarize yourself with the repository's [README.md](https://github.com/bhits/pcm-api) file before starting the instance.

`docker run  --name pcm -d bhits/pcm:latest <additional program arguments>`

*NOTE: In order for this API to fully function as a microservice in the Consent2Share application, it is required to setup the dependency microservices and support level infrastructure. Please refer to the [Consent2Share Deployment Guide](https://github.com/bhits/consent2share/releases/download/2.1.0/c2s-deployment-guide.pdf) for instructions to setup the Consent2Share infrastructure.*


## Configure

This API runs with a [default configuration](https://github.com/bhits/pcm-api/blob/master/pcm/src/main/resources/application.yml) that is primarily targeted for the development environment.  The Spring profile `docker` is actived by default when building images. [Spring Boot](https://projects.spring.io/spring-boot/) supports several methods to override the default configuration to configure the API for a certain deployment environment. 

Here is example to override default database password:

`docker run -d bhits/pcm:latest --spring.datasource.password=strongpassword`

## Using a custom configuration file

To use custom `application.yml`, mount the file to the docker host and set the environment variable `spring.config.location`.

`docker run -v "/path/on/dockerhost/C2S_PROPS/pcm/application.yml:/java/C2S_PROPS/pcm/application.yml" -d bhits/pcm:tag --spring.config.location="file:/java/C2S_PROPS/pcm/"`

## Environment Variables

When you start the PCM image, you can edit the configuration of the PCM instance by passing one or more environment variables on the command line. 

### JAR_FILE

This environment variable is used to setup which jar file will run. you need mount the jar file to the root of container.

`docker run --name pcm -e JAR_FILE="pcm-latest.jar" -v "/path/on/dockerhost/pcm-latest.jar:/pcm-latest.jar" -d bhits/pcm:latest`

### JAVA_OPTS 

This environment variable is used to setup JVM argument, such as memory configuration.

`docker run --name pcm -e "JAVA_OPTS=-Xms512m -Xmx700m -Xss1m" -d bhits/pcm:latest`

### DEFAULT_PROGRAM_ARGS 

This environment variable is used to setup application arugument. The default value of is "--spring.profiles.active=docker".

`docker run --name pcm -e DEFAULT_PROGRAM_ARGS="--spring.profiles.active=ssl,docker" -d bhits/pcm:latest`

# Supported Docker versions

This image is officially supported on Docker version 1.12.1.

Support for older versions (down to 1.6) is provided on a best-effort basis.

Please see the [Docker installation documentation](https://docs.docker.com/engine/installation/) for details on how to upgrade your Docker daemon.

# License

View [license](https://github.com/bhits/pcm-api/blob/master/LICENSE) information for the software contained in this image.

# User Feedback

## Documentation 

Documentation for this image is stored in the [bhits/pcm-api](https://github.com/bhits/pcm-api) GitHub repository. Be sure to familiarize yourself with the repository's README.md file before attempting a pull request.

## Issues

If you have any problems with or questions about this image, please contact us through a [GitHub issue](https://github.com/bhits/pcm-api/issues).