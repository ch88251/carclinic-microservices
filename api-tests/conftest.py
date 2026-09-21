import os

import pytest
from dotenv import load_dotenv

from clients.customer_service import CustomerServiceClient

load_dotenv()


@pytest.fixture(scope="session")
def customer_service_url() -> str:
    return os.getenv("CUSTOMER_SERVICE_URL", "http://localhost:8080")


@pytest.fixture
def customer_client(customer_service_url):
    with CustomerServiceClient(base_url=customer_service_url) as client:
        yield client


@pytest.fixture
def make_customer(customer_client):
    """Creates a customer and cleans it up after the test, regardless of outcome."""
    created_ids = []

    def _make_customer(
        first_name: str = "Test",
        last_name: str = "Customer",
        email: str = "test.customer@example.com",
        phone: str = "555-0100",
    ):
        response = customer_client.create_customer(first_name, last_name, email, phone)
        response.raise_for_status()
        customer = response.json()
        created_ids.append(customer["id"])
        return customer

    yield _make_customer

    for customer_id in created_ids:
        customer_client.delete_customer(customer_id)
