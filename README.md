# About

Repository for practicing a Capture-The-Flag session.

## Learning Goals

- Do static analysis.
- Do dynamic analysis.
- Inject code through recompiling.

> Mostly reverse engineering related...

# Instructions

Grab the latest `practice-CTF.apk` file from the [releases](https://github.com/wqyeo/CTF-Practice-Android/releases) page.

- Emulator Android's version **must** be 8.0 or higher.
- **DO NOT** make use of the source code, `/src`. Pretend you have no access to the source code, but you may decompile the APK.
- Do whatever else you want, use ChatGPT if you want, overcook and insert a malware into the APK...
- There are 2 questions as of now, each with various hints and their own rules.

> The solutions are in [SOLUTIONS.md](https://github.com/wqyeo/CTF-Practice-Android/blob/main/SOLUTION.md)

## Question 1 (Find Username)

**Question:** In the application, what is the actual username used to login?

**Rules**: None.

<details> 
  <summary><h3>Hint 1 <i>(Click to reveal)</i></h3></summary>
   Decompile, and observe the flow of authentication function.
</details>

<details> 
  <summary><h3>Hint 2</h3></summary>
   How do you access an application's logcat?
</details>

## Question 2 (Login)

**Question:** In the application, do a proper, successful login into a page that displays `WIN`.

**Rules**: Regardless of how you modify the APK or inject new code, you **must** do a proper login,<br>Requiring you to input a username and password into a field, then clicking the `Login` button,<br> bringing you into a page that displays `WIN`.

<details> 
  <summary><h3>Hint 1 <i>(You will likely need this...)</i></h3></summary>
   Although the password seems to be randomly generated, they are actually consistently generated in a fixed set.<br>Ie. it is not true random, but just pseudo random.<br>

  > Pseudo Random means a predictable random.<br>
  > Imagine a rigged dice, that is programmed to roll `6` on every other roll.<br>
  > Or a haunted coin, that lands on head during the night, and lands on tails during the day. 
</details>


<details> 
  <summary><h3>Hint 2</h3></summary>
   How do you inject logging code into the APK?
</details>

<details> 
  <summary><h3>Hint 3</h3></summary>
   Log the password and attempt to authenticate a few times <i>(~11 times)</i>. Everything will make sense after, <i>hopefully</i>...
</details>

# Issues, Suggestions

For another issues or suggestions, post them in the [Issue Tracker](https://github.com/wqyeo/CTF-Practice-Android/issues) of the repository<br>I will accept [Pull Requests](https://github.com/wqyeo/CTF-Practice-Android/pulls) as well.
