#!/bin/bash

javac -cp lanterna.jar:. CandyCrush.java

if [ "$OSTYPE" == "linux-gnu" ]
then
	resize -s 62 25
else
	printf '\e[8;25;62t'
fi
