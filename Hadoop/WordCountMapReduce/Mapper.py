import sys

def mapper_out(file):
    sys.stdout = open("MappedOutput.txt", "w")
    with open("Input.txt") as file:
        lines = file.readlines()
        for line in lines:
            words = line.split()
            for word in words:
                print '%s %d\t' % (word, 1)
    sys.stdout.close()

if __name__ == "__main__":
    mapper_out(file)



