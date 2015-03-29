	#include <stdio.h>

#define MOOK 0
#define ZZI 1
#define BBA 2

//*************
//use here for global variable
//don't remove comments
int glmine;
int glpreturn = -1;
int glstatus = 1;
int glwin;    //0 = lose 1 = win 2 = draw

//*************

int logic(int turn, int other_last_hand){
//*************
//fill here and don't remove comments
int my_hand ;
if (glstatus) {
    int result = glmine - other_last_hand;
    // win
    if (result == -1 || result == 2) {
        glwin = 1;
        glstatus = 0;
        my_hand = other_last_hand;
    }
    // lose
    else if (result == 1 || result == -2) {
        glwin = 0;
        glstatus = 0;
       my_hand = other_last_hand;
    }
    // draw
    else {
        my_hand = (glmine+1) % 3;
    }
    if (glpreturn < turn) {
        glpreturn++;
        my_hand = ZZI;
    }
} else {
    if (glwin == 1) {
        my_hand = other_last_hand;
    } else if (glwin == 0) {
        my_hand = other_last_hand;
    }
}

//*************
glmine = my_hand;
return my_hand;
}


//this is simulator main for your testing
int main(void){
int n = 100;
while(n-->0){
logic(100-n, n%3);
}
return 0;
}

