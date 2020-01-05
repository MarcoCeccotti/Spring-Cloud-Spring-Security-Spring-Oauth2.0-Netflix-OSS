#!/bin/bash

cd app-marco-cloud-discovery-server
mvn spring-boot:run &

cd ..
cd app-marco-cloud-api-gateway
mvn spring-boot:run &

cd ..
cd app-marco-cloud-auth-server
mvn spring-boot:run &

cd ..
cd app-marco-cloud-products-service
mvn spring-boot:run &