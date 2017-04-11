import sys, csv

def reducer():
    oldTag = None
    thisTag = None
    questionId = None
    tags = {}
    tagCounts = {}
    readFile= csv.reader(sys.stdin, delimiter='\t')
    for line in readFile:
        if (len(line) != 2):
            continue
        if(len(line) == 2):
            currentTag, questionId = line
            if(not tags.has_key(currentTag)):
                tags[currentTag] = []
                tagCounts[currentTag] = 0
            tags[currentTag].append(questionId)
            tagCounts[currentTag] = tagCounts[currentTag] + 1
    topTags = sorted(tagCounts, key = tagCounts.get, reverse=True)

    for tag in topTags:
        print([tag, ','.join(tags[tag])])

if __name__ == "__main__":
    reducer()