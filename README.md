The Teamscale Custom Check Sample Repository
============================================

This repository is meant to serve as a starting point for developing custom checks for [Teamscale](https://www.teamscale.com).

Creating your own checks with Eclipse
-------------------------------------
Use ```gradlew eclipse``` (linux/mac) or ```gradlew.bat eclipse``` (Windows) to create Eclipse project files, afterwards you can easily import the project into Eclipse. You will find the sample custom check in the ```src/``` directory.

Building the checks for use with Teamscale
------------------------------------------
- Clone the repository
- Run ```gradlew jar``` (linux/mac) or ```gradlew.bat jar``` (windows)
- Copy the jar from ```build/libs/custom-checks.jar``` to the 'custom-checks' folder in your Teamscale installation root.
- Restart Teamscale
- You should now be able to see the sample check when creating a new JAVA analysis profile. It can be found inside the Bad Practice group.


Get Help
--------
Need help? Contact us: support@teamscale.com
