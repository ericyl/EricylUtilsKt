# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/ericyl/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-dontshrink

#-----------------------------------------------------------------------

# NDK C++ Support
-keepclasseswithmembers class * {
    private native <methods>;
    public native <methods>;
    protected native <methods>;
    public static native <methods>;
    private static native <methods>;
    static native <methods>;
    native <methods>;
}

#-----------------------------------------------------------------------

# Android aidl support【Deprecated】
# -dontwarn android.content.pm.**
# -keep class android.content.pm.** { *; }

#-----------------------------------------------------------------------

# Android Suuort
-keepclassmembernames class * extends android.app.Activity {
    public void *(android.view.View);
    public android.view.View *;
}

-keep class * extends android.view.** {
    public static <fields>;
    protected static <fields>;
    public <fields>;
    public static <methods>;
    protected static <methods>;
    public <methods>;
}

-keepclasseswithmembers class * extends android.view.** {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keep public class * extends android.support.v7.widget.** {
    public static <fields>;
    protected static <fields>;
    public <fields>;
    public static <methods>;
    protected static <methods>;
    public <methods>;
}

-keep class * extends android.support.v4.widget.** {
    public static <fields>;
    protected static <fields>;
    public <fields>;
    public static <methods>;
    protected static <methods>;
    public <methods>;
}

-keepclassmembernames class * extends android.preference.Preference {
    public static <fields>;
    protected static <fields>;
    public <fields>;
    public static <methods>;
    protected static <methods>;
    public <methods>;
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepnames class * extends android.preference.Preference


-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

-keep class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
    public <fields>;
}

#-----------------------------------------------------------------------

# Volley Support
-keep public class * extends com.android.volley.** {
    public static <fields>;
    protected static <fields>;
    public <fields>;
    public static <methods>;
    protected static <methods>;
    public <methods>;
}

#-----------------------------------------------------------------------

# Java
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#-----------------------------------------------------------------------

# Ericyl Utils
-keeppackagenames com.ericyl.utils.**
-keep public interface com.ericyl.utils.** {
    public static <fields>;
    protected static <fields>;
    public <fields>;
    protected <fields>;
    public static <methods>;
    protected static <methods>;
    public <methods>;
    protected <methods>;
}

#【Deprecated】
# -keep class com.ericyl.utils.model.** {
#     public static <fields>;
#     protected static <fields>;
#     public <fields>;
#     public static <methods>;
#     protected static <methods>;
#     public <methods>;
# }

-keep class com.ericyl.utils.util.** {
    public static <fields>;
    protected static <fields>;
    public <fields>;
    public static <methods>;
    protected static <methods>;
    public <methods>;
}

-keepattributes Exceptions, InnerClasses, Signature, Deprecated, SourceFile, LineNumberTable, Annotation, EnclosingMethod, MethodParameters