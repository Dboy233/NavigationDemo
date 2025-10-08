# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

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
# 保留泛型签名（不要被擦除）

-keep public interface androidx.viewbinding.ViewBinding
-keep class * implements androidx.viewbinding.ViewBinding{
    *;
}

#你的Activity继承的AppCompatActivity，就是AppCompatActivity
-keep class androidx.appcompat.app.AppCompatActivity
-keep class * extends androidx.appcompat.app.AppCompatActivity
#不加也行，默认不混淆Fragment
-keep class androidx.fragment.app.Fragment
-keep class * extends androidx.fragment.app.Fragment
