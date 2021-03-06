- [CS 1632 - Software Quality Assurance](#cs-1632---software-quality-assurance)
  * [Description](#description)
  * [List of Files](#list-of-files)
  * [How to Run QuickCheck](#how-to-run-quickcheck)
  * [What to do](#what-to-do)
  * [Task 1: Complete IntegerOpsTest](#task-1-complete-integeropstest)
  * [Task 2: Debug IntegerOps](#task-2-debug-integerops)
    + [IntegerOpsTest Lessons](#integeropstest-lessons)
  * [Task 3: Complete StringOpsTest testEquals method](#task-3-complete-stringopstest-testequals-method)
  * [Task 4: Debug StringOps equals method](#task-4-debug-stringops-equals-method)
  * [Task 5: Complete ValidHTMLStringGenerator doShrink method](#task-5-complete-validhtmlstringgenerator-doshrink-method)
  * [Task 6: Debug StringOps isValidHTML method](#task-6-debug-stringops-isvalidhtml-method)
    + [StringOpsTest Lessons](#stringopstest-lessons)
  * [Submission](#submission)
- [Extra Credit](#extra-credit)
  * [Description](#description-1)
  * [What to do](#what-to-do-1)
  * [Extra Credit Submission](#extra-credit-submission)

# CS 1632 - Software Quality Assurance
Summer Semester 2021 - Supplementary Exercise 3

* DUE: July 28 (Wednesday), 2021 6:30 PM 

**GitHub Classroom Link:** https://classroom.github.com/g/HXh7JIpY

## Description

For this exercise, you will practice a software testing technique called
property-based testing.  Please review Lecture 14: Property-based Testing
before starting on this exercise.  Property-based testing is a type of dynamic
testing but differs from your regular unit testing in that it tests properties
rather than individual values to check behavior.  Instead of saying: "for this
particular set of input values, I expect this output value to be returned", a
property says: "regardless of what input values are provided, I expect this
property to hold no matter what".

The fact that a property is invariant across the entire set of inputs allows a
property-based test to be used with any input.  That means, instead of manually
encoding inputs (say, as part of JUnit test scripts), we can even auto-generate
some random inputs and test them against our property!  This type of testing
is called stochastic testing, or random testing.  Stochastic testing relies
on property-based tests to check postconditions for a broad range of inputs.  

But stochastic testing is not the only place where property-based testing is
used.  Property-based testing is also used in the form of invariant assertions
embedded in source code that do sanity checks during operation.  These
assertions are often left on when the software is deployed on the client site
so that the program will shutdown rather than perform some irrecoverable
disastrous action when a sanity check fails.  These sanity checks have to be
invariants because they need to pass for all inputs and all system
configurations that are legal.  Property-based testing is also useful for model
checking that does exhaustive program state space exploration that we will
practice on Exercise 5.

For this exercise, we are going to use a JUnit extension library called
QuickCheck.  Existing JUnit annotations such as @Test, @Before, @After still
apply as usual but we are going to learn about some new annotations that enable
us to do property-based testing.

## List of Files

IntegerOps.java - Methods to perform simple integer arithmetic such as add and subtract (**modify**).

StringsOps.java - Methods to perform some String operations such as checking whether two strings are equal and checking a string is valid HTML (**modify**).

IntegerOpsTest.java - A QuickCheck JUnit class that performs property-based testing on IntegerOps (**modify**).

StringOpsTest.java - A QuickCheck JUnit class that performs property-based testing on StringOps (**modify**).

ABCStringGenerator.java - A QuickCheck generator class that generates random strings containing the characters 'A', 'B', and 'C'.

ValidHTMLStringGenerator.java - A QuickCheck generator class that generates random valid HTML strings containing HTML tags such as <b>, </b>, <i>, </i> (**modify**).

TestRunner.java - Driver class that contains the main method to invoke JUnit on IntegerOpsTest and StringOpsTest.

## How to Run QuickCheck

1. Running QuickCheck. For Windows do:
    ```
    runTest.bat
    ```
1. For Mac / Linux do:
    ```
    bash runTest.sh
    ```    

Initially, you should see one test already failing:
```
testIsValidHTMLTrue(StringOpsTest): Property named 'testIsValidHTMLTrue' failed:
With arguments: [<i><b><i></i></b><b></b><b><i></i></b><i><b><i></i></b></i><i></i></i>]
Seeds for reproduction: [4133865354563074415]

!!! - At least one failure, see above.
```
You may see a different seed and string because it is randomly generated.

Alternatively, I've created an Eclipse project for you so you can use Eclipse
to import the existing project and run either IntegerOpsTest or StringOpsTest
individually as JUnit classes.

## What to do

The goal is to debug IntegerOps and StringOps using the QuickCheck test classes
IntegerOpsTest and StringOpsTest.  The QuickCheck classes are incomplete as of
now so you will have to complete those first.  All places where you need to
modify are marked by // TODO comments.  Pay close attention to the Javadoc
comment above each method that describes what each method does, or is supposed
to do.

## Task 1: Complete IntegerOpsTest

Open IntegerOpsTest.java.  Look at the testAdd method:

```
@Property(trials = 1000)
public void testAdd(int x, int y) {
	// System.out.println("testAdd x='" + x + "', y='" + y + "'");
	// TODO: Fill in.
}
```

Note that each method has the @Property(trials = 1000) annotation.  The
@Property annotation tells QuickCheck that this is a property-based test and
QuickCheck is to do 1000 trial runs using 1000 randomized input values.  Note
that unlike a regular @Test JUnit method, an @Property method has input
parameters x and y.  These input parameters are where the randomized input
values generated by QuickCheck are passed.  So on each of the 1000 trials, x
and y will be passed a different value.  Not only that, the first time you run
it with the 1000 trials will be different from the second set of 1000 trials
(that is, x and y are going to be different values).  You can observe this
yourself by un-commenting the System.out.println.  That is the beauty of
property-based testing: as you run the tests repeatedly, you will gradually
gain higher coverage without you having to do anything!

Now it's time to fill in the method.  Fill in the code according to the
invariant property specified in the Javadoc comment above the method.  If you
implemented it properly, you should get something similar to the following
message when you execute runTest.bat again (actual numbers may differ due to
randomness):

```
testAdd(IntegerOpsTest): Property named 'testAdd' failed:
With arguments: [1091099725, 1056406418]
First arguments found to also provoke a failure: [1936803025, 1056406418]
```

This is telling you that QuickCheck was able to determine that testAdd fails
when x = 1091099725 and y = 1056406418.  What is the part about: "First
arguments found to also provoke a failure"?  That's telling you that among the
1000 trials, [1936803025, 1056406418] were the first set of values where a
defect was found.  From those values, QuickCheck progressively refined or
"shrunk" them to the final values [1091099725, 1056406418].  QuickCheck always
tries to "shrink" input values to the simplest or smallest values that still
trigger the defect to make debugging easier.  Rather that just reporting
[1936803025, 1056406418], [1091099725, 1056406418] gives you more information.
1091099725 + 1056406418 = 2147506143 which is just slightly bigger than
Integer.MAX_VALUE = 2147483647.  So this strongly suggests the defect has
something to do with integer overflow.  Otherwise, we would have had to find
this out by trial-and-error.

If you left the System.out.println un-commented, you can see what's happening
behind the scenes by observing the output:

```
testAdd x='-1967126952', y='1194075525'
testAdd x='1191001002', y='529527415'
testAdd x='-427676937', y='1415513158'
testAdd x='898946678', y='-810210174'
testAdd x='-2096855516', y='147305889'
testAdd x='-1427326142', y='201626672'
testAdd x='927999071', y='-507009504'
testAdd x='-1575502058', y='-1850940687'
testAdd x='-82004065', y='-1320953857'
testAdd x='275074581', y='-1498381415'
testAdd x='1936803025', y='1056406418'  <--- First discovery of defect, starting shrinking ...
testAdd x='0', y='1056406418'
testAdd x='968401512', y='1056406418'
testAdd x='1452602268', y='1056406418'
testAdd x='0', y='1056406418'
testAdd x='726301134', y='1056406418'
testAdd x='1089451701', y='1056406418'
...
testAdd x='1090899926', y='1056406418'
testAdd x='1091033125', y='1056406418'
testAdd x='1091099725', y='1056406418'  <--- Smallest input while still triggering defect
testAdd x='0', y='1056406418'
testAdd x='545549862', y='1056406418'
testAdd x='818324793', y='1056406418'
testAdd x='954712259', y='1056406418'
...
```

You can see how QuickCheck is methodically doing the trial-and-error behind the
scenes using bisection, so that you don't have to do it.

## Task 2: Debug IntegerOps

Now debug IntegerOps.add(int x, int y) so that the test passes.  All you have
to do is: if you detect integer overflow (you add two positive numbers but you
end up with a negative number), then return 0.

Complete testSubstract in the same way and debug IntegerOps.subtract(int x, int
y).

### IntegerOpsTest Lessons

These are the three things you should have learned through this exercise:
1. A @Property QuickCheck test goes through many randomized trials during a
   single test run where each trial is provided with randomized input values.
1. A property check must be an invariant assertion that is true no matter what
   randomized input values are tested.  For example, things like: the addition
of two positive integer should result in a positive integer.
1. When a @Property test fails, not only does QuickCheck provide you with the
   set of input values triggering the defect, but it also "shrinks" them to the
smallest set of defect-triggering values meant to help you debug.

## Task 3: Complete StringOpsTest testEquals method

Open StringOpsTest.java.  Let's look at the testEquals method:

```
@Property(trials = 1000)
public void testEquals(String s1, String s2) {
	// System.out.println("testEquals s1='" + s1 + "', s2='" + s2 + "'");
	// TODO: Fill in.
}
```

Note that now the randomized input parameters **s1** and **s2** are now
strings.  A string is a form of a byte stream, since each character in the
string is represented as an ASCII code byte.  So you can consider this to be
an example of **fuzz testing**, a type of stochastic testing that deals with
byte streams.

When you do fuzz testing, you need to be extra careful in choosing a good
distribution of random values, in order to get good test coverage.  Your end
goal should be to get a good number of random values for each equivalence
class, since each equivalence class would exercise a different part of the
code.  For numerical input values, you are likely to hit every equivalence
class, given enough trials.  Or, you can use the **@InRange** annotation to
ensure that random values are generated for a certain range of input values
for a particular equivalence class.

For byte streams, equivalence classes cannot be simply described using a
"range" like numbers.  For byte streams, equivalence classes are things
like a string with less 10 characters, a string in all-caps, a string in
proper XML format, etc.  These cannot be expressed using the @InRange
annotation.  So we need to create a specialized random value generator for
each equivalence class.  Otherwise, there is fat chance that you will hit
upon a properly formatted XML string by dumb luck!

Going back to our testEquals test, fill in the test as before based on the
Javadoc comments.  Now even after filling in the test the test passes (and
it will pass no matter how many times you run it unless you are very lucky).
Does that mean StringOps is bug-free?  Absolutely not!  If you see
StringOps.equals(String s1, String s2), you can see the two strings are
compared only up to Integer.min(s1.length(), s2.length()).  So if one string
is shorter than the other, but the two strings are identical up to that
point, equals will return true.  That cannot be correct behavior.

Why wasn't the defect caught during the 1000 trials?  That is because the
defect manifests only when s1 and s2 fit a certain pattern (one is a substring
of the other).  And given uniform distribution, it is very unlikely that s1 and
s2 will show any resemblance whatsoever.  If you uncomment System.out.println
and observe the strings passed, you will see what I mean.

That means we have to generate a distribution more likely to uncover the
defect.  Remember in Lecture 14, I stressed that stochastic testing requires a
lot of thinking about the distribution you generate in order to be effective?
This is just such a case.  Modify the testEquals method declaration as such to
use @From annotations:

```
@Property(trials = 1000)
public void testEquals(@From(ABCStringGenerator.class) String s1, @From(ABCStringGenerator.class) String s2) {
	...
}
```

The @From annotation tells QuickCheck to use the ABCStringGenerator.class
instead of the default uniform String generator to generate s1 and s2.  Open
ABCStringGenerator.java, and focus on the overridden generate method.  I
overrode the default method such that now the generated strings only contain
'A's, 'B's, and 'C's.  This greatly increases the chance that the two strings
will resemble each other, giving us a better chance of uncovering the defect.
Once you make the change, testEquals should fail after one or two runs.  Note
how I also overrode the doShrink method

```
@Override
public List<String> doShrink(SourceOfRandomness random, String larger) {
	if (larger.length() == 0)
		return Collections.emptyList();

	// In this case, the string is shrunk simply by chopping it in half.
	// Both the left and right half are added to the list of strings to check.
	List<String> list = new ArrayList<>();
	list.add(larger.substring(0, larger.length() / 2));
	list.add(larger.substring(larger.length() / 2));
	return list;
}
```

If a String fails a test, it is chopped in half and the lower half and upper
half are tried again respectively.  Implementing the doShrink method allows
QuickCheck to shrink the input values just like before:

```
testEquals(StringOpsTest): Property named 'testEquals' failed:
With arguments: [, A]
First arguments found to also provoke a failure: [AB, ABC]
```

Inputs [AB, ABC] has been shrunk to [, A].  You can see how this is easier to
debug.  The progressive shrinking happens as before:

```
testEquals s1='', s2=''
testEquals s1='AB', s2='ABC'
testEquals s1='A', s2='ABC'
testEquals s1='', s2='ABC'
testEquals s1='', s2='A'
testEquals s1='', s2=''
```
## Task 4: Debug StringOps equals method

Fix equals() based on the feedback given by QuickCheck.

## Task 5: Complete ValidHTMLStringGenerator doShrink method

Now it's time to look at the testIsValidHTML method:

A uniform distribution will not give us valid HTML strings.  So we need a different
generator just like before, namely the HTMLStringGenerator.

```
@Property(trials = 1000)
public void testIsValidHTML(@From(ValidHTMLStringGenerator.class) String s) {
	// System.out.println("testIsValidHTMLTrue s='" + s + "'");
	assertTrue(StringOps.isValidHTML(s));
}
```

ValidHtmlStringGenerator generates randomized HTML strings with matching
**\<b\>...\</b\>** and **\<i\>...\</i\>** tags.  All strings passed by this generator are valid
HTML so the invariant is that they all return true when
StringOps.isValidHTML(s) is called.  ValidHtmlStringGenerator is another custom
generator that is able to rigorously test your program by generating random but
only valid HTML strings.  If you run the above, you will immediately see a
failure.  But the failed input is probably going to be too long for easy
debugging.  That is because the doShrink method is incomplete as of now.
Currently, it returns an empty list of candidates meaning that the candidate
search ends immediately and the original string is not shrunk.

Fill in the doShrink method after reading the comments and comparing against
ABCStringGenerator.  If you implement this properly, you should see something
like this on the console:

```
testIsValidHTMLTrue s='<i><i><i></i></i><b><i><i></i></i><b><i></i><i></i></b></b></i>'
testIsValidHTMLTrue s='<i><i></i><b><i><i></i></i><b><i></i><i></i></b></b></i>'
testIsValidHTMLTrue s='<i><b><i><i></i></i><b><i></i><i></i></b></b></i>'
testIsValidHTMLTrue s='<i><b><i></i><b><i></i><i></i></b></b></i>'
testIsValidHTMLTrue s='<i><b><b><i></i><i></i></b></b></i>'
testIsValidHTMLTrue s='<i><b><b><i></i></b></b></i>'
testIsValidHTMLTrue s='<i><b><b></b></b></i>'
testIsValidHTMLTrue s='<i><b></b></i>'
testIsValidHTMLTrue s='<i></i>'
testIsValidHTMLTrue s=''
```

## Task 6: Debug StringOps isValidHTML method

Debug StringOps.isValidHTML based on the feedback so that now all tests pass.

### StringOpsTest Lessons

These are the two things you should have learned through this exercise:
1. Sometimes a program functions meaningfully only for a certain pattern of
   inputs.  In these situations, going with the default uniform distribution of
inputs will lead to horrible test coverage.
1. QuickCheck allows you to create your own generator by inheriting from
   existing generators and overriding some methods.  This allows you to
customize your own distribution.  In this exercise, we only practiced
generating integers and Strings but there is nothing preventing you from
generating objects.  For example, in the above StringOpsTest.testEquals(String
s1, String s2) method, we could have generated the two strings s1 and s2 as
part of the same object such that we correlate s1 and s2 in some way rather
than generating them separately.

## Submission

Each pairwise group will do one submission to GradeScope as usual.  The
submitting member must use the "View or edit group" link at the top-right
corner of the assignment page after submission to add his/her partner.  

Submit the repository created by GitHub Classroom for your team to GradeScope
at the **Supplementary Exercise 3 GitHub** link.  Once you submit, GradeScope
will run the autograder to grade you and give feedback.  If you get deductions,
fix your code based on the feedback and resubmit.  Repeat until you don't get
deductions.

The autograder will test your updated IntegerOpsTest, StringOpsTest, and
ValidHTMLStringGenerator classes against the original IntegerOps and StringOps
classes.  Since we are testing against the original classes before debugging,
we expect all tests to fail, if you have done the work.

# Extra Credit

* DUE: August 7 (Saturday), 2021 11:59 PM 

**GitHub Classroom Link:** https://classroom.github.com/g/dv2OpU9n

This extra credit is worth 1 point out of 100 points for the entire course.
Note that you need to get 100/100 on the autograder to get credit.  Partial
credit will be given under the discretion of the instructor and may not
necessarily equate to your GradeScope score.

## Description

We are going to go back to the MonkeySim program we profiled on Exercise 4.
The game is a simulation of the Collatz Conjecture
(https://en.wikipedia.org/wiki/Collatz_conjecture).  The conjecture states that
the game will eventually end (that is the first monkey will eventually get the
banana).  

Now it turns out that the MonkeySim does not end given certain arguments.  So
have we disproved the conjecture, something that nobody was able to do since
1937?  Are our names going to be on the cover page of newspapers tomorrow?

Sorry for the downer, but not really.  MonkeySim does not end because it has a
defect in its implementation.  For certain arguments, the game falls into an
infinite loop, where a cycle is formed when a group of monkeys pass the banana
among themselves in a repetitive pattern.

Now, this defect is hard to find using just a handful of test cases you can
write using JUnit.  So we are going to use stochastic testing to feed MonkeySim
with a whole bunch of randomized arguments to see which triggers the infinite
loop.  Complete MonkeySimStochasticTest.java to implement the testMonkeySim
method which checks the invariant that no matter which argument is given,
runSimulation never falls into an infinite loop.  Use the @InRange annotation
to make sure the generated random number for the starting monkey is greater
than or equal to 1, to make sure we do meaningful tests.

So how do we detect when runSimulation falls into an infinite loop?  Maybe add
a timeout to the test and say that if the test does not return within 10
minutes, we consider it to be an infinite loop?  But of course, there is no
guarantee since runSimulation may just be taking a long time to return.  In
order to detect infinite loops reliably, you will have to modify MonkeySim to
be able to detect it when it happens, that is when you detect a cycle forming
within a group of monkeys.  On detection, throw an InfiniteLoopException.  In
the testMonkeySim JUnit method, the check can be done by catching the
InfiniteLoopException and fail()-ing that test run.

## What to do

Create a new GitHub classroom repository by following the link posted above.
Note that this repository, while similar to Exercise 4, differs in a couple
of key ways.  It removed the List<Monkey> ml and MonkeyWatcher mw parameters
from its public methods to give you more freedom to choose your own data
structure for the group of monkeys and make other implementation changes.
And you will have to optimize MonkeySim heavily, beyond what you did for
Exercise 4, in order for the program to be performant enough for stochastic
testing.  Stochastic testing does hundreds of random trials and each trial
must be quick for it to end in a reasonable amount of time.

Also, a new **verbose** flag is added that allows MonkeySim to disable system
output, so that MonkeySim can be further sped up during testing.  You are
expected to turn off this flag for your stochastic tests as specified in the
Javadoc comments.

You will have to:
1. Optimize MonkeySim with the help of VisualVM, while making sure
   MonkeySimPinningTest continues to pass.
1. Modify MonkeySim so that it detects infinite loops and throws the
   InfiniteLoopException when it does, again making sure
MonkeySimPinningTest still passes.
1. Complete the testMainStochastic QuickCheck method in
   MonkeySimStochasticTest.java using the InfiniteLoopException exception.
1. Run testMainStochastic and let QuickCheck find a monkey number argument
   that triggers the infinite loop defect.
1. Create a regular JUnit test case out of that argument named
   testMainInfiniteLoop that checks for infinite loops.  Of course, this
test case will always fail with the current implementation.

In order to run the simulation, you can use the following commandline:
```
java -cp bin MonkeySim <starting_monkey_number>
```

In order to run the MonkeySimPinningTest and MonkeySimStochasticTest JUnit
tests, you can use the following commandline (for Windows):
```
java -cp quickcheck-jars/*:bin TestRunner
```
Or the commandline (for Mac/Linux):
```
java -cp quickcheck-jars/*:bin TestRunner
```

The expectation is that the pinning tests (that check for existing behavior)
should pass and the stochastic tests (that check for the infinite loop
defect), should fail.

## Extra Credit Submission

Modify your GitHub classroom repository to perform the above stochastic
test.  Please submit the repository to a separate **Supplementary Exercise 3
Extra Credit** link.
