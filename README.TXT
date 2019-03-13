----------------
Before you begin
----------------

Before running these samples, you will need:

1. Apache Maven 3.5.0 or higher (https://maven.apache.org/)
2. JDK version 8.  A JRE is not sufficient to build the samples 
3. DataConnect 11.5 installation (RCP or standalone engine)
   
Note that the sample code must be on the same machine as the DataConnect install.

----------------------------------------------------
Preparing for execution
----------------------------------------------------

Set environment
-----------------
1.  Start a command shell
2.  Ensure that the Apache Maven bin directory is in the path.  If it is, then the command
    "mvn -v" should print the maven version along with the Java version and other info
3.  Add the "runtime/di9" directory relative to the engine installation to your PATH environment
    variable.  At this point, executing the command "djengine" from the command line should
    find the command, but it will fail due to not being able to find the license
4.  Set the "DJLIB" environment variable to the location of the directory that contains your
    cosmos.ini file.  This will typically be in "\ProgramData\Actian\DataConnect\dc-rcp-64-bit-<version>"
    on Windows.
5.  At this point, running "djengine -?" should produce license info and command line help

The Client SDK samples project builds and executes using Apache Maven
------------------------------------
Building and testing the sample code
------------------------------------

After you have completed the setup above, and have your JDK and ant installs 
set up, you should be able to build and test the samples as follows:

1. Open a command shell
2. CD to the root directory of the samples
3. Run the following command:
   mvn verify

Assuming that the environment was set as stated above, this will build the samples and execute
them.

Check Results:
1.	You will see the run process on the console, and got FINISHED_OK for all jobs, and BUILD SUCCESSFUL displayed. 
2.	A map target file will be created under target/runtime/data folder. 
3.	Job log files will be created under target/work/log and target/work/log/ec.
