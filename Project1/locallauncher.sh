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
		port=$( echo $line | awk '{ print $3 }' )
		id=$( echo $line | awk '{ print $1 }' )
		echo $id $host $port 

		#/Users/nxc141130/IdeaProjects/CS6378/Project1/out/production/Project1
		java -cp out/production/Project1/ TestProject $port config.txt $id &
		#java -cp  $PROJDIR/out/production/Project1 TestProject  $PROJDIR/out/production/Project1/config.txt $port  &
        n=$(( n + 1 ))
	else 
		echo $line
	fi
    done
   
)

