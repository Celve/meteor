#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void print(char *str) { printf("%s", str); }

void println(char *str) { printf("%s\n", str); }

void printInt(int n) { printf("%d", n); }

void printlnInt(int n) { printf("%d\n", n); }

int getInt() {
  int n;
  scanf("%d", &n);
  return n;
}

char *getString() {
  char *str = malloc(sizeof(char) * 256);
  scanf("%s", str);
  return str;
}

char *toString(int n) {
  char *str = malloc(20);
  sprintf(str, "%d", n);
  return str;
}

char *_malloc(int mallocSize) { return malloc(mallocSize); }

char *_string_substring(char *str, int start, int end) {
  char *substr = malloc(sizeof(char) * (end - start + 1));
  memcpy(substr, str + start, end - start);
  substr[end - start] = '\0';
  return substr;
}

int _string_length(char *str) { return strlen(str); }

int _string_parseInt(char *str) {
  int n;
  sscanf(str, "%d", &n);
  return n;
}

int _string_ord(char *str, int index) { return str[index]; }

char *_string_concat(char *str1, char *str2) {
  char *str = malloc(sizeof(char) * (strlen(str1) + strlen(str2) + 1));
  sprintf(str, "%s%s", str1, str2);
  return str;
}