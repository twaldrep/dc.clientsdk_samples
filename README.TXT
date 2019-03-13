----------------
Before you begin
----------------

Before running these samples, you will need:

1. Apache Maven 3.5.0 or higher
2. JDK version 8.  A JRE is not sufficient to build the samples 

Instructions below on where to edit the sample code to set this configuration.

ConnectionType.LOCAL
--------------------

In order to run the samples, you will need the following

3. A DataConnect 11.5 Standalone Engine installation

         --- OR ---

4. A DataConnect 11.5 RCP installation
   
Note that the sample code must be on the same machine as the DataConnect install.

----------------------------------------------------
Preparing for execution (ConnectionType.LOCAL)
----------------------------------------------------

Set configuration
-----------------

In the SamplesRunner class of the sample code, you will find a variable named sampleDataMacroValue.  
By default, the value is a relative path to the "data" directory.  
If you want to use data in a non-default location, edit the value of this variable to match the location of the data.

In the ConnectionBuilder class of the sample code, you will find a variable named connectionType. 
By default, the value is ConnectionType.REMOTE. 
Change the value to ConnectionType.LOCAL.

In the ConnectionBuilder class of the sample code, you will find a variable named installPath.  
Edit the value of this variable to match the runtime/di9 directory within your install.  
This directory should contain the djengine executable.

See lib/README.txt for details

------------------------------------
Building and testing the sample code
------------------------------------

After you have completed the setup above, and have your JDK and ant installs 
set up, you should be able to build and test the samples as follows:

1. Open a command shell
2. CD to the root directory of the samples
3. Run the following command:
   ant test

-------------------------------------------------------------
 Linux-specic instructions for builing and running remotely
-------------------------------------------------------------
1.	Install Apache Ant 1.7.1 or higher, set ANT_HOME /home/qetester/apache-ant-1.7.0
2.	Install java sdk 1.6 or higher, set JAVA_HOME to /usr/bin/jdk1.6.0_12
3.	add /home/qetester/apache-ant-1.7.0/bin and /usr/bin/jdk1.6.0_12/bin to PATH and LD_LIBRARY_PATH
4.	Copy and unzip di-client-sdk-samples-10.4.3-3.zip, say /home/qetester/BUILDS/di-client-sdk-samples-10.4.3-3
5.	Install Data Integrator Client Tools, say di.install.rpm.linux.di-client-tools-10.4.3-2.rpm.bin
6.	Copy all jar files under /opt/Actian/di-client-tools-10.4.3-2 to /home/qetester/BUILDS/di-client-sdk-samples-10.4.3-3/lib (with command “cp /opt/Actian/di-client-tools-10.4.3-2/*.jar /home/qetester/BUILDS/di-client-sdk-samples-10.4.3-3/lib/”), and add /home/qetester/BUILDS/di-client-sdk-samples-10.4.3-3/lib to environment variable LD_LIBRARY_PATH
7.	Install DI stack, say di.install.rpm.linux.stack-10.4.3-2.rpm.bin. Start di-full stack. This will create cosmos.ini in /home/qetester/.Actian/DI-FULL-10.4.3-2/runtime/di9. Copy cosmos.ini from /home/qetester/.Actian/DI-FULL-10.4.3-2/runtime/di9 to /opt/Actian/di-stack-10.4.3-2/runtime/di9 (where djengine exists). (You may install di standalone, instead of di stack, then you don’t need to copy cosmos.ini).
8.	Upload valid v10 license file. You may shutdown di or leave it running. (Need to copy a valid license file to the install folder if using di standalone).
9.	Modify two files under /home/qetester/BUILDS/di-client-sdk-samples-10.4.3-3/src/com/pervasive/di 
ConnectionBuilder.java --- comment out connectionType = ConnectionType.REMOTE, and uncomment out connectionType = ConnectionType.local; keep the connection credential; modify installPath = "/opt/Actian/di-stack-10.4.3-2/runtime/di9"
(Need to specify the name or IP address of the di server if connectionType = ConnectionType.REMOTE)
SamplesRunner.java --- modify sampleDataMacroValue = "/home/qetester/BUILDS/di-client-sdk-samples-10.4.3-3/data"
10.	Modify build.xml in /home/qetester/BUILDS/di-client-sdk-samples-10.4.3-3 to change the following line to the actual java jdk version if the java jdk version is 1.7, instead of 1.6
<property name="target" value="1.7"/>

Run Test:
Open a new PUTTY session, login as root, go to /home/qetester/BUILDS/di-client-sdk-samples-10.4.3-3/,  
1.	Run following command: (Note: use actual versions on your test machine)
export JAVA_HOME=/usr/bin/jdk1.6.0_12 
export ANT_HOME=/home/qetester/apache-ant-1.7.0
export PATH=$JAVA_HOME:$ANT_HOME:$PATH
export LD_LIBRARY_PATH=/home/qetester/BUILDS/di-client-sdk-samples-10.4.3-3/lib:$JAVA_HOME:$ANT_HOME:$LD_LIBRARY_PATH 

2.	Run command:
ant test
Check Results:
1.	You will see the run process on the console, and got FINISHED_OK for all jobs, and BUILD SUCCESSFUL displayed. 
2.	A map target file will be created under data folder. 
3.	Job log files will be created under /work/log and /work/log/ec.
