import httpx

from clients.base import BaseServiceClient


class CustomerServiceClient(BaseServiceClient):
    """Client for customer-service's /customers API."""

    def create_customer(
        self, first_name: str, last_name: str, email: str, phone: str
    ) -> httpx.Response:
        return self.http.post(
            "/customers",
            json={
                "firstName": first_name,
                "lastName": last_name,
                "email": email,
                "phone": phone,
            },
        )

    def get_customer(self, customer_id: int) -> httpx.Response:
        return self.http.get(f"/customers/{customer_id}")

    def list_customers(self) -> httpx.Response:
        return self.http.get("/customers")

    def update_customer(
        self, customer_id: int, first_name: str, last_name: str, email: str, phone: str
    ) -> httpx.Response:
        return self.http.put(
            f"/customers/{customer_id}",
            json={
                "firstName": first_name,
                "lastName": last_name,
                "email": email,
                "phone": phone,
            },
        )

    def delete_customer(self, customer_id: int) -> httpx.Response:
        return self.http.delete(f"/customers/{customer_id}")
