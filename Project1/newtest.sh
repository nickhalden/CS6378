#!/bin/bash
n=3
i=2
k=$(($i+1))
echo $k
echo $k
exit

while true; do 
	var=$(cat /Users/nxc141130/IdeaProjects/CS6378/Project1/logs/*.txt | grep "finished" |wc -l) 
	if [ "$n" -eq "$var" ]
	then
		echo $var
		exit
	fi
done

