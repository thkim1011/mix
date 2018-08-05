* TAPE INPUT TOOL (BY TAE HYUNG KIM)
* THIS TOOL ALLOWS USERS TO INPUT NUMERICAL
* DATA INTO MIX TAPE UNITS.

PRINTER EQU  18
TERM    EQU  19
INPUT   EQU  2000
OUTPUT  EQU  1000

START   OUT  MSG1(PRINTER)
        OUT  MSG2(PRINTER)
        IN   INPUT(TERM)   Which tape unit to use?
        LDA  INPUT
        ENTX 0
        SRAX 9
        NUM
        STA  CHANGE(4:4)   Changes tape unit.

        ENT3 0
        OUT  MSG3(PRINTER)
LOOP    IN   INPUT(TERM)
        LDA  INPUT
        LDX  INPUT+1
        CMPA QUIT          If input is QUIT, save and exit.
        JE   END           
        JMP  FORMAT        Otherwise convert to number.
        STA  OUTPUT,3
        CMP3 =99=
        JNE  *+3           If rI1=100, send to tape.
CHANGE  OUT  OUTPUT
        ENT3 -1
        INC3 1
        JMP  LOOP

END     OUT  OUTPUT
        HLT

* FORMAT SUBROUTINE
FORMAT  STJ  RETURN
        CMPX SPACE(5:5)    Check if last char is a " "
        JNE  BREAK
        SRAX 1
        JMP  *-3
BREAK   NUM
RETURN  JMP  *

* STRINGS
        ORIG 3000
MSG1    ALF  TAPE 
        ALF  INPUT
        ALF   TOOL
        ALF   (BY 
        ALF  TAE H
        ALF  YUNG 
        ALF  KIM)

        ORIG 3024
MSG2    ALF  WHICH
        ALF   TAPE 
        ALF   UNIT
        ALF  :

        ORIG 3048
MSG3    ALF  INSER
        ALF  T DAT
        ALF  A:

        ORIG 3072
QUIT    ALF  QUIT
SPACE   ALF  
        END  START
