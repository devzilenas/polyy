@ECHO OFF
SET proj=polyy
:: Delete classes dir
RMDIR /S /Q classes
mkdir classes

:: Compiling
@ECHO ON
javac -sourcepath src -d classes src\Polyy.java 
@ECHO OFF
mkdir jars
del jars\%proj%.jar
:: Making jar
@ECHO ON
:: With entry-point 
jar cfe jars\%proj%.jar Polyy -C classes .
:: With manifest
jar cfm jars\%proj%.jar src\Manifest.txt -C classes .

