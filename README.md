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
    4. Wait for few minutes, the app doesn't show any visuals for processing. You can put the app in background, don't clear it from background
    
# Issues:
The Async Task window closes immediately after setting the jar file location. 
This doesn't mean that conversion has been completed. 
So, don't clear the app from background untill and unless you see the dex file in the folder in which your jar file exist.

# Credits:
Argument class is provided by @tranleduy2000 from Java N IDE.
Runtime execution inspired from @RohitVermaOP BuildX app.
