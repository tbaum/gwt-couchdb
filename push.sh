#!/bin/bash

[ -e target/couch ] && rm -r target/couch
mkdir target/couch
cp -r src/main/couch/* target/couch/
cp -r target/gwt/de.atns.playground.couchdb.Proto1 target/couch/_attachments
couchapp push target/couch http://127.0.0.1:5984/gwt
