@REM ==================================================
@rem Aglets Server startup script
@rem	author  Hideki Tai
@rem	version    $Revision: 1.2 $ $Date: 2002/01/19 21:54:58 $ Last checkin: $author$
@REM ==================================================

@echo off
@REM Configurable part
set AGLET_HOME=/home/aditya/aglets
set JDK_HOME=/usr/lib/jvm/java-8-openjdk-amd64/jre

@REM Add all jars in the lib directory to the classpath
@REM Local class files will supercede aglets.jar.
set LOCALCLASSPATH=%CLASSPATH%;%AGLET_HOME%\lib;%AGLET_HOME%\lib\classes
for %%i in (%AGLET_HOME%\lib\*.jar) do call lcp.bat %%i

:loop

"%JDK_HOME%\bin\java" -Daglets.home=%AGLET_HOME% -classpath "%LOCALCLASSPATH%" com.ibm.awb.launcher.Main %1 %2 %3 %4 %5 %6 %7 %8 %9

@if errorlevel 1 goto exit
@goto loop

:exit
