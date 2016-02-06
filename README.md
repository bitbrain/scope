# scope
A 3D space network arena shooter

## Features

* fight against your friends in a space arena
* collect energy and deliver as many as possible to win
* shoot your enemies with lasers to interrupt their journey

## Technology used

* libGDX
* MBassador
* Blender 

## How to build the project

First of all you have to manually install [Universal Tween Engine](https://github.com/libgdx/libgdx/wiki/Universal-Tween-Engine#using-your-local-maven-repository) because it does not exist officially as Maven resource.

### Desktop

Run command:
```
gradlew desktop:run
```
Build & pack command:
```
gradlew desktop:dist
```

### Android

Run command:
```
gradlew android:installDebug android:run
```
Build & pack command:
```
gradlew android:assembleRelease
```

### iOS
Run command:
```
gradlew ios:launchIPhoneSimulator
gradlew ios:launchIPadSimulator
gradlew ios:launchIOSDevice
```
Build & pack command:
```
gradlew ios:createIPA
```
