import os
import sys


if __name__ == "__main__":
    if (len(sys.argv) > 1):
      os.system("gradle shadowJar")
    os.system("java -jar build/libs/meteor-all.jar < debug/program.mx > debug/test.ll")
    os.system("clang src/main/resources/builtin/builtin.ll debug/test.ll -o debug/code")
    os.system("./debug/code")

