MAXWDS  EQU  1200
PERM    ORIG *+MAXWDS
ANS     ORIG *+MAXWDS
OUTBUF  ORIG *+24
CARDS   EQU  16
PRINTER EQU  18
BEGIN   IN   PERM(CARDS)
        ENT2 0
        LDA  EQUALS
1H      JBUS *(CARDS)
        CMPA PERM+15,2
        JE   *+2
        IN   PERM+16,2(CARDS)
        ENT1 OUTBUF
        JBUS *(PRINTER)
        MOVE PERM,2(16)
        OUT  OUTBUF(PRINTER)
        JE   1F
        INC2 16
        CMP2 =MAXWDS-16=
        JLE  1B
        HLT
1H      INC2 15
        ST2  SIZE
        ENT3 0
2H      LDAN PERM,3
        CMPA LPREN(1:5)
        JNE  1F
        STA  PERM,3
        INC3 1
        LDXN PERM,3
        JXZ  *-2
1H      CMPA RPREN(1:5)
        JNE  *+2
        STX  PERM,3
        INC3 1
        CMP3 SIZE
        JL   2B
        LDA  LPREN
        ENT1 ANS
OPEN    ENT3 0
1H      LDXN PERM,3
        JXN  GO
        INC3 1
        CMP3 SIZE
        JL   1B
*
DONE    CMP1 =ANS=
        JNE  *+2
        MOVE LPREN(2)
        MOVE =0=
        MOVE -1,1(22)
        ENT3 0
        OUT  ANS,3(PRINTER)
        INC3 24
        LDX  ANS,3
        JXNZ *-3
        HLT
*
LPREN   ALF      (
RPREN   ALF  )
EQUALS  ALF      =
*
GO      MOVE LPREN
        MOVE PERM,3
        STX  START
SUCC    STX  PERM,3
        INC3 1
        LDXN PERM,3(1:5)
        JXN  1F
        JMP  *-3
5H      STX  0,1
        INC1 1
        ENT3 0
4H      CMPX PERM,3(1:5)
        JE   SUCC
1H      INC3 1
        CMP3 SIZE
        JL   4B
        CMPX START(1:5)
        JNE 5B
CLOSE   MOVE RPREN
        CMPA -3,1
        JNE  OPEN
        INC1 -3
        JMP  OPEN
        END  BEGIN