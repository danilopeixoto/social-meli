# Social Meli

Mercado Libre social network.

Social Meli democratizes the marketplace and enables all users to advertise or buy products securely.

## Prerequisites

* [Docker Engine (>=19.03.10)](https://docs.docker.com/engine)
* [Docker Compose (>=1.27.0)](https://docs.docker.com/compose)

## Installation

Build and run service:

```
docker-compose up -d
```

## Design modifications

### Requirements

* US00001: `POST /api/v1/accounts/{id}/following/{followedId}`
* US00002: `GET /api/v1/accounts/{id}/followers/count`
* US00003: `GET /api/v1/accounts/{id}/followers`
* US00004: `GET /api/v1/accounts/{id}/followed`
* US00005: `POST /api/v1/posts`
* US00006: `GET /api/v1/posts/recents/followed/{accountId}`
* US00007: `DELETE /api/v1/accounts/{id}/following/{followedId}`
* US00008: `GET /api/v1/accounts/{id}/followers?sort=username&order=<ASC|DESC>`
* US00009: `GET /api/v1/posts/recents/followed/{accountId}?sort=created_at&order=<ASC|DESC>`
* US00010: `POST /api/v1/posts`
* US00011: `GET /api/v1/posts/promotions/count/{accountId}`
* US00012: `GET /api/v1/posts/promotions/{accountId}`

### User experience

All users can advertise or buy products.

Public and corporate accounts can request account verification and earn a badge:

* `POST /api/v1/accounts/{id}/verifications?verified=<boolean>`

## Documentation

Check the API reference for models and routes:

```
http://localhost:8000/api/v1/docs
```

The hostname and port of the server are defined by the `.env` file in the project root directory.

## Copyright and license

Copyright (c) 2021, Mercado Libre, Inc. All rights reserved.

Project developed under a [BSD-3-Clause license](LICENSE.md).
