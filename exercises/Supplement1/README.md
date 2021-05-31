# CS 1632 - Software Quality Assurance
SUmmer Semester 2021 - Supplementary Exercise 1

* DUE: No Submission 

## Description

This set of code demonstrates concepts we learned in the Software QA and
Nondeterminism lecture.  By trying out these programs, you will learn the following:

1. Observe how values of pointers in C are randomized through ASLR leading to nondeterministic program behavior.

1. Observe how pointer values can leak out to program output through memory errors.

1. Learn how to turn ASLR off to make C pointers deterministic.

1. Learn how to use ASAN (Google Address Sanitizer) to debug stack overflow memory errors.

1. Learn how to use ASAN (Google Address Sanitizer) to debug dangling pointer memory errors.

1. Observe how dataraces in C leads to nondeterministic program behavior.

1. Learn how to use TSAN (Google Thread Sanitizer) to debug datarace errors.

1. Compare ASAN with Valgrind, another memory error detection tool.

## Connecting to thoth.cs.pitt.edu

In order to use ASAN or TSAN, you need to clang version >= 3.1 or gcc version >= 4.8.  Since you are unlikely to have either installed on your local
computer, I will ask you to connect using SSH to one of the departmental public
Linux servers at thoth.cs.pitt.edu.

If you use Windows, please follow these steps:

1. Every OS comes with an SSH commandline client nowadays.  Open a commandline shell (e.g. cmd) then type:
   ```
   $ ssh USERNAME@thoth.cs.pitt.edu
   ```
   Where USERNAME is replaced with your own Pitt ID.

   If you would like a GUI SSH client, Putty is a free open source SSH terminal:
   https://www.chiark.greenend.org.uk/~sgtatham/putty/latest.html
   Connect to "thoth.cs.pitt.edu" by typing that in the "Host Name" box.  Make sure that port is 22 and SSH is selected in the radio button options.

1. Once connected, the host will ask for your Pitt credentials.  Enter your Pitt ID and password.

Once logged in, you may see an unsettling welcome message showing system
diagnostics.  Don't panic, the machine is just going through some system
updates and admins haven't yet settled on a nice welcome message yet. :) 

## Building

Once logged in, you will also notice that your home directories are empty,
which may be unsettling for those of you who used thoth before and had files
there.  That is because the admins have not yet connected your thoth accounts
to your departmental AFS (Andrew File System) home directories.  For now, your
home directories are located in the local hard disk.  These home directories
will be razed once the semester is over.

The upshot is that you will have to backup your files before the semester is
over if you want to keep them.  You can use a file transfer client like
[FileZilla](https://filezilla-project.org/download.php?type=client) to download
them to your desktop.  Or, you can try to copy them to your AFS home directory,
if you know your way around Linux.  Your AFS home directory is mounted at
/afs/cs.pitt.edu/usr0/USERNAME (replace USERNAME with your Pitt ID).

The first action item is to copy over the exercise source code to your local
directory.  Create and go to a directory of your choice (or you can stay at
your default home directory) and do the following:

```
$ cp -R /home/PITT/wahn/nondeterminism ./
$ cd nondeterminism/C
```

I have provided a Makefile build script to automate the build.  All you have to do is invoke 'make':

```
$ make
gcc -c -g -w heap.c -o heap.o
gcc heap.o -lm -o heap.bin
gcc -c -g -w stack.c -o stack.o
gcc stack.o -lm -o stack.bin
gcc -c -g -w stack_overflow.c -o stack_overflow.o
gcc stack_overflow.o -lm -o stack_overflow.bin
gcc -c -g -w stack_pointer_return.c -o stack_pointer_return.o
gcc stack_pointer_return.o -lm -o stack_pointer_return.bin
gcc -c -g -w binary_tree.c -o binary_tree.o
gcc binary_tree.o -lm -o binary_tree.bin
gcc -c -g -w -pthread datarace.c -o datarace.o
gcc datarace.o -lm -pthread -o datarace.bin
gcc -c -g -w -fsanitize=address stack_overflow.c -o stack_overflow.asan.o
gcc stack_overflow.asan.o -lm -fsanitize=address -o stack_overflow.asan
gcc -c -g -w -fsanitize=address stack_pointer_return.c -o stack_pointer_return.asan.o
gcc stack_pointer_return.asan.o -lm -fsanitize=address -o stack_pointer_return.asan
gcc -c -g -w -fsanitize=address binary_tree.c -o binary_tree.asan.o
gcc binary_tree.asan.o -lm -fsanitize=address -o binary_tree.asan
gcc -c -g -w -fPIE -fsanitize=thread -pthread datarace.c -o datarace.tsan.o
gcc datarace.tsan.o -lm -pie -fsanitize=thread -o datarace.tsan
```

Note how when I create ASAN instrumented binaries (e.g. stack_overflow.asan,
stack_pointer_return.asan, ...), I pass the **-fsanitize=address** compiler option
to gcc.  You need to pass it to both the compilation stage and the linking
stage.

Also note how when I create TSAN instrumented binaries (e.g. datarace.tsan)
I pass the **-fsanitize=thread** compiler option
to gcc.  I also pass the **-fPIE** and **-pie** options to the compilation and linking
stages respectively.  This makes your code position independent, and is needed for
TSAN to work flawlessly (if you don't know what PIE is, you'll learn in CS 449).

## Testing ASLR (Address Space Layout Randomization)

heap.c is a simple program that mallocs some bytes on the heap and prints out
the pointer to that heap location.  You can use 'nano' to view the file on the
terminal (or your favorite Linux editor):

```
$ nano heap.c
```

Or, you can view it on the GitHub.  As we learned, even this simple program can
display nondeterministic behavior due to ASLR.  Try it out yourself!

```
$ ./heap.bin
p = 0x55de862c92a0
$ ./heap.bin
p = 0x56317e9b52a0
$ ./heap.bin
p = 0x5654dc8022a0
```

Your actual values will vary but you can see how the output is randomized.

Likewise, stack.c is a simple program that prints out the pointer to a stack
location.  And it also displays nondeterministic behavior due to ASLR:

```
$ ./stack.bin
p = 0x7fff5e1060d0
$ ./stack.bin
p = 0x7fff4526e850
$ ./stack.bin
p = 0x7ffd5c507230
```

Now, let's try running both with ASLR turned off.  I've written a simple script
named 'run_aslr_off.sh' that does exactly that:

```
$ ./run_aslr_off.sh ./heap.bin
setarch x86_64 -R ./heap.bin
p = 0x5555555592a0
$ ./run_aslr_off.sh ./heap.bin
setarch x86_64 -R ./heap.bin
p = 0x5555555592a0
$ ./run_aslr_off.sh ./heap.bin
setarch x86_64 -R ./heap.bin
p = 0x5555555592a0
$ ./run_aslr_off.sh ./stack.bin
setarch x86_64 -R ./stack.bin
p = 0x7fffffffe3e0
$ ./run_aslr_off.sh ./stack.bin
setarch x86_64 -R ./stack.bin
p = 0x7fffffffe3e0
$ ./run_aslr_off.sh ./stack.bin
setarch x86_64 -R ./stack.bin
p = 0x7fffffffe3e0
```

Note that now the output is no longer random!  This is what it says if you 'man
setarch':

```
$ man setarch
...
 -R, --addr-no-randomize
              Disables randomization of the virtual address space (turns on ADDR_NO_RANDOMIZE).
...
```

Did you ever get the feeling that your C program that used to behave randomly
suddenly becomes deterministic when you run it on top of GDB (GNU Debugger)?
That is because GDB by default turns off ASLR for debugging purposes so that
behavior is reproducible.  Turning off ASLR can be very useful in a debug
setting.

## Using Google ASAN (Address Sanitizer)

stack_overflow.c is a buggy program that demonstrates the stack buffer overflow
issue that we discussed in the lecture.  It tries to send bytes beyond the
bounds of the 'data' array and ends up sending the 'next' pointer value along
with the data.  It displays nondeterministic behavior due to the pointer
value leaking out:

```
$ ./stack_overflow.bin
second.next = 0x7ffcff441850
48 65 6c 6c 6f  0  0  0 50 18 44 ff fc 7f  0  0
$ ./stack_overflow.bin
second.next = 0x7ffc03076390
48 65 6c 6c 6f  0  0  0 90 63  7  3 fc 7f  0  0
$ ./stack_overflow.bin
second.next = 0x7ffd8d7558a0
48 65 6c 6c 6f  0  0  0 a0 58 75 8d fd 7f  0  0
```

The first 8 bytes of the output (48 65 6c 6c 6f  0  0  0) is the ASCII code
representation of the "Hello" string in the 'data' array, and is therefore
deterministic.  The last 8 bytes of the output is the 'next' pointer of the
'second' node that follows it in the stack memory layout, and is therefore
randomized by ASLR.  I also print out the value of the 'next' pointer
explicitly, just to show you that the latter half of send_data is sending out
the value of 'next' (in little endian).

Of course, you could again turn off ASLR to make the buggy program run
deterministically at least while debugging:

```
$ ./run_aslr_off.sh ./stack_overflow.bin
setarch x86_64 -R ./stack_overflow.bin
second.next = 0x7fffffffe3c0
48 65 6c 6c 6f  0  0  0 c0 e3 ff ff ff 7f  0  0
$ ./run_aslr_off.sh ./stack_overflow.bin
setarch x86_64 -R ./stack_overflow.bin
second.next = 0x7fffffffe3c0
48 65 6c 6c 6f  0  0  0 c0 e3 ff ff ff 7f  0  0
$ ./run_aslr_off.sh ./stack_overflow.bin
setarch x86_64 -R ./stack_overflow.bin
second.next = 0x7fffffffe3c0
48 65 6c 6c 6f  0  0  0 c0 e3 ff ff ff 7f  0  0
```

But your end users will most likely have ASLR turned on in their machines for
security.  What then?  Your programs will again be nondeterministic and testing
would no longer guarantee correct behavior.  So we may still get surprise
defects.

How can we have a deterministic program when all addresses are randomized?
Easy: just don't let addresses leak out to program output!  As we discussed,
unless for debugging purposes, programs will almost never intentionally output
addresses where data is stored --- they will typically output the data.  It is
just that addresses leak out to output due to memory errors (like in this
case).  So if we can catch all memory errors, then problem solved!  ASAN is
exactly the kind of tool that can help you do that.

Now let's see if ASAN can find the bug for us by running the instrumented binary:

```
$ ./stack_overflow.asan
second.next = 0x7ffdd8881d00
=================================================================
==473163==ERROR: AddressSanitizer: stack-buffer-overflow on address 0x7ffdd8881cd0 at pc 0x55856b3162e7 bp 0x7ffdd8881c60 sp 0x7ffdd8881c50
READ of size 1 at 0x7ffdd8881cd0 thread T0
    #0 0x55856b3162e6 in send_data /home/PITT/wahn/nondeterminism/C/stack_overflow.c:12
    #1 0x55856b3164bc in main /home/PITT/wahn/nondeterminism/C/stack_overflow.c:27
    #2 0x7f5cc67fc0b2 in __libc_start_main (/lib/x86_64-linux-gnu/libc.so.6+0x270b2)
    #3 0x55856b3161cd in _start (/u/home/PITT/wahn/nondeterminism/C/stack_overflow.asan+0x11cd)

Address 0x7ffdd8881cd0 is located in stack of thread T0 at offset 48 in frame
    #0 0x55856b316328 in main /home/PITT/wahn/nondeterminism/C/stack_overflow.c:17

  This frame has 3 object(s):
    [32, 48) 'first' (line 18) <== Memory access at offset 48 overflows this variable
    [64, 80) 'second' (line 18)
    [96, 112) 'third' (line 18)
...
```

ASAN is able to pinpoint exactly where the illegal "READ of size 1" happened at
stack_overflow.c:12!  That is where the out of bounds array access happens.
Below that line is the stack trace so we know the calling context.

stack_pointer_return.c is a buggy program with a common error where a function
returns a pointer to a local array.  When the function returns, the local array
is deallocated with the rest of the function frame as it is now out of scope,
thereby leaving the pointer dangling.  Later, that memory location is
reoccupied by a pointer value so that later when the program attempts to send
the bytes in that array, it sends the pointer value instead.  It leads to
nondeterministic behavior likewise:

```
$ ./stack_pointer_return.bin
p = 0x7fff71d62550
Segmentation fault (core dumped)
$ ./stack_pointer_return.bin
p = 0x7ffcc23f1cc0
Segmentation fault (core dumped)
$ ./stack_pointer_return.bin
p = 0x7fff29117c00
Segmentation fault (core dumped)
```

Let's see if ASAN is able to find this bug:

```
$ ./stack_pointer_return.asan
p = 0x7fffe612ea70
AddressSanitizer:DEADLYSIGNAL
=================================================================
==473332==ERROR: AddressSanitizer: SEGV on unknown address 0x000000000000 (pc 0x55ddf298f2c7 bp 0x7fffe612ead0 sp 0x7fffe612eab0 T0)
==473332==The signal is caused by a READ memory access.
==473332==Hint: address points to the zero page.
    #0 0x55ddf298f2c6 in send_data /home/PITT/wahn/nondeterminism/C/stack_pointer_return.c:7
    #1 0x55ddf298f548 in main /home/PITT/wahn/nondeterminism/C/stack_pointer_return.c:31
    #2 0x7ffada7380b2 in __libc_start_main (/lib/x86_64-linux-gnu/libc.so.6+0x270b2)
    #3 0x55ddf298f1ad in _start (/u/home/PITT/wahn/nondeterminism/C/stack_pointer_return.asan+0x11ad)

AddressSanitizer can not provide additional info.
SUMMARY: AddressSanitizer: SEGV /home/PITT/wahn/nondeterminism/C/stack_pointer_return.c:7 in send_data
==473332==ABORTING
```

Again, stack_pointer_return.c:7 is flagged as an illegal read because it is
attempting to read a location that has already been deallocated.

## Using Google TSAN (Thread Sanitizer)

datarace.c is a buggy program with a datarace on the variable 'shared'.  Hence,
everytime you run the program you will get nondeterministic output:

```
$ ./datarace.bin
shared=1024461
$ ./datarace.bin
shared=1041862
$ ./datarace.bin
shared=1021775
```

Now let's try using TSAN to discover this bug by running the instrumented binary:

```
$ ./datarace.tsan
==================
WARNING: ThreadSanitizer: data race (pid=473522)
  Read of size 4 at 0x557ee2a4d014 by main thread:
    #0 add /home/PITT/wahn/nondeterminism/C/datarace.c:7 (datarace.tsan+0x129a)
    #1 main /home/PITT/wahn/nondeterminism/C/datarace.c:16 (datarace.tsan+0x1325)

  Previous write of size 4 at 0x557ee2a4d014 by thread T1:
    #0 add /home/PITT/wahn/nondeterminism/C/datarace.c:7 (datarace.tsan+0x12af)
    #1 <null> <null> (libtsan.so.0+0x2d1af)

  Location is global 'shared' of size 4 at 0x557ee2a4d014 (datarace.tsan+0x000000004014)

  Thread T1 (tid=473524, running) created by main thread at:
    #0 pthread_create <null> (libtsan.so.0+0x5ea99)
    #1 main /home/PITT/wahn/nondeterminism/C/datarace.c:14 (datarace.tsan+0x131b)

SUMMARY: ThreadSanitizer: data race /home/PITT/wahn/nondeterminism/C/datarace.c:7 in add
==================
shared=1921984
ThreadSanitizer: reported 1 warnings
```

It tells you exactly what each thread was doing to cause the datarace.  The
"main thread" was executing add in line datarace.c:7 and "thread T1" (the
child thread) was likewise executing add at the same source line.  That is
exactly where the unprotected 'shared++' is happening.

## Comparing Google ASAN with Valgrind

You may have used a runtime memory error checking tool called Valgrind in CS
449: Introduction to System Software or somewhere else.  In terms of purpose,
ASAN and Valgrind share common goals.  However, ASAN is superior to Valgrind in
some ways.  That is because ASAN performs instrumentation at the source code
level whereas Valgrind performs instrumentation at the binary level.  A lot of
the semantic information that was present at the source code level is removed
at the binary level, meaning Valgrind instrumentation cannot be as detailed and
as efficient as ASAN instrumentation.  

For one thing, ASAN is much faster than Valgrind.  Since the source code
provides much more semantic information, ASAN can make a much better decision
on where instrumentation is needed.  Also, the instrumentation gets optimized
along with other code using compiler optimizations.  'binary_tree.c' is a
benchmark in the [Language Shootout Benchmark
Suite](https://benchmarksgame-team.pages.debian.net/benchmarksgame/index.html).
Let's time 'binary_tree.c' a) running without instrumentation, b) running with
ASAN instrumentation, and c) running with Valgrind instrumentation:

   ```
   $ time ./binary_tree.bin 10
   ...
   real    0m0.012s
   user    0m0.012s
   sys     0m0.000s

   $ time ./binary_tree.asan 10
   ...
   real    0m0.174s
   user    0m0.145s
   sys     0m0.029s

   $ time valgrind ./binary_tree.bin 10
   ...
   real    0m1.831s
   user    0m1.723s
   sys     0m0.108s
   ```

   'time' is a Linux utility used to time an application.  The last three rows
starting with 'real', 'user', and 'sys' is output from the 'time' utility and
not from the application.  We are going to learn more about it when we talk
about Performance Testing, but for now, all you need to know is that 'real'
measures real time (as in actual wall clock time to run an application).  As
you can see, ASAN results in an approximately 14.5X slowdown whereas Valgrind
results in an approximately 152.6X slowdown!

So, is Valgrind obsolete?  No, Valgrind does have one strong point: that it can
instrument binaries without the need of source code and without the need of
recompilation.  But if you do have the source code (which is typically the case
for tested software), most people would prefer ASAN over Valgrind.

## Submission

There is no submission.  These are just code examples to help your
understanding.  I encourage you to try to debug these errors using the tools we
learned.  If you are not familiar with the Linux environment, you can use
'nano' as a very simple editor.

```
$ nano stack_overflow.c
```

Then, invoke 'make' again to recompile the program:

```
$ make
gcc -c -g -w stack_overflow.c -o stack_overflow.o
gcc stack_overflow.o -lm -o stack_overflow.bin
gcc -c -g -w -fsanitize=address stack_overflow.c -o stack_overflow.asan.o
gcc stack_overflow.asan.o -lm -fsanitize=address -o stack_overflow.asan
```

## Resources

* Reproducible Builds Website: https://reproducible-builds.org/docs/
* Windows SSH Terminal Client: [Putty](https://www.chiark.greenend.org.uk/~sgtatham/putty/latest.html)
* File Transfer Client: [FileZilla](https://filezilla-project.org/download.php?type=client)
* Linux command line tutorial: [The Linux Command Line](http://linuxcommand.org/lc3_learning_the_shell.php)
