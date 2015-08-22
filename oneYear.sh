#!/bin/bash

for hours in {1..24}
do
  for days in {1..365}
  do
    curl http://localhost:8080/gravity/system/1/timestep/create
  done
done

