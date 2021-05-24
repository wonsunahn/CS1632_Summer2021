# CS 1632 - Software Quality Assurance
Spring Semester 2021 - Exercise 1

* DUE: May 26 (Wednesday), 2021 11:59 PM 

## Description

For this exercise, you and your partner will determine a **test plan** for the
simple simulator GoatGoatCar, based on the requirements listed.

* There should be at least **6 test cases** altogether.
* Test cases should have unambiguous preconditions, execution steps, and
  postconditions.  Remember, a test case should be **reproducible** no matter
who is doing the testing and should be executable **independent** of one other.
* The test plan should include a **traceability matrix** showing the mapping of
  test cases to requirements.  The traceability matrix should **cover all
requirements** for good test coverage.

You will execute the above test plan to find **defects** in the software.  There
are several known defects (10 at last count); try to find and report on at
least **two defects**.

You may find it useful to initially do some **exploratory testing** to
determine how the system works before coming up with a test plan.  Exploring
the software will give you a good feel of which test cases to add and which
ones are likely fo uncover defects.  A good test plan has edge cases and corner
cases as well as base cases.

## Creating a Test Plan

Remember, the template for test cases -

```
	IDENTIFIER: [A unique number or string (e.g. TEST-ARGS-NUMBER-FIVE-ARGS)]
	TEST CASE: [A description of the test case]
	PRECONDITIONS: [State of the system before performing execution steps]
	EXECUTION STEPS: [Step-by-step instructions on how to perform test]
	POSTCONDITIONS: [*EXPECTED* state of the system after having performed execution steps]
```

The IDENTIFIER is some value which will uniquely specify the test case.  We
learned it can be either a number, or a more descriptive label (e.g.
TEST-INVALID-TIMES, TEST-LOW-NUM-TIMES, etc.).  For this exercise, please use a
descriptive label.  Note that the INPUT VALUES and OUTPUT VALUES fields in the
template are omitted because we are not doing method unit testing.
Please refer to [Lecture 4: Test Plans](../../lectures/CS1632_Lecture4_Test_Plans.pdf) Slides 8 - 13
for more details and examples for each item.

PRECONDITIONS is the **state of the system before performing the test**.  If
the system is a website, it is things like: "user is logged into the website"
or "user is subscribed to the mailing list", etc.  The program we will be
testing today is a commandline Java program where the test is simply running
the program with a set of arguments.  There is no program state to speak of
before running the test.  Then, what things should you put as preconditions?
There are plenty!  For a Java program, it is always important which Java
Runtime Environment (JRE) the program runs in.  You could say something like:
When "java -version" is run, the system outputs "java version "1.8.0_231".
Companies would actually perform multiple tests on the same program using the
same arguments for multiple versions of Java that their clients use, and each
would be a separate test case!

EXECUTION STEPS should be **numbered step-by-step instructions** on what you expect
the tester to do.  It should be exact to the letter so that tests are
reproducible.  The tested program does not allow any interaction with the user
other than to run it with commandline arguments, so all steps will be
one-liners in this case, but typically there will be multiple steps.

POSTCONDITIONS should be the **expected state** of the program after the test.
It is **not** the observed state.  So where should you get expectations of your
program?  From the [requirements](requirements.md) of course!

## Creating a Traceability Matrix

A traceability matrix allows us to correlate test cases to requirements and
vice versa.  It allows us to check why a test case is being run (to check one
or more requirements).  It also allows us to check how much testing a
particular requirement is receiving.  See Chapter 6 section 6 (6.6) in the
textbook for examples and details on creating them.

Note that some test cases may test several requirements at once.  This is only
natural.  As we saw on the last example on the lecture slides, in a real-world
traceability matrix, you can have one test case mapped to multiple
requirements.  Vice versa, you can have several test cases for one requirement.

Also note that a good traceability matrix must cover all requirements to have
no gaps in test coverage.  Please make sure of this.

## Reporting Defects

Please listen to "Lecture 5: Defects" before completing this section.

This is the correct format for defects -

```
	 IDENTIFIER: [A unique number or string (e.g. BUG-ARGS-NUMBER-FIVE-ARGS)]
	 SUMMARY: [A one sentence description of defect]
	 DESCRIPTION: [A detailed description of everything the tester discovered]
	 REPRODUCTION STEPS: [Preconditions + Steps to reproduce (similar to test case execution steps)]
	 EXPECTED BEHAVIOR: [What you expected according to requirements]
	 OBSERVED BEHAVIOR: [What you *ACTUALLY* saw]
```

Please refer to [Lecture 5: Defects](../../lectures/CS1632_Lecture5_Defects.pdf) Slides 15 - 22
for more details and examples for each item.  Optional bug report items
such as SEVERITY or IMPACT were not included for this exercise.

**Don't forget to include any preconditions at the start of the REPRODUCTION
STEPS.** You will not be able to reproduce the bug even if you reproduce the
steps if you start from a different precondition!

## Test Plan / Defect Reporting Tips

Please read [tips.md](tips.md) for additional hints on how to properly document test cases and defects.

## Test Application: GoatGoatCar

GoatGoatCar is going to be our way of determining the correct answer to the
"Monty Hall Problem" (https://en.wikipedia.org/wiki/Monty_Hall_problem).  The
Monty Hall Problem can be summarized in pictures:

<img alt="Figure 1" src=img/monty-hall-pic-1.jpg width=350>
<img alt="Figure 2" src=img/monty-hall-pic-2.jpg width=350>
<img alt="Figure 3" src=img/monty-hall-pic-3.jpg width=350>
<img alt="Figure 4" src=img/monty-hall-pic-4.jpg width=350>
<img alt="Figure 5" src=img/monty-hall-pic-5.jpg width=350>

What do you think?  The answer lies in the below illustration:

<img alt="Figure 6" src=img/monty-hall-pic-6.svg width=350>

If you still don't get it, you can read the above wikipedia entry if you like
reading, or here is a video if you are a more visual learner: https://www.youtube.com/watch?v=9vRUxbzJZ9Y.

Another way to get at the answer is through Monte Carlo simulations, that is
simulate the game thousands of times to empirically answer the question one
way or another.  The program we will test today randomly generates games,
plays them one way another, and gives a summary at the end of what percentage
of the time switching would give you the "good item" and what percentage of
the time staying would have won you the "bad" item.

The program will accept four arguments, in this order:

* __"Good" option__ - This will be the item that the player actually wants (e.g., a car).

* __"Bad" option__ - This will be the item that the player does not want (e.g., a goat)

* __Num Times__ - This is the number of rounds that will be simulated.

* __Num Threads__ - This will be the number of threads that the simulation runs in

Example command to execute.  This will use "car" as the good choice, "goat" as
the bad choice, 10001 as the number of rounds to simulate, and it will do it on
four threads.

```
$ java -jar GoatGoatCar.jar car goat 10001 4
```

GoatGoatCar.jar is available in this directory.  

Create a reasonable test plan based on the [requirements](requirements.md).
Hint: Try to have a combination of explicit boundary values and implicit
boundary values as well as interior values in your test cases.  As we learned,
this is where most of the defects will reside!

## Submission

Please note that the submission of this exercise takes the place of the
attendance check for this in-class exercise.  Submission will be reflected on
your participation grade.  As long as the submission shows any amount of
reasonable work was done (that is, you don't get a 0 on GradeScope), it will
count.  On the other hand, the actual score you get on GradeScope will not
count towards your grade.  It's there only for feedback purposes and help you
do Deliverable 1 better.  This applies to all future exercises.

Please use the ReportTemplate.docx file provided in this directory to write
your report.  If you don't have a .docx compatible word processor, that's
perfectly fine as long as you follow the same organization.  A PDF version of
the file is at ReportTemplate.pdf.  Please make sure that the traceability
matrix, test cases, and defects are on seperate pages.  You will be submitting
to GradeScope in PDF format.  When you submit, you will be asked to assign
pages in the PDF file to each rubric item: 1. Traceability Matrix, 2. Test
Cases, and 3. Defects.  

Each pairwise group shall do *one* submission to GradeScope to the **Exercise
1** link, by *one member* of the group.  The submitting member shall press the
**View or edit group** link at the top-right corner of the assignment page
before or after submission to add his/her partner.  That way, the feedback will
be accessible to both of you (and the credit).  You may submit as many times as
you wish before the deadline as long as you make sure of this. This applies to
all future exercises and deliverables.

When your exercise is marked as graded, you should find feedback written on
your grade details.  Please use the feedback wisely when doing Deliverable 1!

## Groupwork Plan

Please create a shared online document for the report using Google Docs or
Microsoft OneDrive or your preferred cloud service.  Each partner shall come up
with three test cases and one defect each, for a sum of six test cases and two
defects for the group (or more, if you wish).  The first partner shall work on
the requirements FUN-ARGS-NUMBER and FUN-ARGS-INVALID.  The second partner
shall work on the requirements FUN-DISPLAY-RESULTS, FUN-DISPLAY-ITERATIONS, and
FUN-SMALL-NUM.

Once you are done, check each other's work to make sure that the test case
documentation and defect reporting were done properly.  If you see issues,
discuss.  Once you are both satisfied, you may submit.

## Extra Credit

* DUE: Jun 7, 2021 6:30 PM

This submission is optional.  An extra credit of 1 point out of 100 points for
the entire course will be awarded to the group that finds the most number of
defects in the program.  There can be multiple winners too if there is a tie!

Duplicate defects that are really the same defect that is triggered by two
different inputs will be counted only once.  How do you know if it is the same
defect?  If they display the same behavior (e.g. causes the same type of
exception at the same source code line).

Also, some behaviors that you may think are defects are expected behaviors.  At
below are some examples: 

1. Bash behavior

```
$ java -jar GoatGoatCar.jar car goat 1000 \
> 
```
... or ...
```
$ java -jar GoatGoatCar.jar car goat 1000 "
> 
```

This is just normal bash behavior that allows user to enter multi-line
commands.  In the first case, the newline was escaped (\) and in the second
case, a multi-line string was started using the quote(") charater.  Other
special characters recognized by bash is listed here:

https://www.tldp.org/LDP/abs/html/special-chars.html

2. Empty names or duplicate names

```
$ java -jar GoatGoatCar.jar "" "" 1000 4
Thread 0: 250 iterations.
Thread 1: 250 iterations.
Thread 2: 250 iterations.
Thread 3: 250 iterations.
Calculating..


Switch:
 : 68.500%
 : 31.500%
-----------------------------
Stay:
 : 31.500%
 : 68.500%
```

There is no requirement that the "good" and "bad" strings have to be
unique, or they cannot be empty strings for that matter.  This is still
behavior conformant with the requirements.

Each pairwise group shall do one submission to the **Exercise 1 Extra Credit**
link on GradeScope in the same manner as for Exercise 1.  Please use the
ReportTemplateExtraCredit.docx to write the report.  Make sure you number the
defects so it is easy to count!  Only defects that I can reproduce will be
counted.  That means they must be properly reported using the preconditions,
execution steps, and postconditions template described on the worksheet.
