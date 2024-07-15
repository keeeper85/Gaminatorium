FROM quay.io/keycloak/keycloak:latest

ENV KC_DB=postgres
ENV KC_HOSTNAME=localhost
ENV KC_LOG_LEVEL=info
ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true

ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]