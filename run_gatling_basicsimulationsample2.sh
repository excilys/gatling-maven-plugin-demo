#!/bin/bash
usage() {
    cat <<EOM
    Usage:
    For Custom Run - $(basename $0) NumberOfUsers DurationInSeconds
    or
    For Default Configuration - $(basename $0)

EOM
    exit 0
}


if [ -z "$1" ]
  then
    #usage
    echo "Number of Users to be used = 1"
    declare -i users="1"
  elif [ "$1" == "-h" ]
   then
     usage
  else
   echo "Number of Users to be used = $1"
   declare -i users=$1
fi


if [ -z "$2" ]
  then
    #usage
    echo "Duration in Seconds = 5"
    declare -i duration="5"
  else
   echo "Duration in Seconds = $2"
   declare -i duration=$2
fi

simulationClass="computerdatabase.BasicSimulationSample2"
mvn gatling:test -Dgatling.simulationClass=$simulationClass  -Dusers=$users -DdurationInSecs=$duration