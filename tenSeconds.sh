#!/bin/bash

for i in {1..10}
do
  curl http://localhost:8080/gravity/system/2/timestep/create
done

curl http://localhost:8080/gravity/system/3/timestep/create

