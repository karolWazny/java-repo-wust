set VERSION=1.0-SNAPSHOT
java -Djava.security.manager -Djava.security.policy=source/assets/lab9.policy -jar release/app-%VERSION%.jar
REM java -jar release/app-%VERSION%.jar