X        EQU   1000
         ORIG  3000
MAXIMUM  STJ   1+1
INIT     ENT3  0,1
         JMP   CHANGEM
LOOP     CMPA  X,3
         JGE   *+3
CHANGEM  ENT2  0,3
         LDA   X,3
         DEC3  1
         J3P   LOOP
EXIT     JMP   *