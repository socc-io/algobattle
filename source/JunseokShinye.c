	#include <stdio.h>

#define MOOK 0
#define ZZI 1
#define BBA 2

//*************
//use here for global variable
//don't remove comments
int start = MOOK;
int count11=1;
//*************

int logic(int turn, int other_last_hand){
//*************
//fill here and don't remove comments
int your_hand;

if(count11==turn){
 count11++;
if((turn%=3)==0){start=MOOK;}
else if((turn%=3)==1){start=ZZI;}
else{start==BBA;}
your_hand=start;

}
else{
    if(other_last_hand-start ==1||other_last_hand-start==-2){
        start = other_last_hand;
     }
    else if(other_last_hand==start){
        start--;
        if(start==-1){start=2;}
     }
    else{
        if(start==0){
           start = 1;
         } 
         else if(start==1){
           start = 2;
         }
         else{
            start = 0;
          }

    }  
     your_hand=start;
}
//*************
return your_hand;
}


//this is simulator main for your testing
int main(void){
int n = 100;
while(n-->0){
logic(100-n, n%3);
}
return 0;
}

