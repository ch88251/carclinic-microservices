# API Tests

Integration tests for the Car Clinic microservices, written in Python with pytest and HTTPX.

## Setup

```bash
cd api-tests
uv sync
```

Copy `.env.example` to `.env` and adjust service URLs if they differ from the defaults (assumes
services are reachable as in `carclinic-microservices/docker-compose.yml`, e.g. via
`docker compose up`).

## Running tests

```bash
uv run pytest
```

## Structure

- `clients/` — thin HTTPX-based clients, one per service, wrapping raw HTTP calls.
- `tests/` — pytest test modules, one per service/resource.
- `conftest.py` — shared fixtures (service clients, test data factories with cleanup).

## Adding tests for a new service

1. Add a `<service>_URL` env var (with a default) and a `<service>_url` fixture in `conftest.py`.
2. Add a client class in `clients/` wrapping that service's endpoints.
3. Add a `tests/test_<service>.py` module.
