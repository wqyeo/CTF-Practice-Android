# Context

In writing of this solution, I am using these set of tools:

- JADX
- genymotion
- adb
- apktool
- keytool
- keysigner

Additionally, I am using `Arch Linux`, with Kernel Version `6.8.2`<br>
Most of these tools seem to work the same as it would on Windows, but if there are any differences, you may submit a PR/Issue on this repository to suggest additions onto this document...

# Question 2

From the JADX Decompiled code, you may have noticed that the actual password is fetched as its being authenticated:

```java
public boolean authenticate(String str, String str2) {
    String decrypt = AESEncryption.decrypt(this.ENCRYPTED_USERNAME, this.USERNAME_KEY);
    if (decrypt == null) {
        Toast.makeText(this, "Failed to decrypt username!\nEnsure your emulator is running on Android 8.0 for this CTF!!!", 1).show();
        return false;
    }

    Log.d("USERNAME_COMPARISON", "Comparing " + str + " against " + decrypt);
    return decrypt.equals(str.trim()) && this.passwordFetcher.getPassword().equals(str2.trim());
}
```

Specifically, look at the last return line:

```java
... this.passwordFetcher.getPassword() ...
```

You may have notice that, this make use of the `PasswordFetcher` class, with the function of `getPassword`.<br>
Opening the codebase for `PasswordFetcher`, you may have realize that the password is being randomly pseudo generated in a cycle.<br>This means that the input password is always checked against a fixed set of passwords.

There are ~3 possible solutions for this, all of which involves recompiling the project and:

- Injecting a `Log.d` to intercept and find the password set. **(I will be showing this in the solution)**
- Overriding the validation logic.
- Overriding the password fetching logic.

> There are defintely more than 3, but those are out of the scope...

## My Solution (Decompile into Smali, inject `Log.d`, Recompile)

First, decompile the code into smali, I will be using `apktool` in my case:

```bash
apktool -d practice-CTF.apk
```

Secondly, using your favourite text editor, find the code for authentication logic.<br>In my case, it was somewhere in `smali_classes\com\example\practiceCTF\LoginActivity.smali`, it should look like this chunk of code:

```smali
.method private authenticate(Ljava/lang/String;Ljava/lang/String;)Z
    .locals 5

    .line 68
    iget-object v0, p0, Lcom/example/practiceCTF/LoginActivity;->ENCRYPTED_USERNAME:Ljava/lang/String;

    iget-object v1, p0, Lcom/example/practiceCTF/LoginActivity;->USERNAME_KEY:Ljava/lang/String;

    invoke-static {v0, v1}, Lcom/example/practiceCTF/AESEncryption;->decrypt(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const/4 v1, 0x0

    const/4 v2, 0x1

    if-nez v0, :cond_0

    .line 70
    const-string p1, "Failed to decrypt username!\nEnsure your emulator is running on Android 8.0 for this CTF!!!"

    invoke-static {p0, p1, v2}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object p1

    invoke-virtual {p1}, Landroid/widget/Toast;->show()V

    return v1

    .line 74
    :cond_0
    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "Comparing "

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, " against "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    const-string v4, "USERNAME_COMPARISON"

    invoke-static {v4, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 76
    iget-object v3, p0, Lcom/example/practiceCTF/LoginActivity;->passwordFetcher:Lcom/example/practiceCTF/PasswordFetcher;

    invoke-virtual {v3}, Lcom/example/practiceCTF/PasswordFetcher;->getPassword()Ljava/lang/String;

    move-result-object v3

    .line 77
    invoke-virtual {p1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_1

    invoke-virtual {p2}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v3, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_1

    move v1, v2

    :cond_1
    return v1
.end method
```

Thirdly, look for the chunk where the password is fetched. In my case it was obvious as the function identity stayed the same after decompilation:

```smali
.line 76

iget-object v3, p0, Lcom/example/practiceCTF/LoginActivity;->passwordFetcher:Lcom/example/practiceCTF/PasswordFetcher;

invoke-virtual {v3}, Lcom/example/practiceCTF/PasswordFetcher;->getPassword()Ljava/lang/String;

move-result-object v3
```

At the end of the this chunk of code, `v3` should be the result of the actual password.

Fourth, inject a `Log.d`, the template for a `Log.d` in smali is as such:

```smali
# Where v0 is the tag of the log, and v1 is the log's message...
invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I
```

In my case, I re-used `v4` as the tag, and placed `v3` (actual password) as the message.

> If you do want to create a new variable for the tag instead, do `const-string v5, "YOUR_TAG"`, and change `.local 5` to `.local 6` at the start of the function<br>
> (`.local` is used to indicate how many local variables exists in the function...)

```smali
.line 76

iget-object v3, p0, Lcom/example/practiceCTF/LoginActivity;->passwordFetcher:Lcom/example/practiceCTF/PasswordFetcher;

invoke-virtual {v3}, Lcom/example/practiceCTF/PasswordFetcher;->getPassword()Ljava/lang/String;

move-result-object v3

invoke-static {v4, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I
```

> Another place where u can inject the logging, is at the `PasswordFetcher class, you may attempt it if you want to...

Fourth, recompile the edited code:

```sh
# In my case, I recompiled to `newCTF.apk`
apktool b practice-CTF -o newCTF.apk
```

Most android systems reject unsigned APKs that are not flagged as debug;<br>A freshly compiled APK does not come with signature, so you would need to sign it:

```sh
# You *may* not have to do this; Last known it was an issue with Linux whereby a compiled APK package resources isn't properly aligned.
zipalign 4 newCTF.apk aligned-newCTF.apk

# Create a new sign-file if you haven't done so...
keytool -genkey -v -keystore key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias my-alias

# Sign the APK file...
apksigner sign --ks key.jks aligned-newCTF.apk
```

Finally, run the application and connect to it's logcat:

```sh
# Add the `| grep TAG_VALUE`, such that it will only show you logs containing to `TAG_VALUE`.
# In my case, remember that I re-used `v3` as the tag for logging? That value was `USERNAME_COMPARISON`.
# So in my case, my grep should be `USERNAME_COMPARISON`
adb logcat | grep USERNAME_COMPARISON
```

Attempt to randomly authenticate a few times, and find out the pattern of the password,<br>
in my case, it looked like this:

```sh
# NOTE: It WILL look different on every fresh launch of the application! But it will always have a pattern
DbACi <- First shown
jioLI
DdvyE
kFIa7
5k1uj
DbACi <- repeat!
jioLI
DdvyE
kFIa7
5k1uj
DbACi
jioLI
DdvyE <- another repeat!
kFIa7
5k1uj
DbACi
jioLI
DdvyE <- another repeat!
kFIa7
5k1uj
DbACi
jioLI
DdvyE
kFIa7
5k1uj
```

Pick any of the password, and login with the username found on Question 1 to login.<br>You will need up to ~5 attempts...

### Fun Fact

The actual implementation of fetching password is as follows:

The Activity/Screen creates a custom randomizer with a random key.<br>The key is used as a seed for the randomizer. The randomizer 'resets' itself with the seed after every 5 password generation...

> See <https://en.wikipedia.org/wiki/Random_number_generator_attack>
