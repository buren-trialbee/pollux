#!/bin/bash
VAR="asdf"
if [ ! -z "$1" ]
	then 
		VAR="$1"
fi
git add .; git commit -m VAR; git push origin master;