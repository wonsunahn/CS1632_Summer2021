#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void send_data(unsigned char *data, int len) {
  for (int i=0; i < len; i++) {
    printf("%2x ", data[i]);
  }
  printf("\n");
}

void foo() {
  // foo()'s stack frame gets allocated at exactly the location where
  // bar()'s stack frame used to be.  That means now p occupies the same
  // location data[8] used to occupy.
  double *p = (double *) &p;
  printf("p = %p\n", p);
}

unsigned char* bar() {
  // Array data[8] is deallocated immediately on function return as it falls
  // out of scope.  It is deallocated with the bar()'s stack frame.
  unsigned char data[8] = {1, 2, 3, 4, 5, 6, 7, 8};
  return data;
}

int main() {
  unsigned char *data = bar();
  foo();
  // At this point, data points to the value of p!
  send_data(data, 8);
  return 0;
}
