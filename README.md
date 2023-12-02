# JAVA Order Microservice Docker Example 

### 1. Service Register 
Command to build docker image 
```
docker build -t kaustavdassoa/eurekaserver:latest .
docker run -d -p8761:8761 --name=eurekaserver kaustavdassoa/eurekaserver:latest
```


### 2. Product Service 
Command to build docker image
```
docker build -t kaustavdassoa/productservice:latest .
docker run -d -p9001:9001 -e EUREKA_SERVER_URL=http://host.docker.internal:8761/eureka --name=productservice kaustavdassoa/productservice:latest

```


### 3. Payment Service 
Command to build docker image
```
docker build -t kaustavdassoa/paymentservice:latest .
docker run -d -p7001:7001 -e EUREKA_SERVER_URL=http://host.docker.internal:8761/eureka --name=paymentservice kaustavdassoa/paymentservice:latest
```

### 3. Order Service
Command to build docker image
```
docker build -t kaustavdassoa/orderservice:latest .
docker run -d -p8001:8001 -e EUREKA_SERVER_URL=http://host.docker.internal:8761/eureka --name=orderservice kaustavdassoa/orderservice:latest
```


## Docker Composer 
Docker composer command 
```
docker-compose -f docker-compose.yml up --detach
```