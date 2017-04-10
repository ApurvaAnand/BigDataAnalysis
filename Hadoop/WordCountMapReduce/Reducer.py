import sys


def reducer_out(file):
    old_word = None
    total_count = 0
    with open("MappedOutput.txt") as file:
        lines = file.readlines()
        for line in lines:
            this_word, this_count = line.split()
            this_count = int(this_count)
            if old_word and old_word != this_word:
                print '%s\t%s' % (old_word, total_count)
                old_word = this_word
                total_count = 0

            old_word = this_word
            total_count += this_count

        if old_word != None:
            print '%s\t%s' % (old_word, total_count)

if __name__ == "__main__":
    reducer_out(file)
