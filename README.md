# My-Application

Welcome!!!

## The big job of embedded mobile phone development terminal

>  Currently in development stage


development environment：IDEA2022.01 utlimate Edition

> Attention: if you choose the utlimate version,maybe you can't find the project named "Android",you can choose the new project based on the gradle.it's ok.And I had a try.
You Just need right JDK version and SDK version.

build tools：gradle

JDK version：jdk11(corretto)

SDK version：Android10.0(Q)  API level:29

## How to use this project to work with me 

* First, you need to folk the repository into yours,and clone your
repository by `git clone your adress`.
* and then ,open it in your IDE(IDEA),IDEA will automatically install the required jdk and gradle, this kind of build tool is so easy to use.But it doesn't work because you haven't android sdk.
* you should install android sdk,open file->settings->Appearance->system settings->android sdk.It seems to automatically jump out of the interface for you to download the latest SDK。Select the path to download directly。
* Switch the version of the SDK，Re-enter, the place above, select Android10 on the SDK platform.
* Build the project，find the symbol named "Gradle" and In the pop-up window that comes out, click the loop icon(near "+" icon) to build the project.
* Above the IDE, select "no devices" of the device manager, select Physical, connect your phone, open developer mode for the phone and tick Allow installation without reminder. Click Run Project and the apk file will be installed on your phone

thats'all Thank you!