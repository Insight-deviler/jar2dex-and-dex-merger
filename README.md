# Jar2Dex and Dex merger
Jar2Dex is an android application for 
    1. Merging dex files.
    2. Convert .jar and .classes file to dex
    3. Convert a folder containing .jar or .classes to dex
    
# Information
The app needs android 8+ devices for converting jar to dex due to the limitations of D8 library
For merging dex files there is no such limitations and may work in all android versions

# How it works?
This app works by loading the d8 library dynamically using runtime execution.
Now the app can do the below functions except adding android.jar file 
                                (or)
Create a folder named Jar2Dex in the storage and add the following to it manually:
    1. [D8 library](https://github.com/Insight-deviler/jar2dex-and-dex-merger/blob/main/app/src/main/assets/fonts/d8s.jar)
    2. [Merger library](https://github.com/Insight-deviler/jar2dex-and-dex-merger/blob/main/app/src/main/assets/fonts/merge.jar)
    3. [Core lambda library (Rt.jar)](https://github.com/Insight-deviler/jar2dex-and-dex-merger/blob/main/app/src/main/assets/fonts/rtjar.jar)
    All the above are present in the assets folder of the app
    
    4. Android.jar 
    Get it from android studio or from this page itself, [click me](https://github.com/Insight-deviler/jar2dex-and-dex-merger/blob/main/android.jar)
    
# Usage:
    1. Set the Min-API depending on the library you use, the default one is 19.
    2. The output location is same as the input location.
    3. Wait for few minutes, until the dialog closes.
    
    Demo app is also provided for easy using(If you don't have time for compiling codes)
    
# Credits:
Argument class is provided by [tranleduy2000](https://github.com/tranleduy2000/javaide)from Java N IDE.

Runtime execution inspired from [RohitVermaOP](https://github.com/RohitVermaOP/apkbuilder-sample)BuildX app.
