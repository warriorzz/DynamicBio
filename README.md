# DynamicBio - Update your twitter bio frequently!
![CI badge](https://github.com/warriorzz/dynamicbio/workflows/CI/badge.svg) [![Docker Hub](https://img.shields.io/docker/pulls/warriorzz/dynamicbio.svg)](https://hub.docker.com/r/warriorzz/dynamicbio)  ![Docker image build and push](https://github.com/warriorzz/DynamicBio/actions/workflows/docker.yml/badge.svg)

This project uses [ktor](https://github.com/ktorio/ktor) and [kord](https://github.com/kordlib/kord).
View this project in action [here](https://twitter.com/warrior__zz).

To run it yourself, use the [docker-compose-example.yml](https://github.com/warriorzz/DynamicBio/blob/main/docker-compose-example.yml):
```yml
version: '3'
services:
  dynamicbio:
    env_file:
      - .env # your .env file
    image: warriorzz/dynamicbio # use warriorzz/dynamicbio:arm64-latest for arm64
    restart: unless-stopped
    container_name: dynamicbio
    depends_on:
      - mongo
    environment:
      - DATABASE_CONNECTION_STRING=mongodb://root:root@mongo
      - DATABASE_NAME=database
      - OAUTH_ACCESS_TOKEN= # Your twitter OAuth token (credentials for user access)
      - OAUTH_ACCESS_SECRET= # Your twitter OAuth secret (credentials for user access)
      - CONSUMER_ACCESS_TOKEN= # Your twitter app token (credentials for app access)
      - CONSUMER_ACCESS_SECRET= # Your twitter app secret (credentials for app access)
      - DISCORD_API_URL=http://dynamicbiobackend/api/v1 # The API url for the discord module
      - ENABLE_DISCORD=true # To disable the discord module, replace 'true' with 'false'
      - DISCORD_GUILD_ID= # The ID of the guild (your discord account and your bot must be joined)
      - DISCORD_USER_ID= # Your discord ID
      - DISCORD_SECRET= # The discord module secret (see discord module section)
    networks:
      - app

  backend:
    container_name: dynamicbiobackend
    build:
      context: .
      dockerfile: backend.Dockerfile
    networks:
      - app
    environment:
      - DISCORD_TOKEN= # Discord token for your bot
      - DATABASE_NAME=database
      - DATABASE_CONNECTION_STRING=mongodb://root:root@mongo
    depends_on:
      - mongo
    ports:
      - 80:80
  mongo:
    image: mongo
    container_name: dynamicbio_database
    environment:
      - MONGO_INITDB_DATABASE=database
      - MONGO_INITDB_ROOT_USERNAME=root
      - MONGO_INITDB_ROOT_PASSWORD=root
    volumes:
      - dynamic_bio:/data/db
    networks:
      - app

volumes:
  dynamic_bio:

networks:
  app:
```

## Modules

### Discord Module

To enable the discord module, you need to provide a "DISCORD_SECRET" which the bot will send you by using the generate command (!update/!generate/!secret).

![Update command](docs/update_command.png?raw=true)