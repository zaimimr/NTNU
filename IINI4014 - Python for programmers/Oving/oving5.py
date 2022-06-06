#methode to find word frequency in a text file
def getWordFreq(file):
  words = {} #initialize empty dict
  with open(file,'r') as f: #open file in read mode
    for line in f:
      for word in line.split():
        word = formatWord(word)
        if word in words: #if in dic increment
          words[word] += 1
        else:
          words[word] = 1
  #printer dict i sortert rekkefølge
  for word, number in sorted(words.items(), key = lambda x : x[1]):
    print(str(word) +  ": " + str(number))
  return words

#finner alle linjer et ord kommer
def getWordsLine(file, inW):
  lineArr = []
  lineCount = 0
  with open(file,'r') as f:
    for line in f:
      lineCount += 1
      #find string in string methode find()
      if (line.lower().find(formatWord(inW)) != -1):
        lineArr.append(lineCount)
  print("the word " + str(inW) + " in " + str(len(lineArr)) + " lines: " + str(lineArr))
  return lineArr

#methode strong to lowercase and remove extra char
def formatWord(word):
  return word.lower().replace("\"", "").replace("?", "").replace(":", "").replace(".", "").replace("(", "").replace(")", ""). replace(",", "").replace("!", "").replace(";", "")

#main methode
def main():
  data_file = "books/folder_00/pg5200.txt"
  findWord = "living"
  getWordFreq(data_file)
  getWordsLine(data_file, findWord)

#run main
main()
