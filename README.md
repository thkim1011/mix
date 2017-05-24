# MIX Simulator and Assembler 
[![Build Status](https://travis-ci.org/thkim1011/mix.svg?branch=master)](https://travis-ci.org/thkim1011/mix)
## About
This is a project I began because I was reading The Art of Computer Programming (I recommend this book for anyone who's interested in both mathematics and computer science). This book, contrary to many other books, use the hypothetical computer called MIX and the assembly language MIXAL associated with it. Upon reading how this computer works in detail, I was pretty amazed because I could finally conceive how a computer works. As a result, I decided to create a simulator and assembler for MIX. This program will provide tools to assemble and load MIXAL programs onto a MIX computer and run these programs. 

## Status
So far, the assembly process seems to be working very well. I've recently incorporated unit testing into this project, and had to significantly restructure it as a result (I'm very new to the idea of large software), so I was delayed, but now I can get on to the simulator. I'll probably have to run more tests in order to catch all the bugs within the assembly process but it should be mostly good. 

## Usage
As of the time or writing, this repository only contains the source. To build the project, use the tradition gradle command `gradle build`. Then in the terminal, type the command `java -cp /path/to/MIX.jar MIX /path/to/MIXAL/file.mixal`. It probably helps to make a batch file if you're using Windows.

## To do
* Make a simulator.
* ~~Learn how to use git properly.~~
* ~~Learn how to use Gradle~~
* ~~Learn how to use Travis CI~~
* Work on the unit testing parts.
* Separate the validation process from the assembling process.

## Disclaimer
When I first started this, I did this off of only knowledge of programming and absolutely no knowledge of assemblers. This may be very unconventionally programmed.

## LOL
I'm honestly surprised that I even came this far.
