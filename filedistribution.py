import random
import os
totalFiles = 0
nodeList = []
fileList = []
fileNameList = ["Adventures of Tintin","Jack and Jill","Glee","The Vampire Diarie",
"King Arthur","Windows XP","Harry Potter","Kung Fu Panda","Lady Gaga","Twilight",
"Windows 8","Mission Impossible","Turn Up The Music","Super Mario","American Pickers",
"Microsoft Office 2010","Happy Feet","Modern Family","American Idol","Hacking for Dummies"]
def calcFile(totalFiles,nodeList,fileList,fileNameList):
    for i in range (0,10):
        numFiles = random.randint(3,5)
        node = {'bucketId':'bucket_'+str(i+1), 'numFiles':numFiles, 'fileNames':[]}
        totalFiles = totalFiles + numFiles
        nodeList.append(node)
    print totalFiles
    for i in range (0,20):
        numFiles = random.randint(3,5)
        fileNode = {'fileName':fileNameList[i], 'numCopies':1, 'fileSize':random.uniform(1.0, 1.9)}
        totalFiles = totalFiles - 1
        fileList.append(fileNode)
    print totalFiles
    while(True):
        fileId = random.randint(0,19)
        if (fileList[fileId]['numCopies']==1):
            newRand = random.randint(1,2)
            if ((totalFiles - newRand) < 0 ):
                if not (totalFiles == 0):
                    fileList[fileId]['numCopies'] = totalFiles + 1
                    break
                else:
                    break
            else:
                fileList[fileId]['numCopies'] = newRand + 1
                totalFiles = totalFiles - newRand

    for element in fileList:
        setFileNum = element['numCopies']
        fileName = element['fileName']
        innterIterCount = 0
        while (setFileNum > 0):
            bucketId = random.randint(0,9)
            innterIterCount = innterIterCount + 1
            if (innterIterCount> 30):
                return False
            if (nodeList[bucketId]['numFiles'] > 0 ):
                if not (fileName in nodeList[bucketId]['fileNames']):
                    nodeList[bucketId]['fileNames'].append(fileName)
                    setFileNum = setFileNum -1
                    nodeList[bucketId]['numFiles']  = nodeList[bucketId]['numFiles']  -1
    return True

complete = False
while (not complete):
    totalFiles = 0
    nodeList = []
    fileList = []
    complete = calcFile(totalFiles,nodeList,fileList,fileNameList)
filePath = os.path.dirname(os.path.realpath(__file__))+'/FileSet'
if not os.path.exists(filePath):
    os.makedirs(filePath)
for element in nodeList:
    if not os.path.exists(filePath+'/'+element['bucketId']):
        os.makedirs(filePath+'/'+element['bucketId'])
        for fileelement in element['fileNames']:
            f = open(filePath+'/'+element['bucketId']+'/'+fileelement+'.txt',"wb")
            for ele in fileList:
                if (ele['fileName'] == fileelement):
                    size = int(1024*1024*ele['fileSize'])
            f.seek(size-1)
            f.write(b"\0")
            f.close()
print nodeList
