#!/bin/bash
# shellcheck disable=SC2045
# shellcheck disable=SC2006

for i in `ls test/*.java`;
do
  javac "$i" ;
  # java -cp ../sootclasses-trunk-jar-with-dependencies.jar soot.Main -pp -f J -cp . "${i/\//\.}"
  tmp="${i/\//\.}";
  java -cp ../sootclasses-trunk-jar-with-dependencies.jar soot.Main -pp -f J -cp . "${tmp%%.java}";
done

popd
