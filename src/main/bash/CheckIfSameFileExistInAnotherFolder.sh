#!/bin/bash
for entry in `ls $1`
do
	if [ ! -f "$2/$entry" ]
	then
	#:
	#else
		echo "$entry"
	fi
done
