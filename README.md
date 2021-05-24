# D8-Dex
Jar2Dex in android using D8 library (which support java8) and Runtime Execution!!!

# How it works?
This app works by loading the d8 library dynamically using runtime execution.
Create a folder named Jar2Dex in the storage and add the following to it manually:

    1. D8.jar file which is present in the assets folder.
    2. rt.jar file get it from linux version of JAVA SE 8.
    3. Latest android.jar (API 30).
    
# Usage:
    1. Set the Min-API depending on the library you use, the default one is 19.
    2. Choose the library file by clicking the fab.
    3. The output location is same as the input location.
    4. Wait for few minutes, until the dialog closes.
    
# Fixed Issues:
Improved the function of Async task with dialog

# Credits:
Argument class is provided by [tranleduy2000](https://github.com/tranleduy2000/javaide)from Java N IDE.

Runtime execution inspired from [RohitVermaOP](https://github.com/RohitVermaOP/apkbuilder-sample)BuildX app.
