#!/usr/bin/env bash

./gradlew build && docker build -f Dockerfile -t tsobu/fuelrod-batch .