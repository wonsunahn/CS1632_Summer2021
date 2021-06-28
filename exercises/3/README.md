- [CS 1632 - Software Quality Assurance](#cs-1632---software-quality-assurance)
  * [Desciption](#desciption)
  * [Task 1: Write test cases](#task-1-write-test-cases)
    + [Tips for Writing assertions for each Test](#tips-for-writing-assertions-for-each-test)
    + [Other Tips](#other-tips)
  * [Task 2: Add test cases to test suite and save project](#task-2-add-test-cases-to-test-suite-and-save-project)
  * [Task 3: Export test suite to JUnit class](#task-3-export-test-suite-to-junit-class)
    + [Tips for JUnit + Selenium problem solving](#tips-for-junit--selenium-problem-solving)
  * [Submission](#submission)
  * [GradeScope Feedback](#gradescope-feedback)
  * [Resources](#resources)

<small><i><a href='http://ecotrust-canada.github.io/markdown-toc/'>Table of contents generated with markdown-toc</a></i></small>


# CS 1632 - Software Quality Assurance

* DUE: Jul 2 (Friday), 2021 11:59 PM

**GitHub Classroom Link:** https://classroom.github.com/g/TcOFne7o

## Desciption

For this assignment, you and a partner will write a systems-level, automated
black-box tests for the Reddit website using the Selenium IDE.  Specifically,
we are going to test the r/cats subreddit at:

https://www.reddit.com/r/cats/

It was chosen because it is a nice safe subreddit which is policed pretty well.  Besides, it's cats.

First, let's start by adding the Selenium IDE browser extension for your web
browser by selecting "Chrome Download" or "Firefox Download" on the below
website:

https://www.selenium.dev/selenium-ide/

Then, open Selenium IDE by clicking on the newly created browser extension with
the "Se" symbol.  You should see a pop up window that looks very similar to the
one shown on the lecture slides.

## Task 1: Write test cases

Write a test case for each requirement listed in
[requirements.md](requirements.md).  Name each test case same as the
requirement name (e.g. FUN-TITLE).  I'm asking you to do this for the purposes
of GradeScope autograding.  Normally, you would use a more descriptive name.

Remember, each test must end with an assertion!  The list of available
assertions and other commands are available at:

https://www.selenium.dev/selenium-ide/docs/en/api/commands

### Tips for Writing assertions for each Test

You will want to use the below commands and assertions to test each of the requirements:

FUN-TITLE: "assert title"

FUN-JOIN-BUTTON-EXISTS - "assert text"

FUN-SIGNUP-LINK - "store attribute" followed by "assert".  You will be storing
the attribute value to a Selenium variable and asserting on the value of that
variable.  Now there is a defect in Selenium IDE with the "store attribute"
command where the target selector button is disabled when it should be enabled.
A work around is to enter a command such as "assert text" or "click" which
allows you to use the target selector, fill in the locator string using it, and
then revert to "store attribute".  As I said, while Selenium web driver is very
mature, Selenium IDE is a work in progress. :)

FUN-SEARCH-SMELLY-CAT - "assert text"

FUN-RULE-3 - "assert text"

FUN-RULES-10-ITEMS - "assert element present" for the 10th item; "assert element not present" for the locator for the 11th item.

**Hint:** If you are really stuck, there is a solution project file [Reddit
Cats Solution.side](Reddit%20Cats%20Solution.side) that you can open from
Selenium IDE.  Take a peek but don't loiter!

### Other Tips

Sometimes your test case will not work as expected.  Here are a few hints on how to debug a problem:

1. Check the Log window at the bottom of the Selenium IDE.  It will tell you
   which step failed for what reason (in red).

1. Select the test step that failed in the main test case window, and then
   select the Reference tab at the bottom pane of the IDE.  It will display
usage instructions for that command.  Remember always, the first argument goes
to the Target field and the second argument goes to the Value field, regardless
of command.

1. Sometimes the target component of a test step is the problem.  The selector
   button tries to generate a locator string as best it can using xpath, css
selector, or id tag.  But it is not fool proof.  The problem is, the web page
may change ever so slightly on the next page load (e.g. due to a new post, or a
new comment) and then the locator will stop working.  You will notice that
there is a small down arrow at the end of the target text box.  If you click on
that arrow, you will see alternative locator strings to the current string.
Select the one that looks specific enough to be able to point to the target but
also general enough to not change between page loads.  You do need to try this
out several times to get a feel of what a good locator string is.  Here is an
in-depth discussion about locators:  

   https://www.selenium.dev/documentation/en/webdriver/locating_elements/#element-selection-strategies
   
1. Sometimes you can use an XPATH position locator string to check that an
   element exists at an expected location ("assert element present") or does
not exist ("assert element not present").  But to do this, you have to select
the XPath position locator string in the drop-down list of optional strings in
the Target field.

1. For those of you who are working in groups, you will be working on the same
   shared .side project file. So it is especially important that your pull
before opening the project file and push immediately after you have modified
and saved the project file. Otherwise, you may get merge conflicts. Merging
conflicts is possible by using the technique described in 
[Using\_Git](https://github.com/wonsunahn/CS1632_Summer2021/blob/master/lectures/Using_Git.pdf)
slides, but it's best to avoid it.

## Task 2: Add test cases to test suite and save project

1. Choose "Test Suites" from the left panel drop down menu.

1. There will already be a "Default Suite" there with possibly one or more tests.

1. Right click on "Default Suite", or click on the vertical-3-dot context menu button, and select "Rename" and rename to "RedditCats".

1. Right click on "RedditCats", or click on the vertical-3-dot context menu
   button, and select "Add tests".  Make sure all tests are checked as shown in
the below figure.  Press on the "Select" button.

   <img alt="Test Suite" src=test-suite-selection.png width=700>
   
1. Click on the "Save project" button on the top right corner that looks like a
   floppy disk.  Save to file name "Reddit Cats.side" in the exercise root folder.

## Task 3: Export test suite to JUnit class

Once you are done writing your Selenium test suite, let's try exporting the test
suite in Selenium IDE to a Java JUnit test class.  

Follow these instructions:

1. Right click on "RedditCats", or click on the vertical-3-dot context menu
   button, and select "Export".

1. Select "Java JUnit" in the list of language options and optionally check
   "Include step descriptions as a separate comment" to generate more detailed
comments.  Leave other boxes unchecked.

1. Save the resulting file into "RedditCatsTest.java" to the root of the
   exercise 3 directory.

1. You will have to also add this line to the beginning of the @Before
setUp() method in the generated RedditCatsTest.java file:

   ```
   System.setProperty("webdriver.chrome.driver", "Windows/chromedriver.exe");
   ```
   Or whatever the path is to your OS compatible chromedriver.  

You can now run the RedditCatsTest JUnit class using the provided
[TestRunner.java](TestRunner.java) using one of the following scripts:

* If you are running Windows:
   ```
   run.bat
   ```

* If you are running Mac or Linux:
   ```
   run.sh
   ```

* You can also run your Selenium tests on Eclipse using the "Run JUnit"
  feature, after opening the provided Eclipse project.

Note that the script only works if you have Chrome version 91 installed on your
computer (the most recent version as of today).  If you have a different
version of Chrome, you may have to update the chromedriver.exe (or
chromedriver) in your respective OS folder (Windows / Mac / Linux) by
downloading a new Chrome Web Driver from:

https://chromedriver.chromium.org/downloads

Your Chrome version can be obtained by clicking on the vertical-3-dot menu at
the top right corner of your browser, then Help > About Google Chrome.

If things go properly, you will see the Chrome browser pop up repeatedly for
each test case, perform the actions, and close.  In the command line, you
should see "ALL TESTS PASSED", which is printed by TestRunner if there are no
failures.

There are multiple reasons why you would want to export to JUnit:

1. You may have a pre-existing testing framework in JUnit (or Python Pytest, or
   JavaScript Mocha, etc).  And you may want to merge the Selenium IDE testing
script to the language and framework of your choice.

1. Exporting to JUnit really gives you a good sense of what's happening under
   the covers (in terms of the actual calls to the WebDriver).  Also, if there
is a test case that is particularly hard to nail down just by using Selenium
IDE, you can touch it up in the form of exported Java code.  

1. Selenium IDE also gives the option to export your JUnit test directly to a
   Selenium Grid which can run the test cases in parallel.  This can allow you
to utilize a server farm to finish your testing very quickly, although we will
not explore this option today.

### Tips for JUnit + Selenium problem solving

1. Often problems that are not apparent in the Selenium IDE commands become apparent in the Java code.  Read the Java code to detect problems.

1. One common problem with Selenium is that it takes a long time for certain
   web pages or web elements to load and if Selenium proceeds with testing
immediately after opening a page, the tests will fail.  So Selenium provides
APIs to allow you to wait until an event happens (e.g. the element is
loaded).  All the details about which APIs to use on which situations is in
the page:

   https://www.selenium.dev/documentation/en/webdriver/waits/
   
   For your purposes, an implicit wait setting at the beginning should be enough.  Insert the following line in the @Before setUp() method:

   ```
   driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
   ```

   In order to use that line, you will need to also import this library:

   ```
   import java.util.concurrent.TimeUnit;
   ```

   What that does is: for every step, if the corresponding element is
missing, it inserts an implicit wait of 10 seconds before signaling a
failure.  Selenium IDE internally uses an implicit wait time of 30 seconds
when running a script, but when it exports the script to the JUnit test, it
fails to insert that implicit wait in the @Before setUp() method.  So if you
want one, you need to insert it yourself.

1. Another common problem is that depending on the browser window size,
   certain elements may disappear.  For example, the Reddit site would hide
the "rules" bar on the right hand side if the windows is too narrow.  One
way to solve this is to uniformly set the window size at the @Before setUp()
method so that all your test cases are tested on the same dimensions (and
remove all calls to setSize in your test cases):

   ```
   driver.manage().window().setSize(new Dimension(1200, 800));
   ```
  
1. Yet another common problem is that some websites have pesky pop up
   windows that prevents the Selenium Web Driver from interacting with the
website, resulting in test failure.  For example, the reddit.com has a pop
up window asking whether you want to "Show notifications" for the website
when visited for the first time.  Until you click "Block" or "Allow" on the
pop up, the rest of the website is inaccessible.  Once you click on a
choice, reddit.com will store your choice in a cookie and not ask you on
subsequent visits.

   When testing with Selenum IDE, the pop up will not occur because most
likely this is not our first visit and as a browser extension, Selenium IDE
has access to cookies.  However, when testing with the exported JUnit test,
JUnit launches a standalone web browser instance in its own sandbox so it
will not have access to pre-existing cookies.  That means the notification
pop up will occur on the JUnit test every time.

   If you do not want any interference from pop ups during testing, there is
a simple way to do it.  Replace the following line in the @Before setUp()
method, replace the following line:

   ```
   driver = new ChromeDriver();
   ```

   with the following set of lines:


   ```
   ChromeOptions options = new ChromeOptions();
   options.addArguments("--disable-notifications");
   driver = new ChromeDriver(options);
   ```

   In order to use ChromeOptions, you will need to import the class at the top:

   ```
   import org.openqa.selenium.chrome.ChromeOptions;
   ```

   The "Show notifications" pop up is not the only annoying pop up out
there. Most of us are also familiar with the "Know your location" pop up.
To disable this one, simply add this line to the above:

   ```
   options.addArguments("--disable-geolocation");
   ```

1. Lastly, there is a quirk with the Reddit website that I only found out recently.  The following two websites are very different websites:
   * https://www.reddit.com/r/cats/  
   * https://www.reddit.com//r/cats/  

   You'd be surprised!  Make sure you are accessing the former and not the latter.

## Submission

Each pairwise group will do one submission to GradeScope as usual.  The
submitting member must use the "View or edit group" link at the top-right
corner of the assignment page after submission to add his/her partner.  

Submit the repository created by GitHub Classroom for your team to GradeScope
at the **Exercise 3 GitHub** link.  Make sure you the files "Reddit Cats.side"
and "RedditCatsTest.java" are in your submission.  Once you submit, GradeScope
will run the autograder to grade you and give feedback.  If you get deductions,
fix your code based on the feedback and resubmit.  Repeat until you don't get
deductions.

My solution test cases are stored as the [Reddit Cats
Solution.side](Reddit%20Cats%20Solution.side) file afore mentioned.  Compare
with your test cases (this is not the only way to implement the test cases).
It is in JSON format so you should be able to open it with a text editor and
trace it with your eyes, if that's what you prefer.

## GradeScope Feedback

The GradeScope autograder works in 2 phases:

1. **RedditCatsTest on https://www.reddit.com/r/cats**: This tests your
   RedditCatsTest.java file on the cats subreddit as originally intended.  All
of your tests should pass.

1. **RedditCatsTest on https://www.reddit.com/r/dogs**: This tests your
   RedditCatsTest.java file on the dogs subreddit, repeating the same steps but
for a different webpage.  Now some tests should pass but some should fail.
Specifically, FUN-JOIN-BUTTON-EXISTS and FUN-SEARCH-SMELLY-CAT should pass and
the rest shold fail.

You may be curious how I was able to run the tests on the GradeScope docker
images when they most likely don't have displays to render the Chrome browser.
The Chrome webdriver, as well as other webdrivers, can be run in "headless"
mode.  That is, the tests can be performed inside the web engine without having
to actually display the page.  This is very common practice since in a work
setting, testers will be running tests on server machines or even on the cloud
in docker images like I did.  If you need to do this in the future, you can
achieve this by passing options when creating the Chrome webdriver:

```
ChromeOptions options = new ChromeOptions();
options.addArguments("--headless");
options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
driver = new ChromeDriver(options);
```

## Resources

These links are the same ones posted at the end of the slides:

* Selenium IDE Command Reference:  
https://www.selenium.dev/selenium-ide/docs/en/api/commands
