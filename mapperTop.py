import sys, csv

def mapper():
    readFile = csv.reader(sys.stdin, delimiter='\t')
    for line in readFile:
        if (len(line)) != 19:
            continue
        if(len(line)) == 19:
            id, title, tagnames, authorId, body, nodeType, parentId, abParentId, addedAt, score, stateString, lastEditedId, lastActivityId, lastActivityT, activeRevId, extra, extraRefId, extraCount, marked = line
            if(nodeType == 'question'):
                readTags = tagnames.split(' ')
                for tag in readTags:
                    print(tag, " ", id)

if __name__ == "__main__":
    mapper()