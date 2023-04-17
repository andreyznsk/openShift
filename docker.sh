#!/usr/bin/env bash

mvn clean package -T 1C -P dev_zad &&
docker build -t andreyznsk/app:main.latest addressbook_main/. &&
docker build -t andreyznsk/app:auth.latest addressbook_auth/. &&
docker build -t andreyznsk/app:cache.latest addressbook_cache/. &&
docker build -t andreyznsk/app:calc.latest addressbook_ageCalcFunction/. &&
docker push andreyznsk/app:main.latest &&
docker push andreyznsk/app:auth.latest &&
docker push andreyznsk/app:cache.latest &&
docker push andreyznsk/app:calc.latest

