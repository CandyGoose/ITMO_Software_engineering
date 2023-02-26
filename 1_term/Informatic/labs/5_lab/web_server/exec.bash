#!/bin/bash

id=$(sudo docker container ls --all --quiet --filter "name=webserver")
docker exec -it $id bash
