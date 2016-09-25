ps -aef | grep 'config.txt'| awk '{print $2}' | xargs kill -9

