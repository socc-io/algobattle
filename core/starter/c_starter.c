#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char* argv[]){
	char path[400];
	char cmd_compile[400];
	char cmd_exec[400];
	if(argc < 2){
		printf("no input source \n");
	}
	sprintf(path,"../generate/%s",argv[1]);
	sprintf(cmd_compile,"gcc -o ./result/%s.out %s",argv[1],path);
	system(cmd_compile);
	
	sprintf(cmd_exec,"./result/%s.out",argv[1]);
	system(cmd_exec);
	return 0;
}
