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

-keep class com.example.practiceCTF.LoginActivity {
}

-keepclassmembers class com.example.practiceCTF.LoginActivity {
   private authenticate(java.lang.String, java.lang.String);
}

-keep,allowobfuscation class com.example.practiceCTF.UsernameFetcher {
    public *;
    private *;
}

-keep,allowobfuscation class com.example.practiceCTF.PasswordFetcher {
    public *;
    private *;
}


# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile