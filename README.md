<p align="center">
<a href="https://www.cognits.co/"><img src="https://www.cognits.co/_next/static/image/assets/images/Logo/black/logo_black.e6d22562719e8f844321ede2133cb698.svg" max-width="200px"/></a>  
</p>

# Requirement
- Java 11

## Dependencies used
| Name       | Version |
|------------|---------|
| [Selenium] | 4.8.0   |
| [Testng]   | 7.7.1   |

[Selenium]: https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java
[Testng]: https://mvnrepository.com/artifact/org.testng/testng

# Testing Environment
### Install
1. [IntelliJ Community](https://www.jetbrains.com/es-es/idea/download/#section=windows)
2. [Git Windows or macOS](https://git-scm.com/downloads)
3. [Java JDK](https://www.oracle.com/java/technologies/javase-downloads.html)
    * Define Environment Variables Windows:
        * JAVA HOME: C:\Program Files\Java\jdk-version
        * %JAVA_HOME%
        * Execute javac command in cmd to verify configuration.
    * Define Environment Variables macOS: https://mkyong.com/java/how-to-set-java_home-environment-variable-on-mac-os-x/
   
### Clone Repo
1. Generate a Shh and register in the account where you are clonning this repo. (i.e. Bitbucket, GitHub, gitLab)
   * [Windows](https://help.github.com/articles/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent/#platform-windows)
   * [GNU/Linux](https://help.github.com/articles/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent/#platform-linux)
   * [MacOS](https://help.github.com/articles/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent/#platform-mac)

2. git bash: git clone direccionShhRepo

   1.1 For macOS to evade the "chromedriver is not recognized" message:
open the terminal and navigate to the folder where the chromedriver is and excute the following commnad:
    * xattr -d com.apple.quarantine chromedriver

### Execute web
1. Browser selection
   * Configure the data provider in the java class or the xml.
2. Execute your test.

### Credits
Jhonatan Flores
