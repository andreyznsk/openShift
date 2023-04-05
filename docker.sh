#!/usr/bin/env bash

/Users/19208093/kka/tools/ApacheMaven3.6/bin/mvn clean package -T 1C -P dev_zad &&
docker build -t andreyznsk/main:latest addressbook_main/. &&
docker build -t andreyznsk/auth:latest addressbook_auth/. &&
docker build -t andreyznsk/calc:latest addressbook_ageCalcFunction/.
#docker push andreyznsk/main:latest &&
#docker push andreyznsk/auth:latest &&
#docker push andreyznsk/calc:latest

