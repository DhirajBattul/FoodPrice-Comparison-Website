FoodPrice Comparison Website

A microservices-based food price comparison platform built with Spring Boot. Users can browse restaurants and products, and compare live prices of the same item across Zomato and Swiggy clones in real time.


Designed and built a food price comparison platform using Spring Boot microservices with Eureka, API Gateway, OpenFeign, PostgreSQL and Redis — featuring a hybrid static-dynamic architecture where product listings are served from a local mapping DB and live prices are fetched from independent Zomato and Swiggy clone services on demand with 30-minute Redis caching.
---


---



---

## Architecture

```
                        ┌─────────────────┐
                        │  React Frontend  │
                        │  localhost:3000  │
                        └────────┬─────────┘
                                 │ HTTP
                                 ▼
                        ┌─────────────────┐
                        │   API Gateway   │
                        │  localhost:8080  │
                        └────────┬─────────┘
                                 │ lb:// (Eureka load balanced)
             ┌───────────────────┼───────────────────┐
             │                   │                   │
             ▼                   ▼                   ▼
  ┌──────────────────┐ ┌──────────────────┐ ┌──────────────────┐
  │  ZomatoClone     │ │  SwiggyClone     │ │ComparisonService │
  │  localhost:8081  │ │  localhost:8082  │ │  localhost:8083  │
  └────────┬─────────┘ └────────┬─────────┘ └────────┬─────────┘
           │                    │                ┌────┴──────────┐
           ▼                    ▼                ▼               ▼
  ┌─────────────────┐ ┌─────────────────┐ ┌──────────┐  ┌──────────────┐
  │  PostgreSQL     │ │  PostgreSQL     │ │PostgreSQL│  │    Redis     │
  │  zomato DB      │ │  swiggy DB      │ │comparison│  │  port: 6379  │
  └─────────────────┘ └─────────────────┘ │    DB    │  │ Price Cache  │
                                           └──────────┘  └──────────────┘
                                 ▲
                        ┌────────┴─────────┐
                        │ Service Registry  │
                        │  Eureka Server   │
                        │  localhost:8761  │
                        └──────────────────┘
```

---

## Services

| Service | Port | Description |
|---|---|---|
| `service-registry` | 8761 | Eureka Server — all services register here |
| `APIGateway` | 8080 | Single entry point — routes all frontend requests |
| `ZomatoClone` | 8081 | Zomato clone — owns its own restaurant and product data |
| `SwiggyClone` | 8082 | Swiggy clone — mirrors ZomatoClone structure |
| `ComparisonService` | 8083 | Core service — seeds mappings, fetches live prices, returns comparisons |

---

## Tech Stack

| Layer | Technology |
|---|---|
| Backend Framework | Spring Boot 3.x |
| Service Discovery | Spring Cloud Eureka |
| API Gateway | Spring Cloud Gateway (WebFlux) |
| Inter-service Calls | OpenFeign |
| Database | PostgreSQL |
| Cache | Redis |
| ORM | Spring Data JPA / Hibernate |
| Frontend | React (localhost:3000) |
| Build Tool | Maven |

---

