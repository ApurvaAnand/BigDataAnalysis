import sys, csv, re

def mapper():
    reader = csv.reader(sys.stdin, delimiter='\t')
    for line in reader:
        if (len(line)) != 19:
            continue
            if(len(line)) == 19:
                id, title, tagnames, authorId, body, nodeType, parentId, abParentId, addedAt, score, stateString, lastEditedId, lastActivityId, lastActivityT, activeRevId, extra, extraRefId, extraCount, marked = line
                print "{0}\t{1}".format(authorId, hour)

if __name__ == "__main__":
    mapper()