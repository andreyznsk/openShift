#!/usr/bin/env bash

/Users/19208093/kka/tools/ApacheMaven3.6/bin/mvn clean package -T 1C -P dev_zad &&
docker build -t addressbook/main:latest addressbook_main/. &&
docker build -t addressbook/auth:latest addressbook_auth/. &&
docker build -t addressbook/calc:latest addressbook_ageCalcFunction/.
