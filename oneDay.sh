#!/bin/bash

for hours in {1..24}
do
  for minutes in {1..60}
  do
    curl http://localhost:8080/gravity/system/4/timestep/create
  done
done


