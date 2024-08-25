#!/bin/bash

ENV=${1:-development}

./scripts/generate-config.sh $ENV

docker-compose -f docker-compose.yml -f docker-compose.${ENV}.yml up -d