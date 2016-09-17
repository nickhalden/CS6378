#!/bin/bash


# Change this to your netid
netid=nxc141130

#
# Root directory of your project
PROJDIR=$HOME/IdeaProjects/CS6378/Project1

#
# This assumes your config file is named "config.txt"
# and is located in your project directory
#
CONFIG=$PROJDIR/config.txt

#
# Directory your java classes are in
#
BINDIR=$PROJDIR/bin

#
# Your main project class
#
PROG=Project1

n=1


cat $CONFIG | sed -e "s/#.*//" | sed -e "/^\s*$/d" |
(
    read i
    echo $i
    while read line 
    do	
    	if [ "$n" -le "$i" ]
	then
        	host=$( echo $line | awk '{ print $2 }' )
        	echo $host 
		port=$( echo $line | awk '{ print $3 }' )
		echo $port
		cd 
		java -cp  $PROJDIR/out/production/Project1 TestProject  $PROJDIR/out/production/Project1/config.txt $port  &
        n=$(( n + 1 ))
	fi
    done
   
)

