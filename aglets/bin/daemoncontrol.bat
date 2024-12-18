@REM ==================================================
@rem $Id: daemoncontrol.bat.in,v 1.1 2001/08/08 02:57:51 kbd4hire Exp $
@rem
@rem TahitiDaemon control script
@rem Author:  Larry Spector
@rem	
@REM ==================================================

@echo off
@REM Configurable part
set AGLET_HOME=/home/aditya/aglets
set JDK_HOME=/usr/lib/jvm/java-8-openjdk-amd64/jre

@REM Add all jars in the lib directory to the classpath
@REM Local class files will supercede aglets.jar.
set LOCALCLASSPATH=%AGLET_HOME%\lib;%AGLET_HOME%\lib\classes
for %%i in (%AGLET_HOME%\lib\*.jar) do call lcp.bat %%i

%JDK_HOME%\bin\java -classpath "%LOCALCLASSPATH%" TahitiDaemonClient %1 %2 %3 %4 %5 %6 %7 %8 %9


