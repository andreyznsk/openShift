#!/usr/bin/env bash

/Users/19208093/kka/tools/ApacheMaven3.6/bin/mvn clean package -T 1C -P dev_zad &&
docker build -t andreyznsk/app:main.latest addressbook_main/. &&
docker build -t andreyznsk/app:auth.latest addressbook_auth/. &&
docker build -t andreyznsk/app:cache.latest addressbook_cache/. &&
docker build -t andreyznsk/app:calc.latest addressbook_ageCalcFunction/.

