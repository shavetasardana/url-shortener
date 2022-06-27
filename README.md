# url-shortener

## What's this

The `url-shortener` is a service that will enable users/clients to convert a long url to short url and get long url based on short url. 
User will receive a short identifier url that will be mapped to the long url in our system.

## Set up local environment

### Install Docker

You can get it from e.g. https://hub.docker.com/editions/community/docker-ce-desktop-mac. It will enable you to run
Docker containers locally and install several command line tools, e.g. `docker-compose` , which you need
later.

## Start the application locally

Clone the repository. A local database environment needs to be running. `docker-compose.yml` contains all needed configuration for database and application startup. To run the application locally run below command from project folder:

```
docker-compose up
```

It requires [Docker to be installed](#install-docker). It will initialize an empty `url-shortener` database and then execute `./gradlew bootRun` automatically and starts the application.

To run tests use command:

```
./gradlew test
```

## Endpoints

### /url/shorten

Shorten the given long url and returns the short url. 

Sample Request:

```
curl --location --request POST 'localhost:8080/url/shorten' \
--header 'Content-Type: application/json' \
--data-raw 'http://www.test-url-shortener.com'
```

Response:
```
{
    "shortUrl": "https://dkb/e3051497"
}
```

### /url/resolve

Resolve the short url and returns the mapped long url.

Sample Request:
```
curl --location --request GET 'localhost:8080/url/resolve?id=https://dkb/e3051497' \
--header 'Content-Type: application/json' \
--data-raw '
    "http://www.facebook.com/"
'
```

Response:
```
{
    "originalUrl": "http://www.test-url-shortener.com"
}
```

