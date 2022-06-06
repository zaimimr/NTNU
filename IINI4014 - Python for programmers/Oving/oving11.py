import re

#Methode to genereate dict with all unique words and get nr of total words
def count_words(text):
	skips = [".", ", ", ":", ";", "'", '"', '\n', '?', '!']
	for ch in skips:
		text = text.replace(ch, "")
	word_counts = {}
	word_counts['.nr_count'] = 0 #totalt number of words
	for word in re.split(" ", text):
		word_counts['.nr_count'] += 1
		if word in word_counts:
			word_counts[word] += 1
		else:
			word_counts[word]= 1
	return word_counts

# get average word per sentence
def avg_words_in_sentence(text):
	text.replace('! ', '. ')
	text.replace('? ', '. ')
	sentences = text.split('.')
	word_count = count_words(text)
	return word_count['.nr_count']/len(sentences)

# get average sentence per paragraph
def sentences_paragraph(text):
  	paragraphs = text.split('\n\n') #its a parapraph if it has double \n
	par = []
	i = 0
	for paragraph in paragraphs:
		par.append(len(paragraph.split('. ')))
	sum = 0
	for p in par:
		sum += p
	return sum/len(par)

#precentage of easy words
def easy_words(text):
	word_list = count_words(text)
	tot_words = word_list['.nr_count']
	del word_list['.nr_count'] #remove total number of words to not mess up counting
	easy = 0
	for word in word_list:
		if word_list[word]/tot_words*100 > 0.1: #chech if word is used more then 0.1% in the text
			easy+=1
	return easy/tot_words

#precentage of hard words
def hard_words(text):
	word_list = count_words(text)
	tot_words = word_list['.nr_count']
	del word_list['.nr_count'] #remove total number of words to not mess up counting
	hard = 0
	for word in word_list:
		if word_list[word]/tot_words*100 < 0.01: #chech if word is used less then 0.01% in the text
			hard+=1
	return hard/tot_words

#precentage of unique words
def unique_words(text):
	word_list = count_words(text)
	tot_words = word_list['.nr_count']
	del word_list['.nr_count']
	return len(word_list)/tot_words

#get the text from a file
def getWords(file):
	text = ''
	with open(file,'r') as f:
		for line in f:
			text+=line
	return text

#main
data_file = "./books/folder_00/pg5200.txt"
a = getWords(data_file)
print("List of all the words:")
print(count_words(a))
print("Average word per sentece:")
print(avg_words_in_sentence(a))
print("Precentage of easy words:")
print(str(easy_words(a)*100) + '%')
print("Precentage of hard words:")
print(str(hard_words(a)*100) + '%')
print("Precentage of unique words:")
print(str(unique_words(a)*100) + '%')
print("Average sentence per paragraph:")
print(sentences_paragraph(a))
