#!/usr/bin/env bash
cd sauce-core
../gradlew -q test jacoco
echo 'complete core test'

cd ../sauce-android
../gradlew -q connectedDebugAndroidTest
echo 'complete android test'