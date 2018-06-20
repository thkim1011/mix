# MIX Assembler, Simulator, and Debugger 
[![Build Status](https://travis-ci.org/thkim1011/mix.svg?branch=master)](https://travis-ci.org/thkim1011/mix)

## About
This is a project I began because I was reading The Art of Computer Programming 
(I recommend this book for anyone who's interested in both mathematics and computer 
science). This book, contrary to many other books, use the hypothetical computer
called MIX and the assembly language MIXAL associated with it. This program will 
provide tools to assemble and load MIXAL programs onto a MIX computer and run 
these programs. 

## Usage

### Build
To build the project, just run `gradle build`. The `mix` file in the root directory 
is a bash script which runs `java -jar /path/to/MIX.jar` with the current directory
 in mind. Adding the `mix` to path will allow you to run `mix` from any directory. 

### Example
Make a new file in any directory called `hello.asm` with the follwoing code.
```
* HELLO WORLD
PRINTER EQU  18
START   OUT  HELLO(PRINTER)
        HLT
HELLO   ALF  HELLO
        ALF   WORL
        ALF  D
        END  START
```
Then run:
```
mix hello.asm
```
You should see the following output
```
HELLO WORLD
```

### Assembling
MIX has three modes, one of which is assembly. To simply assemble MIX code, run one of the two:
```
mix -a hello.asm
mix --assemble hello.asm
```
This will make a new file called which contains 4000 words divided into a sign and 5 bytes.

### Simulating
To simulate the MIX code, run one of the three:
```
mix hello.asm
mix -s hello.asm
mix --simulate hello.asm
```
Note that not specifying any option is equivalent to the simulation mode.


### Debugging
To debug MIX code, run one of the three:
```
mdb hello.asm
mix -d hello.asm
mix --debug hello.asm
```

## Status
The assembler is complete, but requires more testing. The simulator is almost finished, 
but input output is not complete. 

## LOL
I'm honestly surprised that I even came this far.
