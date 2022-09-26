import os


def testProg(filePath):
    res = os.system("gradle run < {} >> meteor.log 2> meteorCompile.log".format(filePath))
    if (not os.system('grep "Success" {}'.format(filePath))):
        status = "Accepted"
    else:
        status = "Rejected"
    if (res == 0):
        print("{} Accepted, supposed {}".format(filePath, status))
    else:
        print("{} Rejected, supposed {}".format(filePath, status))


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
                    then = input()
                    if then == "stop":
                        return


testSema()
