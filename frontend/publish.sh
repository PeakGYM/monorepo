#!/bin/bash

version=$(cat package.json | jq .version | tr -d '"')
image="porcupine96/peak-gym-frontend:$version"

docker build -t "$image" .
docker push "$image"
