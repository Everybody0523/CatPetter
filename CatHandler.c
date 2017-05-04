#include <stdio.h>
#include <unistd.h>
#include <libgen.h>
#include <string.h>
#include <errno.h>
#include <stdlib.h>

int getCatInfo(const char * cat_name, char * output, const char fname){
    // Open file with information about cats
    FILE * fp;
    fp = fopen(fname, "r");
    char * line = NULL;
    size_t len;
    if (fp == NULL){ // Just doing some error checking
        return -1;
    }

    while (read = getline(&line, &len, fp) != -1){
        if (strcmp(line, cat_name)){
            output = malloc(sizeof(char) * len);
            strcpy(line, output);
            return 0;
        }
    }
    return -1;
}
