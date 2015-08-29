#!/bin/bash

SYSTEM=$1

for hours in {1..24}
do
  for days in {1..365}
  do
    curl http://localhost:8080/gravity/system/$SYSTEM/timestep/create
  done
done

