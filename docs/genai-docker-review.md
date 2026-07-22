# GenAI Docker review
- Adopted non-root `USER 10001:10001` because it reduces runtime privilege without changing the app's port or writable paths.
- Rejected pinning base images by digest because it would require deliberate, ongoing digest updates for security patches.
- The startup-race answer matched our proof: it distinguished started from ready and led with a MySQL healthcheck plus `depends_on: condition: service_healthy`; its sleep/restart extras were unnecessary.
