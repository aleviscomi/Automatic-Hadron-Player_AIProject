#!/bin/bash

if [ $# -ne 2 ]; then
	echo "Uso: $0 <ip> <porta>"
	exit 1
fi

$(java -jar src/HadronPlayer.jar $1 $2)
