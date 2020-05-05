#!/usr/bin/env bash

if [ -z "$DEBUG" ]; then
  echo 'Debugging is disable by default'
else
  JAVA_OPTS="$JAVA_OPTS -Ddebug=$DEBUG"
fi

if [ -z "$DATABASE_URL" ]; then
  echo 'Default database URL used'
else
  JAVA_OPTS="$JAVA_OPTS -Dspring.datasource.url=$DATABASE_URL"
fi

if [ -z "$DATABASE_USERNAME" ]; then
  echo 'Default database username used'
else
  JAVA_OPTS="$JAVA_OPTS -Dspring.datasource.username=$DATABASE_USERNAME"
fi

if [ -z "$DATABASE_PASSWORD" ]; then
  echo 'Default database password used'
else
  JAVA_OPTS="$JAVA_OPTS -Dspring.datasource.password=$DATABASE_PASSWORD"
fi

if [ -z "$BATCH_CRON" ]; then
  echo 'Using default cron schedule'
else
  JAVA_OPTS="$JAVA_OPTS -Dbatch.cron=$BATCH_CRON"
fi

exec java $JAVA_OPTS \
-jar \
/app.jar
