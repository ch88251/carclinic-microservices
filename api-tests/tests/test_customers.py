import uuid

import pytest


def unique_email() -> str:
    return f"{uuid.uuid4().hex}@example.com"


class TestCreateCustomer:
    def test_create_customer_returns_created_customer(self, customer_client):
        email = unique_email()
        response = customer_client.create_customer("Ada", "Lovelace", email, "555-0101")

        assert response.status_code == 200
        body = response.json()
        assert body["id"] is not None
        assert body["firstName"] == "Ada"
        assert body["lastName"] == "Lovelace"
        assert body["email"] == email
        assert body["phone"] == "555-0101"

        customer_client.delete_customer(body["id"])

    @pytest.mark.parametrize("missing_field", ["firstName", "lastName", "email", "phone"])
    def test_create_customer_missing_required_field_returns_400(self, customer_client, missing_field):
        payload = {
            "firstName": "Ada",
            "lastName": "Lovelace",
            "email": unique_email(),
            "phone": "555-0101",
        }
        payload[missing_field] = ""

        response = customer_client.http.post("/customers", json=payload)

        assert response.status_code == 400


class TestGetCustomer:
    def test_get_customer_by_id_returns_customer(self, customer_client, make_customer):
        customer = make_customer(email=unique_email())

        response = customer_client.get_customer(customer["id"])

        assert response.status_code == 200
        assert response.json() == customer

    def test_get_customer_by_unknown_id_returns_404(self, customer_client):
        response = customer_client.get_customer(999_999_999)

        assert response.status_code == 404


class TestListCustomers:
    def test_list_customers_includes_created_customer(self, customer_client, make_customer):
        customer = make_customer(email=unique_email())

        response = customer_client.list_customers()

        assert response.status_code == 200
        ids = [c["id"] for c in response.json()]
        assert customer["id"] in ids


class TestUpdateCustomer:
    def test_update_customer_returns_updated_customer(self, customer_client, make_customer):
        customer = make_customer(email=unique_email())

        updated_email = unique_email()
        response = customer_client.update_customer(
            customer["id"], "Grace", "Hopper", updated_email, "555-0199"
        )

        assert response.status_code == 200
        body = response.json()
        assert body["id"] == customer["id"]
        assert body["firstName"] == "Grace"
        assert body["lastName"] == "Hopper"
        assert body["email"] == updated_email
        assert body["phone"] == "555-0199"

    def test_update_customer_unknown_id_returns_404(self, customer_client):
        response = customer_client.update_customer(
            999_999_999, "Grace", "Hopper", unique_email(), "555-0199"
        )

        assert response.status_code == 404


class TestDeleteCustomer:
    def test_delete_customer_returns_no_content(self, customer_client, make_customer):
        customer = make_customer(email=unique_email())

        response = customer_client.delete_customer(customer["id"])
        assert response.status_code == 204

        get_response = customer_client.get_customer(customer["id"])
        assert get_response.status_code == 404

    def test_delete_customer_unknown_id_returns_404(self, customer_client):
        response = customer_client.delete_customer(999_999_999)

        assert response.status_code == 404
