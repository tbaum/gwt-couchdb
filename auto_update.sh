#!/bin/bash

UPDATE_TIMESTAMP=.last_update
touch $UPDATE_TIMESTAMP
rm $UPDATE_TIMESTAMP

while (sleep 0.5); do
    if [ ! -e $UPDATE_TIMESTAMP ] ||
       [ ! -z $(find src/main/couch -newer $UPDATE_TIMESTAMP -type f) ] ||
       [ ! -z $(find target/abowind.war -newer $UPDATE_TIMESTAMP -type f) ]; then
	touch $UPDATE_TIMESTAMP
	[ -e target/couch ] && rm -r target/couch
	mkdir target/couch
    cp -r src/main/couch/* target/couch/
    cp -r target/abowind/de.atns.abowind.Proto1 target/couch/_attachments
	couchapp push target/couch http://admin:develop@127.0.0.1:5984/abowind
    fi
done
