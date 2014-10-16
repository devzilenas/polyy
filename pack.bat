:: Prepares files for compiling as one file
set PATH=%PATH%;F:\pa\PortableApps\GnuWin32\bin
del packed\src\Polyy.java
del packed\jars\polyy.jar
mkdir packed\src
mkdir packed\jars
:: Generates one java file
:: Get all imports
grep --no-filename "^import.*;$" src/*.java | sed -n "G; s/\n/&&/; /^\([ -~]*\n\).*\n\1/d; s/\n//; h; P" > packed\src\Polyy.java
::copy /V /Y /B src\*.java packed\src\Polyy.java
grep --no-filename --invert-match "^import.*;$" src\*.java >> packed\src\Polyy.java

copy /V /Y /B src\Manifest.txt packed\src\Manifest.txt

@ECHO OFF
SET proj=polyy
:: Delete classes dir
RMDIR /S /Q packed\classes
mkdir packed\classes

:: Compiling
@ECHO ON
javac -sourcepath packed\src -d packed\classes packed\src\Polyy.java 
@ECHO OFF
mkdir jars
del packed\jars\%proj%.jar
:: Making jar
@ECHO ON
:: With entry-point 
jar cfe packed\jars\%proj%.jar Polyy -C packed\classes .
:: With manifest
jar cfm packed\jars\%proj%.jar packed\src\Manifest.txt -C packed\classes .

