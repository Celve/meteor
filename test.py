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


def testProg(filePath):
    res = os.system(
        "java -jar build/libs/meteor-all.jar < {} > meteor.log 2> meteorCompile.log".format(filePath))
    if (not os.system('grep "Success" {} > grep.log'.format(filePath))):
        status = "Accepted"
    else:
        status = "Rejected"
    if (res == 0):
        real = "Accepted"
    else:
        real = "Rejected"
    if (real == status):
        print(bcolors.OKGREEN + "{} {}, supposed {}".format(filePath,
                                                            real, status) + bcolors.ENDC)
    else:
        print(bcolors.FAIL + "{} {}, supposed {}".format(filePath,
                                                         real, status) + bcolors.ENDC)


def testSema():
    dirsPath = "src/main/resources/testcases/sema"
    dirs = os.listdir(dirsPath)
    for dir in dirs:
        dirPath = dirsPath + "/" + dir
        if os.path.isdir(dirPath):
            files = os.listdir(dirPath)
            for file in files:
                if not os.path.isdir(file):
                    testProg(dirPath + "/" + file)


if __name__ == "__main__":
    os.system("gradle shadowJar")
    argv = sys.argv[1:]
    if len(argv) == 0:
        testSema()
    else:
        dirsPath = "src/main/resources/testcases/sema/"
        for folder in argv:
            dirPath = dirsPath + folder
            files = os.listdir(dirPath)
            for file in files:
                if not os.path.isdir(file):
                    testProg(dirPath + "/" + file)
