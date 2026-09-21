import httpx


class BaseServiceClient:
    """Thin wrapper around an httpx.Client scoped to one service's base URL."""

    def __init__(self, base_url: str, timeout: float = 10.0):
        self.http = httpx.Client(base_url=base_url, timeout=timeout)

    def __enter__(self) -> "BaseServiceClient":
        return self

    def __exit__(self, *exc_info) -> None:
        self.close()

    def close(self) -> None:
        self.http.close()
