data_file = "oving6.txt"
#Get words from file
def getWords(file):
  words = [] #initialize empty dict
  with open(file,'r') as f: #open file in read mode
    for line in f:
      for word in line.split():
        words.append(word)
  return words

#buuble sort methode
def bubleSort(words):
  for i in range(len(words)):
    for j in range(len(words)-1):
        #check if length is greater, then swap
        if len(words[i])<len(words[j]):
          temp = words[i]
          words[i] = words[j]
          words[j] = temp
        #if length is same, check word length
        if len(words[i])==len(words[j]):
            if words[i]<words[j]:
              temp = words[i]
              words[i] = words[j]
              words[j] = temp
  return words

print(bubleSort(getWords(data_file)))