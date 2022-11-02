import os
import sys


class Colors:
    HEADER = '\033[95m'
    OKBLUE = '\033[94m'
    OKCYAN = '\033[96m'
    OKGREEN = '\033[92m'
    WARNING = '\033[93m'
    FAIL = '\033[91m'
    ENDC = '\033[0m'
    BOLD = '\033[1m'
    UNDERLINE = '\033[4m'


def get_io_and_ec(fd):
    content = fd.readlines()
    input_content = ""
    output_content = ""
    mode = 0
    exit_code = 0

    for line in content:
        if line.startswith("ExitCode: "):
            exit_code = int(line.split(": ").pop())

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

    return exit_code


def enumerate_count(filename):
    count = 0
    with open(filename) as f:
        for count, _ in enumerate(f, 1):
            pass
    return count


def test_single_file(filename):
    fd = open(filename)

    right_exit_code = get_io_and_ec(fd)

    os.system(
        "java -jar build/libs/meteor-all.jar < {} > debug/test.s".format(filename))

    exit_code = os.system(
        "ravel --input-file=debug/input.txt --output-file=debug/answer.txt debug/test.s src/main/resources/builtin/builtin.s > debug/ravel.log 2> debug/ravel-error.log"
    ) >> 8

    if exit_code:
        if exit_code == right_exit_code:
            print(Colors.BOLD + Colors.OKGREEN +
                  "Accepted with exit code: {} (right)".format(exit_code) + Colors.ENDC)
            return 1
        else:
            print(Colors.BOLD + Colors.FAIL +
                  "Runtime Error {}".format(exit_code) + Colors.ENDC)
            return 0
    elif os.system(
            "diff -w -B --strip-trailing-cr --ignore-all-space debug/answer.txt debug/output.txt > debug/diff.txt"):
        print(Colors.BOLD + Colors.FAIL + "Wrong Answer" + Colors.ENDC)
        if enumerate_count("debug/output.txt") > 50:
            print(Colors.BOLD + Colors.FAIL + "Output too long" + Colors.ENDC)
        else:
            print(Colors.BOLD + Colors.FAIL + "Expected: " + Colors.ENDC)
            os.system("cat debug/output.txt")
            print(Colors.BOLD + Colors.FAIL + "Got: " + Colors.ENDC)
            os.system("cat debug/answer.txt")
        return 0
    else:
        print(Colors.BOLD + Colors.OKGREEN + "Accepted" + Colors.ENDC)
        return 1


def test_all_files(dirname):
    sum, accepted = 0, 0
    for filename in os.listdir(dirname):
        filepath = dirname + filename
        if os.path.isdir(filepath):
            sub_sum, sub_accepted = test_all_files(filepath + "/")
            sum += sub_sum
            accepted += sub_accepted
        elif filename.endswith(".mx"):
            print(Colors.BOLD + "Testing {}...".format(filename) + Colors.ENDC)
            accepted += test_single_file(filepath)
            sum += 1
    return sum, accepted


if __name__ == "__main__":
    file = ""
    testcases_dir = "src/main/resources/testcases/"
    subdir = "codegen/"
    modified = False
    for argv in sys.argv:
        if argv.startswith("--compile"):
            os.system("gradle shadowJar")
        elif argv.startswith("--file"):
            _, file = argv.split("=")
            if not file.endswith(".mx") and file.find(".") == -1:
                file += ".mx"
        elif argv.startswith("--dir"):
            _, subdir = argv.split("=")
            modified = True
            if not subdir.endswith("/"):
                subdir += "/"

    if not modified:
        print("In default, testcases in codegen/ will be tested.")

    if file == "":  # test all files
        sum, accepted = test_all_files(testcases_dir + subdir)
        print(Colors.BOLD + "Test Score: {}/{}".format(accepted, sum) + Colors.ENDC)
    else:  # test single file
        filename = testcases_dir + subdir + file
        print(Colors.BOLD + "Testing {}...".format(filename) + Colors.ENDC)
        test_single_file(filename)
