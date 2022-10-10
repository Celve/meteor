import os
import sys


class bcolors:
    HEADER = '\033[95m'
    OKBLUE = '\033[94m'
    OKCYAN = '\033[96m'
    OKGREEN = '\033[92m'
    WARNING = '\033[93m'
    FAIL = '\033[91m'
    ENDC = '\033[0m'
    BOLD = '\033[1m'
    UNDERLINE = '\033[4m'


def getInputAndOutput(fd):
    content = fd.readlines()
    input_content = ""
    output_content = ""
    mode = 0

    for line in content:
        if line == "=== end ===\n":
            mode = 0

        if mode == 1:
            input_content += line
        elif mode == 2:
            output_content += line

        if line == "=== input ===\n":
            mode = 1
        elif line == "=== output ===\n":
            mode = 2

    input_fd = open("debug/input.txt", "w")
    input_fd.write(input_content)

    output_fd = open("debug/output.txt", "w")
    output_fd.write(output_content)


def enumerateCount(filename):
    count = 0
    with open(filename) as f:
        for count, _ in enumerate(f, 1):
            pass
    return count


if __name__ == "__main__":
    file = "debug/program"
    for argv in sys.argv:
        if argv.startswith("--compile"):
            os.system("gradle shadowJar")
        elif argv.startswith("--file"):
            _, file = argv.split("=")
            file = "src/main/resources/testcases/codegen/{}".format(file)

    os.system(
        "cp {}.mx debug/program.mx".format(file))

    fd = open(file + ".mx")

    if (file == "debug/program"):
        os.system(
            "java -jar build/libs/meteor-all.jar < {}.mx > debug/test.ll".format(file))
        os.system(
            "clang src/main/resources/builtin/builtin.ll debug/test.ll -o debug/code")
        os.system("./debug/code")
        exit(0)

    getInputAndOutput(fd)

    os.system("	clang++ -fno-discard-value-names -S -emit-llvm {}.cpp -o debug/standard.ll -O0".format(file))
    os.system(
        "java -jar build/libs/meteor-all.jar < {}.mx > debug/test.ll".format(file))
    os.system(
        "clang src/main/resources/builtin/builtin.ll debug/test.ll -o debug/code")

    exitCode = os.system("./debug/code < debug/input.txt > debug/answer.txt")
    if exitCode:
        print(bcolors.FAIL + "Runtime Error {}".format(exitCode) + bcolors.ENDC)
        exit(0)

    if os.system("diff --strip-trailing-cr --ignore-all-space debug/answer.txt debug/output.txt > debug/diff.txt"):
        print(bcolors.FAIL + "Wrong Answer" + bcolors.ENDC)
        if enumerateCount("debug/output.txt") > 50:
            print(bcolors.FAIL + "Output too long" + bcolors.ENDC)
        else:
            print(bcolors.FAIL + "Expected: " + bcolors.ENDC)
            os.system("cat debug/output.txt")
            print(bcolors.FAIL + "Got: " + bcolors.ENDC)
            os.system("cat debug/answer.txt")
    else:
        print(bcolors.OKGREEN + "Accepted" + bcolors.ENDC)
