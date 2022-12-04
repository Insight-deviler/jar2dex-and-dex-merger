[![Android CI](https://github.com/Insight-deviler/jar2dex-and-dex-merger/actions/workflows/android.yml/badge.svg)](https://github.com/Insight-deviler/jar2dex-and-dex-merger/actions/workflows/android.yml)
# Jar2Dex and Dex merger
Jar2Dex is an android application for 

    1. Merging dex files.    
    2. Convert .jar and .classes file to dex.   
    3. Convert a folder containing .jar or .classes to dex.
    
# Information
The app needs android 8+ devices for converting jar to dex due to the limitations of D8 library

For merging dex files there is no such limitations(but can't compile java 8 codes) and may work in all android versions

The app can create a folder of its own and copy the rt.jar file to it but not android.jar file

(or)

Create a folder named Jar2Dex in the storage and add the following to it manually:

[Core lambda library (Rt.jar)](https://github.com/Insight-deviler/jar2dex-and-dex-merger/blob/main/app/src/main/assets/fonts/rtjar.jar)

    All the above are present in the assets folder of the app
    
[Android.jar](https://github.com/Insight-deviler/jar2dex-and-dex-merger/blob/main/android.jar)

    Get it from android studio or from this page itself.
    
# Usage:

    1. Set the Min-API depending on the library you use, the default one is 19.
    2. The output location is same as the input location.
    3. Wait for few minutes, until the dialog closes.

# Credits:
Argument class is provided by [tranleduy2000](https://github.com/tranleduy2000/javaide)from Java N IDE.
