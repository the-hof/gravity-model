#!/bin/bash

YEARS=$1

for (( y=1; y<=$YEARS; y++ ))
do
  for hours in {1..24}
  do
    for days in {1..365}
    do
      curl http://localhost:8080/gravity/system/1/timestep/create
    done
  done
done

