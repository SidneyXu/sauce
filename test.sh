#!/usr/bin/env bash
cd sauce-android
../gradlew connectedDebugAndroidTest
echo 'complete android test'