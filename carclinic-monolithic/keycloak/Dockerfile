FROM quay.io/keycloak/keycloak:latest AS builder

ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true
ENV KC_DB=postgres

WORKDIR /opt/keycloak
RUN keytool -genkeypair -storepass password -storetype PKCS12 -keyalg RSA -keysize 2048 -dname "CN=server" -alias server -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -keystore conf/server.keystore
RUN /opt/keycloak/bin/kc.sh build

FROM quay.io/keycloak/keycloak:latest
COPY --from=builder /opt/keycloak/ /opt/keycloak/

ENV KC_DB=postgres
ENV KC_DB_URL=jdbc:postgresql://db:5432/carclinic
ENV KC_DB_USERNAME=carclinic
ENV KC_DB_PASSWORD=carclinic
ENV KC_HTTP_ENABLED=true
ENV KC_HOSTNAME_STRICT=false

ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]
