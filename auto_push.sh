#!/bin/bash

UPDATE_TIMESTAMP=.last_update
touch $UPDATE_TIMESTAMP
rm $UPDATE_TIMESTAMP

while (sleep 1); do
    if [ ! -e $UPDATE_TIMESTAMP ] ||
       [ ! -z $(find src/main/couch -newer $UPDATE_TIMESTAMP -type f) ] ||
       [ ! -z $(find target/gwt.war -newer $UPDATE_TIMESTAMP -type f) ]; then
	touch $UPDATE_TIMESTAMP
	. push.sh
    fi
done
